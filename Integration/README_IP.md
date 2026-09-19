# Persistent Digital Twin — Individual Project

COMPSYS 704 Project 1 · Zhiyuan Fu (zfu276) · Group 7

A persistent digital twin for every bottle on the EABS line: workpiece traceability,
recipe-deviation detection, recovery from abrupt interruption, and a fault log with
operator resolutions — stored in SQLite, so the production record outlives the run that
produced it.

Brief theme (§ 4.1.3): *"Creation and use of digital twins of workpieces and product
instances to enable tracking of the workpiece during production."*

> This is the **Individual Project** README. The production line it plugs into —
> coordinator, stations, safety, POS order entry — and the build are documented in the
> Group Project README: **[`README.md`](README.md)**. Design rationale and deviations from
> the conceptual design are in the IP report.

**Contents:** [1 What it adds](#1-what-the-ip-adds) · [2 Boundary](#2-boundary-with-the-gp) ·
[3 How it connects](#3-how-it-connects) · [4 Files](#4-files) ·
[5 Data model](#5-data-model) · [6 Run](#6-running) · [7 Use](#7-using-the-ip) ·
[8 Demos](#8-demonstrations) · [9 Inspecting the database](#9-inspecting-the-database) ·
[10 Troubleshooting](#10-troubleshooting)

---

## 1. What the IP adds

The GP's `WorkpieceTracker` knows where a bottle is *now*, and forgets everything when the
simulation stops. This IP records where every bottle has been, which order, product and
recipe it belongs to, whether it followed its route, and what happened to it if it never
finished — and keeps all of it after the run ends.

| Capability | Where to see it |
|---|---|
| Per-bottle traceability that survives restart | Track Bottle tab |
| Order progress counted from per-bottle evidence | Track Order tab |
| Recipe-deviation detection against the expected route | Track Bottle tab, console |
| Recovery from an abrupt interruption | Console at start-up; Track Order, Track Bottle |
| Fault log with operator-recorded resolutions | Faults History tab |
| Cross-order batch consolidation | Console, `RunCoordinatorIp` |

---

## 2. Boundary with the GP

| IP | GP (used by the IP, not part of it) |
|---|---|
| SQLite schema, `Db`, `Dao` | `CoordinatorCD` / `IntegratedCoordinator` |
| `TwinEventBus`, `DigitalTwinAssembler`, `DbWorker`, `DeviationDetector` | `WorkpieceTracker`, the stations, the safety monitor |
| `IpBatchManagerModel` — persistence, recovery, GUI queries | `POS`, `BatchManager` — order validation and batch admission |
| GUI tabs 2–4: **Track Order**, **Track Bottle**, **Faults History** | GUI tab 1: **Place Order** |

IP code enters a GP file in one place only: two private hooks in `IntegratedCoordinator`
(`publishTwinAdmission`, `publishTwinTransition`), called beside the coordinator's existing
tracker calls. They never block, never throw, and do nothing in the GP-only profiles.

---

## 3. How it connects

The IP's clock domain is `IpBatchManagerCD` (`sysj/ip_batch_manager.sysj`), wired in
`coordinator_ip.xml` as a peer of `CoordinatorCD`:

```
IpBatchManagerCD(
    output String channel activateBatchOut;   // → CoordinatorCD.activateBatchIn
    input  String channel batchResultIn;      // ← CoordinatorCD.batchDrainedOut
)->{
    IpBatchManagerModel signal stateModel;
    emit stateModel(new IpBatchManagerModel());
    pause;
    { … while(true){ model.tick(); pause; } }
    ||
    { … frame = model.nextActivate();
        if(!frame.equals("")){ send activateBatchOut(frame); } pause; … }
    ||
    { … receive batchResultIn;
        model.resultReceived((String)#batchResultIn); pause; … }
}
```

It sends `ACTIVATE|…` frames and receives `DRAINED`, `REJECTED`, `FAULT` or `RECOVERED`.

Per-bottle evidence travels separately: the coordinator offers each confirmed transition to
`TwinEventBus`; a consumer thread hands it to the database worker. **All database work runs
on that one worker thread** — never on a SystemJ thread or the Swing thread.

---

## 4. Files

Paths under `Integration/Zhiyuan/`.

| File | Role |
|---|---|
| `sysj/ip_batch_manager.sysj` | `IpBatchManagerCD`. |
| `sysj/coordinator_ip.xml` | Run profile that wires `IpBatchManagerCD` in as the batch source. |
| `sql/schema.sql` | The six tables. Applied automatically to a new database file. |
| `src/com/g7/ip/Db.java` | Opens the SQLite file; the only class that knows the JDBC URL. |
| `src/com/g7/ip/Dao.java` | Every SQL statement. |
| `src/com/g7/ip/DigitalTwinAssembler.java` | Mints bottle ids, attaches order/product/recipe at admission, appends one event per confirmed transition, records faults, counts completions. |
| `src/com/g7/ip/DeviationDetector.java` | Compares a bottle's recorded route with the expected one. |
| `src/com/g7/ip/TrackerFieldChange.java` | One confirmed change: workpiece, batch, location, operation, status. |
| `src/nz/.../coordinator/TwinEventBus.java` | Bounded queue (10,000) from the coordinator to the IP. |
| `src/nz/.../coordinator/DbWorker.java` | The single database thread. |
| `src/nz/.../coordinator/IpBatchManagerModel.java` | Model behind `IpBatchManagerCD`: wires everything, runs start-up recovery, answers GUI queries. |
| `src/com/g7/ip/gui/IpGuiFrame.java` | The four-tab window. Tabs 2–4 and the safety strip are IP; tab 1 is the GP's POS. |

---

## 5. Data model

| Table | One row per | Notes |
|---|---|---|
| `Recipes` | Formulation | Liquid A/B proportions and bottle type. |
| `Batches` | Production run | `RUNNING` → `COMPLETED` or `FAULT`. |
| `Orders` | Customer purchase order | `customer_po` is unique. |
| `OrderBatches` | (order, batch) pair | Allocated, admitted and completed quantities. |
| `BottleEvents` | Confirmed transition | **Append-only.** Status `DONE`, `FAULT` or `ABORTED`. |
| `Faults` | Device fault | `OPEN` → `RESOLVED`, with the operator's resolution text. |

**Bottle id:** `B-<batch>-<nnnn>`, e.g. `B-16-0003`. Minted by the IP and stable across runs;
the GP's run-local workpiece id (`WP-16-3`) is stored alongside it.

Only confirmed transitions are written. `BUSY` and other intermediate states are not.

---

## 6. Running

Build first: `ip_batch_manager.sysj` is compiled by the GP's **BuildAll** (README.md § 4).
The SQLite driver is vendored in `lib/`; `Zhiyuan/build/` must be writable.

| Launch config | Database | Mode | Use for |
|---|---|---|---|
| **`RunCoordinatorIpGui`** | `build/ip-gui.db` | Window | **Main demo**: traceability, recovery (§ 8.1, 8.3). |
| **`RunCoordinatorIpGuiHazard`** | `build/ip-gui.db` | Window | A sensor raises a hazard at 20 s, clears it at 26 s (§ 8.2). |
| **`RunCoordinatorIpFault`** | `build/ip-fault.db` | Window | The lid loader jams mid-batch (§ 8.4). |
| **`RunCoordinatorIp`** | `build/ip-dev.db` | Console | Orders wait for `go` — cross-order consolidation (§ 8.5). |
| `RunCoordinatorGpPos` | `build/gp-pos-demo.db` | Console | The GP's console POS; the IP persists underneath. |

All use `sysj/coordinator_ip.xml`. Database files are created in `Integration/Zhiyuan/build/`.

| Property | Effect |
|---|---|
| `ip.gui` | Open the four-tab window instead of console order entry. |
| `ip.autoActivate` | Each accepted order starts its own batch. Without it, type `go`. |
| `ip.database` | SQLite file. **Default: a new random file each launch** — set a path to keep data. |

---

## 7. Using the IP

All three IP tabs refresh every 2 seconds, and **keep working during a hold** — including
the permanent hold a device fault causes. Only order entry is blocked while the line is held.

### 7.1 Track Order (tab 2)

Choose a PO reference from the dropdown (every PO placed this session) and click **Track**.
It shows the order's state and a progress bar. When every bottle has reached the unloader the
status turns green: **COMPLETED**.

Progress is counted from bottles that actually reached the unloader, not read from a status
column.

### 7.2 Track Bottle (tab 3)

Pick a bottle from the dropdown (recently active bottles) or type one, and click **Look up**:

- the order, product and recipe attached when it was admitted;
- its journey — `loader`, `conveyor_in`, `filler`, `lid`, `capper`, `conveyor_out`,
  `labeller`, `unloader` — each marked with what the database records, with timestamps.
  Stations not yet reached show `PENDING`, so an interrupted bottle reads as an
  interrupted journey;
- the deviation verdict.

### 7.3 Deviation verdicts

| Verdict | Meaning |
|---|---|
| `matches expected sequence […]` | Every station, in order, `DONE`. |
| `fault recorded at <station>` | **Deviated.** A device faulted on this bottle. |
| `expected <station>:DONE at position <n> …` | **Deviated.** A station was skipped, repeated or out of order. |
| `aborted at <location> before completion — no recipe verdict applies` | **Not** a deviation. The run was stopped from outside before the bottle finished. |

If a bottle faulted and was then swept up by a reset, the fault is reported.

The check covers the route and fault history. **Liquid proportions are not checked**: the
filler plant echoes the commanded doses rather than measuring them.

The console prints a verdict for every bottle that reaches the unloader:

```
[DigitalTwin] B-16-0003 (WP-16-3): deviated=false -> matches expected sequence [...]
```

### 7.4 Faults History (tab 4)

Every device fault on file, newest first, with a count of open and resolved faults. Select a
row, click **Resolve selected fault**, and describe what was done; the fault becomes
`RESOLVED` with that text. This works during the hold the fault caused.

### 7.5 Console order entry (`RunCoordinatorIp`)

A fresh database is seeded with two recipes, printed at start-up:

```
[IpBatchManager] Recipes on file:
  1: PRODUCT_X 60%/40% (500ml)
  2: PRODUCT_Y 50%/50% (500ml)
```

You are then prompted field by field:

| Prompt | Example | Rule |
|---|---|---|
| `customer_po` | `PO-A1` | `[A-Za-z0-9_.-]`, 1–60, unique. Or type `go`. |
| `customer_id` | `CUST-A` | |
| `product_id` | `PRODUCT_X` | Must match the recipe's product. |
| `quantity` | `3` | > 0 |
| `bottle_spec` | `500ml` | Must match the recipe's bottle type. |
| `recipe_id` | `1` | A catalogue id, or `new` — then `doseA` and `doseB` (each ≥ 0, sum 1–100). An identical recipe on file is reused. |

---

## 8. Demonstrations

### 8.1 Traceability

1. Launch **`RunCoordinatorIpGui`**.
2. On **Place Order**, submit one line: `500ml`, 60 / 40, quantity 3.
3. On **Track Order**, select the PO and click **Track**. The bar reaches **COMPLETED**.
4. On **Track Bottle**, pick one of its bottles. Every station shows `DONE` with a
   timestamp; the verdict is `matches expected sequence`.
5. Stop the simulation, launch it again, and look the same bottle up. It is still there.

### 8.2 An interrupted bottle is not a deviation

1. Launch **`RunCoordinatorIpGuiHazard`** and immediately submit a line with quantity 8.
2. At 20 s the safety strip turns **HAZARD ACTIVE**, then **ON HOLD**; at 26 s the hazard clears.
3. While held, the three IP tabs still work — look up the order and a bottle.
4. Click **Reset / Resume Production**. The console prints
   `COORDINATOR SAFETY RESET -- line reconciled`.
5. On **Track Bottle**, pick a bottle from the interrupted batch. Its journey stops part way
   at an `ABORTED` step, with the verdict `aborted at … before completion — no recipe verdict applies`.
6. **Track Order** shows the order stopped, not stuck in production.

### 8.3 Recovery from an abrupt interruption

1. Launch **`RunCoordinatorIpGui`** and submit a line with quantity 8.
2. While bottles are on the line, kill the process: Eclipse Console view → red square.
3. Launch **`RunCoordinatorIpGui`** again. It reopens `build/ip-gui.db`, finds the batch the
   dead process left `RUNNING`, and prints:

   ```
   [IpBatchManager] Recovered batch 7 left RUNNING by a previous, abruptly-ended process:
   5 incomplete bottle(s) marked ABORTED, batch closed as FAULT.
   ```

4. On **Track Bottle**, finished bottles from that batch show a full `DONE` journey; the
   interrupted ones end in `ABORTED`.

Open bottles are removed rather than resumed, following the brief's rule for hazards.

### 8.4 A device fault and the fault log

1. Launch **`RunCoordinatorIpFault`** and submit a line with quantity 3.
2. The lid loader jams. The console shows `COORDINATOR HOLD LID_…` and
   `Batch <n> closed as FAULT: <k> incomplete bottle(s) marked ABORTED.`; the safety strip
   shows **ON HOLD**.
3. On **Faults History**, the lid fault is listed as `OPEN`.
4. Select it, click **Resolve selected fault**, and enter a resolution. It becomes `RESOLVED`.
5. On **Track Bottle**, the bottle at the lid station reports `fault recorded at lid`.
6. On **Place Order**, a new submission is refused — the line stays held until the
   simulation is restarted.

### 8.5 Cross-order consolidation

1. Launch **`RunCoordinatorIp`**.
2. Enter two orders for the same product — `PO-A1 · CUST-A · PRODUCT_X · 3 · 500ml · 1`,
   then `PO-B1 · CUST-B · PRODUCT_X · 2 · 500ml · 1`. Each is stored as `PENDING`.
3. Type `go`. Both are merged into **one batch of 5**; the first 3 bottles are attributed to
   `PO-A1` and the next 2 to `PO-B1`.

The evaluation of this feature is in the IP report, § 8.

---

## 9. Inspecting the database

The files are ordinary SQLite databases in `Integration/Zhiyuan/build/`. Open one with DB
Browser for SQLite, Navicat or `sqlite3`. Close any editor with unsaved changes before running
a simulation against the same file.

One bottle's history:

```sql
SELECT event_id, location, status, event_timestamp
FROM BottleEvents
WHERE bottle_id = 'B-16-0003'
ORDER BY event_id;
```

Progress of every order:

```sql
SELECT o.customer_po, o.quantity,
       SUM(ob.admitted_quantity)  AS admitted,
       SUM(ob.completed_quantity) AS completed
FROM Orders o JOIN OrderBatches ob ON ob.order_id = o.order_id
GROUP BY o.order_id
ORDER BY o.order_id;
```

Aborted bottles per batch:

```sql
SELECT batch_id, COUNT(DISTINCT bottle_id) AS aborted_bottles
FROM BottleEvents
WHERE status = 'ABORTED'
GROUP BY batch_id;
```

Open faults:

```sql
SELECT fault_id, bottle_id, device_name, fault_reason, fault_timestamp
FROM Faults
WHERE status = 'OPEN'
ORDER BY fault_timestamp DESC;
```

---

## 10. Troubleshooting

### 10.1 Place Order: `Rejected(system is in FAULT/HOLD)`

The line is held, so no new production is accepted. The IP tabs still work.

- After a safety hold, click **Reset / Resume Production** once the hazard has cleared.
- After a device fault, the hold lasts for the session — restart the simulation. The data
  is kept if `ip.database` points at the same file.

### 10.2 No tabs, or no data at all

Only `coordinator_ip.xml` wires `IpBatchManagerCD`, and only configs with `-Dip.gui=true`
open the window. The GP profiles (`RunCoordinator`, `RunCoordinatorReal`,
`RunCoordinatorFault`) have no database. Check the console for
`[IpBatchManager] Database ready: <path>`.

### 10.3 `[IpBatchManager] Could not open database`

`Integration/Zhiyuan/build/` is missing or read-only, or another program holds a write lock on
the file — close any database editor with unsaved changes. Nothing can be queried or stored
for the rest of that session.

### 10.4 Data disappeared between runs

`ip.database` was not set, so the launch created a new random file. Every shipped launch config
sets a fixed path.

### 10.5 An order was accepted, but nothing is produced

Without `-Dip.autoActivate=true` (as in `RunCoordinatorIp`), orders wait as `PENDING` — type
`go` at the `customer_po` prompt. `A batch is already in flight` means they will be picked up
when the current batch drains.

### 10.6 Console order `Rejected(…)`

The order did not match the recipe catalogue: `product_id` differs from the recipe's product,
`bottle_spec` differs from its bottle type (case matters: `500ml`), the `recipe_id` is unknown,
or the quantity is not an integer.

### 10.7 A completed bottle says `expected <station>:DONE at position <n>`

A genuine route deviation — the recorded history is in the message. An interrupted bottle
reports `aborted at …` instead, which is expected (§ 7.3).

### 10.8 An order is stuck in production after a fault

A fault should close its batch; look for
`Batch <n> closed as FAULT: <k> incomplete bottle(s) marked ABORTED`. If that line is missing,
the `FAULT` frame carried a non-numeric batch id, which only happens in the GP console
profiles.

### 10.9 The `Recipes` table grows every launch

Each start-up seeds `PRODUCT_X` and `PRODUCT_Y`, so a reused database gains two duplicate rows
per launch. Harmless — the newest matching row is used. Recipes created through the GUI or with
`new` are not duplicated.

### 10.10 `[DigitalTwin] Field change for unadmitted workpieceId=…`

A transition arrived for a bottle whose admission was never recorded. The event bus drops
events if its queue is ever full, and events arriving while the line is held are discarded,
so that bottle's history is incomplete. Not observed in normal runs.

---

## 11. Further reading

| Document | Contents |
|---|---|
| `IP_Final_Report.docx` | The Individual Project report, including design rationale and deviations from the conceptual design. |
| `README.md` | The Group Project: build, all run configurations, compendium. |
| `Zhiyuan/sql/schema.sql` | The schema. |
