# EABS — Group Project (Group 7)

COMPSYS 704 Project 1. A SystemJ simulation model of the Advantech Extended Automated
Bottling System (EABS) with an integrated Purchase Order System (POS).

This folder holds the **integrated, runnable system**: all three members' subsystems are
wired into one SystemJ configuration.

> **This is the Group Project README.** Zhiyuan's Individual Project (the persistent
> digital twin) lives in the same codebase and has its own README:
> **[`README_IP.md`](README_IP.md)**.

**Contents:** [1 Scope](#1-scope-gp-and-ip) · [2 Prerequisites](#2-prerequisites) ·
[3 Files](#3-repository-layout) · [4 Build](#4-build--run-this-first) ·
[5 Run](#5-running-the-applications) · [6 Operate](#6-operating-a-running-simulation) ·
[7 Compendium](#7-compendium) · [8 Troubleshooting](#8-troubleshooting) ·
[9 Tasks](#9-task-allocation)

---

## 1. Scope: GP and IP

The brief (§ 4.2) states: *"The POS System will be considered a part of the core ABS."*
Order entry is therefore **GP**. Everything that exists only because production history is
**persisted to a database** is **IP**.

| Component | Owner | |
|---|---|---|
| `CoordinatorCD` / `IntegratedCoordinator` | Zhiyuan | **GP** |
| `SafetyMonitorCD` and the hazard / clear / reset protocol | Zhiyuan | **GP** |
| Bottle loader, conveyor, rotary table, two-liquid filler (controllers + plants) | Tonny | **GP** |
| Lid loader, capper, labeller, unloader (controllers, plants, shims) | Eric | **GP** |
| `WorkpieceTracker` (run-local twin) and the EABS dashboard | Eric | **GP** |
| POS order validation and batch admission (`POS`, `BatchManager`) | Zhiyuan | **GP** |
| GUI tab 1 — **Place Order** | Zhiyuan | **GP** |
| SQLite schema, persistence layer, digital-twin assembly, deviation detection | Zhiyuan | IP |
| GUI tabs 2–4 — Track Order, Track Bottle, Faults History | Zhiyuan | IP |

The GP runs without any IP code being exercised: the `coordinator.xml`,
`coordinator_real.xml` and `coordinator_fault.xml` profiles have **no database at all**.

---

## 2. Prerequisites

| Requirement | Notes |
|---|---|
| **JDK 11 or newer** | Verified on Eclipse Adoptium JDK 11. The SystemJ compiler is driven through `javax.tools`, so a **JDK** is required — a JRE will fail. |
| **Eclipse** | Import **both** the `Zhiyuan` and `Eric` projects (§ 8.1). The `.launch` files are Eclipse run configurations. |
| **Heap** | `-Xmx768m` for the build, `-Xmx256m` for a simulation. |
| **TCP ports 30103–30105 free** | The three safety signals travel over loopback. Only one simulation may run at a time. |

Every library is vendored in `Zhiyuan/lib/`. Nothing is downloaded.

---

## 3. Repository layout

```
Integration/
├── README.md          ← this file (GP)
├── README_IP.md       ← Individual Project
├── Zhiyuan/           ← the integrated system — build and run everything from here
│   ├── sysj/            SystemJ sources (.sysj) and run profiles (.xml)
│   ├── src/             hand-written Java: state models, tooling, GUI
│   ├── generated-src/   sjc output — 26 clock-domain classes (never edit)
│   ├── tests/           IntegratedCoordinatorTest
│   ├── sql/             schema.sql (IP)
│   ├── lib/             vendored jars
│   ├── scripts/         optional PowerShell helpers
│   └── *.launch         Eclipse run configurations
├── Eric/
│   ├── src/main/java/   finishing-station Java (an Eclipse project reference)
│   ├── systemj/         finishing_devices.sysj, finishing_shims.sysj — compiled by BuildAll
│   └── docs/            device interface contract
└── Tonny/             original standalone project (reference only — see § 5.4)
```

### 3.1 SystemJ sources

`BuildAll` compiles **15** `.sysj` files: 13 in `Zhiyuan/sysj/` and 2 in `Eric/systemj/`.

| File | Clock domain(s) | Owner | Role |
|---|---|---|---|
| `coordinator.sysj` | `CoordinatorCD` | Zhiyuan | Central orchestrator. 14 parallel reactions: 7 station handshakes, plus tick, batch activation, result reporting, rotation and the 3 safety inputs. |
| `safety_monitor.sysj` | `SafetyMonitorCD` | Zhiyuan | Human-presence / environmental hazard input. Emits `safetyPermitLost`, `safetyPermitRestored`, `safetyResetRequested`. |
| `batch_manager.sysj` | `BatchManagerCD` | Zhiyuan | Console batch source — reads typed `ACTIVATE` frames. |
| `ip_batch_manager.sysj` | `IpBatchManagerCD` | Zhiyuan | Database-backed batch source used by the POS (IP code behind it). |
| `coordinator_harness.sysj` | `CoordinatorBatchHarnessCD`, `CoordinatorFaultHarnessCD` | Zhiyuan | Scripted batch sources for unattended runs. |
| `BottleLoaderController/Plant.sysj` | 2 CDs | Tonny | Bottle admission onto the input conveyor. |
| `ConveyorController/Plant.sysj` | 2 CDs | Tonny | Transfers into and out of the rotary table. |
| `RoteryTableController/Plant.sysj` | `RotaryTableController`, `RotaryTablePlant` | Tonny | 60° index of the six-position table. *(Filename keeps its historical misspelling.)* |
| `TwoLiquidFillerController/Plant.sysj` | 2 CDs | Tonny | Two-dose fill with overflow detection. |
| `Eric/systemj/finishing_devices.sysj` | 8 CDs | Eric | Lid loader, capper, labeller, unloader — controller + plant each. |
| `Eric/systemj/finishing_shims.sysj` | 4 CDs | Eric | Translate the coordinator's frame protocol to Eric's device interface. |

`Eric/systemj/` also contains `finishing_contract.sysj` and `finishing_shim_contract.sysj`.
They declare Eric's standalone test harnesses (`LidHarnessCD`, `CapperFlatHarnessCD`, …),
which stand in for the coordinator when his subsystem is tested alone. The integrated build
does not compile them.

### 3.2 Run profiles — `Zhiyuan/sysj/*.xml`

Every profile wires the **same 22 clock domains** and differs in exactly **one**: the
batch source.

| Profile | Batch source | Purpose |
|---|---|---|
| `coordinator.xml` | `CoordinatorBatchHarnessCD` | Unattended smoke test: a scripted batch runs itself. |
| `coordinator_real.xml` | `BatchManagerCD` | Console-driven production: type `ACTIVATE` frames. |
| `coordinator_ip.xml` | `IpBatchManagerCD` | POS / GUI: orders come from the database. |
| `coordinator_fault.xml` | `CoordinatorBatchHarnessCD` | As `coordinator.xml`, used with lid-fault injection. |

### 3.3 GP Java classes — `Zhiyuan/src/nz/ac/auckland/eabs/zhiyuan/`

| Class | Role |
|---|---|
| `coordinator/IntegratedCoordinator.java` | The coordinator's state machine, held behind a valued signal inside `CoordinatorCD`: phases, handshakes, frame validation, fault latching, safety reset. |
| `coordinator/SafetyMonitorModel.java` | Hazard state behind `SafetyMonitorCD`. |
| `coordinator/HazardSensorSimulator.java` | Optional timer-driven hazard sensor (`-Dsafety.autoSensor`). |
| `coordinator/BatchManagerModel.java` | State behind the console batch source. |
| `coordinator/SharedConsole.java` | The single stdin reader shared by every console-driven model. |
| `tooling/EclipseSystemJBuild.java` | The build and verification pipeline (§ 4). |
| `coordinator/TrackerPort.java`, `TwinView.java`, `StubTracker.java` | **Legacy, unused.** An early tracker-boundary sketch, kept for history. Neither the runtime nor the tests reference them. |

`com/g7/ip/*`, `coordinator/Ip*`, `DbWorker.java` and `TwinEventBus.java` are IP — see
`README_IP.md`. `POS.java` and `BatchManager.java` in `com/g7/ip/` are the GP's order
validation and admission logic.

> **`generated-src/` is compiler output.** Edit the `.sysj`, then rebuild.

---

## 4. Build — run this first

Nothing runs until the SystemJ sources are compiled into clock-domain classes.

**Eclipse:** `Run → Run Configurations → Java Application → BuildAll`

**Terminal** (in `Integration/Zhiyuan/`, Git Bash):

```bash
CP=$(ls lib/*.jar | tr '\n' ';')
mkdir -p build/toolingclasses
javac -d build/toolingclasses -cp "$CP" -sourcepath "src;../Eric/src/main/java" \
  src/nz/ac/auckland/eabs/zhiyuan/tooling/EclipseSystemJBuild.java
java -cp "build/toolingclasses;$CP" nz.ac.auckland.eabs.zhiyuan.tooling.EclipseSystemJBuild
```

It runs three stages:

1. Compile the hand-written Java helpers to Java 8 bytecode.
2. Translate the 15 `.sysj` files with the course SystemJ compiler. **Several minutes.**
3. Compile and check all **26** generated clock-domain classes.

Success:

```
[3/3] Compile and check all 26 generated CD classes
ECLIPSE SYSTEMJ BUILD PASSED
```

Then in Eclipse: **F5** on both projects and **Project → Build Project**.

| You changed… | Re-run BuildAll? |
|---|---|
| a `.sysj` or `.xml` file | **Yes** |
| only `.java` files | No — F5 and rebuild in Eclipse |

---

## 5. Running the applications

Every simulation is started the same way — the SystemJ runtime loads a profile:

```
main class:         com.systemj.SystemJRunner
program argument:   sysj/<profile>.xml
working directory:  Integration/Zhiyuan
```

In Eclipse, pick the matching run configuration.

### 5.1 GP simulations

| Launch config | Profile | What you get |
|---|---|---|
| **`RunCoordinator`** | `coordinator.xml` | Scripted batch, unattended, with Eric's dashboard. The fastest end-to-end check. |
| **`RunCoordinatorReal`** | `coordinator_real.xml` | **Console GP demo.** You type `ACTIVATE` frames (§ 6.1). |
| **`RunCoordinatorFault`** | `coordinator_fault.xml` | Headless. The lid loader jams; the coordinator must return `FAULT`, never `DRAINED`. |
| **`RunCoordinatorGpPos`** | `coordinator_ip.xml` | POS order entry at the console; each accepted order starts production immediately. |
| **`RunCoordinatorIpGui`** | `coordinator_ip.xml` | **Main demo.** The four-tab application (tab 1 = GP POS) plus Eric's dashboard. |
| **`RunCoordinatorIpGuiHazard`** | `coordinator_ip.xml` | As above; a simulated sensor raises a hazard 20 s after launch and clears it 6 s later. |

VM arguments, exactly as in the `.launch` files:

| Launch config | VM arguments |
|---|---|
| `RunCoordinator` | `-Xmx256m -Deric.finishing.flatSimulation=true -Dcoordinator.gui=true` |
| `RunCoordinatorReal` | `-Xmx256m -Deric.finishing.flatSimulation=true -Dcoordinator.gui=true` |
| `RunCoordinatorFault` | `-Xmx256m -Djava.awt.headless=true -Deric.finishing.flatSimulation=true -Deric.finishing.testMode=true -Dcoordinator.testLidFault=true` |
| `RunCoordinatorGpPos` | `-Xmx256m -Deric.finishing.flatSimulation=true -Dcoordinator.gui=true -Dip.database=build/gp-pos-demo.db -Dip.autoActivate=true` |
| `RunCoordinatorIpGui` | `… -Dip.database=build/ip-gui.db -Dip.autoActivate=true -Dip.gui=true` |
| `RunCoordinatorIpGuiHazard` | `… as IpGui … -Dsafety.autoSensor=true -Dsafety.hazardAfterMs=20000 -Dsafety.clearAfterMs=6000` |

The remaining `RunCoordinatorIp` and `RunCoordinatorIpFault` are IP runs — see `README_IP.md`.

### 5.2 Build and test applications

| Launch config | Main class | Purpose |
|---|---|---|
| **`BuildAll`** | `tooling.EclipseSystemJBuild` | Build (§ 4). |
| **`VerifyIntegration`** | `tooling.EclipseSystemJBuild --test` | Build, then run the scripted normal and fault scenarios and the archive checks. **Stop all simulations first** (§ 8.4). |
| **`CoordinatorTests`** | `coordinator.IntegratedCoordinatorTest` | Headless model tests of the coordinator's state machine and archive. Fast; no SystemJ build needed. |

### 5.3 GP system properties

| Property | Effect |
|---|---|
| `coordinator.gui` | Show Eric's EABS dashboard. |
| `eric.finishing.flatSimulation` | Use the flat finishing simulation (every demo config sets it). |
| `eric.finishing.testMode` + `coordinator.testLidFault` | Inject a lid-loader jam. Both are required. |
| `coordinator.archive` | File the tracker archives completed bottles to (default: a fresh temp file). |
| `safety.autoSensor` | Enable the timer-driven hazard sensor. |
| `safety.hazardAfterMs` / `safety.clearAfterMs` | Its timing. |
| `ip.autoActivate` | POS: each accepted order starts its own batch immediately. |

### 5.4 Standalone subsystem projects

`Tonny/` keeps its pre-integration launches (`RunController`, `RunPlant`, `RunEABS`) and
`Eric/` its own scripts. They run one subsystem against stub partners. **They are not the
integrated system and are not used in the demo.** The integrated copies of Tonny's
`.sysj` sources are the ones in `Zhiyuan/sysj/`.

---

## 6. Operating a running simulation

### 6.1 Console batch entry — `RunCoordinatorReal`

Type one 8-field frame per batch:

```
ACTIVATE|<batchId>|<recipeId>|<productId>|<quantity>|<doseA>|<doseB>|<orderId>
ACTIVATE|B1|R1|PRODUCT_X|8|20|80|ORDER-1
```

| Field | Rule |
|---|---|
| `batchId`, `recipeId`, `productId`, `orderId` | `[A-Za-z0-9_.-]`, 1–60 characters. `batchId` must be new this run. |
| `quantity` | 1–10000 |
| `doseA`, `doseB` | Integers ≥ 0, each ≤ 100, sum 1–100. **Simulation units**, not mL — the filler holds 100. |

The coordinator answers on the console with one of:

```
DRAINED|<batchId>                          batch complete, line idle
REJECTED|<batchId>|INVALID_BATCH_OR_RECIPE malformed frame, line still idle — send a corrected one
FAULT|<batchId>|<reason>                   line latched in HOLD
```

Send the next batch only after the previous one's result has arrived.

### 6.2 Place Order — GUI tab 1 (`RunCoordinatorIpGui`)

The customer never types a product code. Each line is:

| Column | Input |
|---|---|
| Capacity | Dropdown: `250ml`, `500ml`, `1L` |
| A% / B% | Liquid A and B percentages (default 60 / 40) |
| Quantity | Bottles (default 1) |
| Formulation | Filled in by the system after submission |

- **Add line** / **Remove selected line** edit the cart. The customer field defaults to
  `UoA CS704 G7`.
- **Submit order lines** submits every line in one click. Each line becomes its own purchase
  order with a generated reference `PO-<year>-<nnnn>`.
- The Formulation column then reports `New formulation — PO-…`, `Existing formulation — PO-…`,
  or the rejection reason. The product identity is derived from capacity and mix
  (`FORM-500ML-60-40`), so an identical order always maps to the identical recipe.
- The button stays disabled for a moment at start-up while the PO counter is seeded from
  the references already on file.

### 6.3 Safety

At the console of any profile (every one wires `SafetyMonitorCD`):

| Command | Effect |
|---|---|
| `hazard` | Hazard detected. The coordinator latches `HOLD`; no new jobs or indices. |
| `clear` | Hazard condition gone. Production does **not** resume on its own. |
| `reset` | Operator acknowledgement. Every open bottle is archived `ABORTED`, then the line returns to `WAIT_ORDER`. Accepted only once cleared. |

In the GUI these are the **Clear Hazard** and **Reset / Resume Production** buttons on the
safety strip, which shows `SAFE`, `HAZARD ACTIVE` or `ON HOLD`.

A machine fault is **not** recoverable by `reset` — only a safety hold is. Restart the
simulation after a machine fault.

---

## 7. Compendium

This section describes the design approach, the main design decisions, and every place the
implementation differs from the initial specification in the brief.

### 7.1 Design approach

**A centralised coordinator over intelligent machines.** One clock domain,
`CoordinatorCD`, makes every system-wide decision: which bottle goes where, when the table
may index, when a batch is complete. Each machine is a controller CD paired with a plant CD,
and a controller is responsible only for safely executing one machine cycle. This keeps
actuator logic out of the coordinator without the synchronisation problems full
decentralisation creates for rotary indexing and batch sequencing.

**One uniform station protocol.** Every coordinator-to-controller link is the same three
String channels — job, report, acknowledgement — carrying the same lifecycle:

```
READY  →  START|job|workpiece|station|operation[|params]
       →  BUSY  →  DONE|…|OK|key=value…  or  FAULT|…|reason
       →  ACK   →  READY
```

Seven stations therefore need one handshake implementation, not seven.

**Evidence before state.** A bottle's location or operation is updated only after the
responsible controller reports `DONE` *with* the evidence keys that station must supply
(`bottlePlaced`, `bottleMoved`, `liquidA`/`liquidB`, `safe`, a matching label payload…).
A command having been sent is never treated as the operation having happened.

**Thin SystemJ reactions over a Java state model.** Each CD's `.sysj` file holds only flat,
parallel I/O reactions; the validated state lives in one Java object passed to those
reactions through a single valued local signal. The same pattern is used for the
coordinator, the safety monitor and both batch sources.

**The coordinator's phase machine:**

```
WAIT_ORDER → DISPATCH → WAIT_OPERATIONS → WAIT_OUTPUT_TRANSFER
           → WAIT_INPUT_TRANSFER → WAIT_ALIGNMENT → DISPATCH …
any fault  → HOLD   (absorbing)
```

Each `WAIT_*` phase advances only once every dispatched station has reported `DONE` and is
`READY` again. Downstream work is released before upstream work, so the output side is
always cleared before the table indexes.

### 7.2 Design decisions

| Decision | Alternative rejected | Why |
|---|---|---|
| Centralised coordinator | Fully decentralised peer controllers | Rotary indexing needs a single global view of all six positions; distributed agreement on "every station is done" adds protocol with no benefit. |
| Rendezvous **channels** for every station link | One-instant signals | A channel send waits for its partner. A signal exists for one tick and is lost if the partner is between ticks. |
| Rotary handshake as channels with a transaction id (`ROTATE\|n` / `DONE\|n`) | The original signal pair over loopback TCP | The signal version was the only networked station link and the only one that ever timed out: a reproducible `TABLE_ALIGNMENT_TIMEOUT` at quantity 9. The id lets a repeated request be acknowledged without rotating the table twice. |
| Flat reactions + a Java state model | Deeply nested parallel / abort structure in SystemJ | The nested form exhausted the course compiler's heap and sometimes produced a zero exit code on failure. |
| Strict `ACTIVATE` validation → `REJECTED` | Filling in default doses or IDs | A wrong recipe must never be produced silently. The coordinator invents no metadata. |
| Any fault latches `HOLD` permanently | Automatic retry or reset | Retrying risks repeating an operation on a bottle; clearing occupancy risks losing one. A person decides. |
| Safety recovery needs `clear` **and** `reset` | Resume as soon as the sensor clears | The brief requires opened bottles to be removed before resumption; an explicit acknowledgement is the point where that happens. |
| `RECOVERED\|<batch>` frame on the existing result channel | A new channel or signal | A batch source that latched "halted" on `FAULT` had no way to learn a reset happened. Reusing `batchDrainedOut` adds a message, not a wire. |
| 15-second per-station timeout → `HOLD` | Waiting indefinitely | A silent station must surface as a fault, not a hang. |
| Serialise conveyor transfers: output first, then input | Independent transfers | One conveyor serves both ends; freeing the exit first prevents a deadlock where the table cannot index. |
| Shift all six table slots atomically on alignment | Moving slots one by one | One physical 60° index moves every bottle at once; partial shifts misrepresent the machine. |
| POS as a Swing tab in the same process | A separate web service | Keeps one process and one database, and lets the POS call the batch source directly. |
| Product derived from capacity + mix | Customer-entered product codes | The brief defines a product *by* its bottle size and liquid mix, so the system can derive it. |

### 7.3 Deviations from the initial specification

| The brief specifies | This implementation | Reason / consequence |
|---|---|---|
| Two safety conditions: human presence, and each ambient quantity (temperature, humidity, light) within range | One generic hazard input. Presence and out-of-range readings both raise the same `safetyPermitLost`; no per-quantity thresholds are modelled | The required *response* is identical for both (§ 4.1.2), so one input exercises it fully. |
| After a hazard, resume "from a predefined point", with opened bottles removed | Open bottles are archived `ABORTED` and the line resumes at `WAIT_ORDER`. The rest of the interrupted batch is **not** produced automatically | The predefined point is "ready for the next batch". The interrupted order must be placed again. |
| Stopping on a hazard | `HOLD` blocks new jobs and table indices. It is **not** an emergency stop: the flat station interface has no STOP line, so a cycle already dispatched may finish | Documented limitation of the station protocol. |
| Fill "with the predefined amount"; liquids mixed from "two or more" | Two liquids. Doses are integers in **simulation units** (sum ≤ 100, the filler's capacity), not mL | Two liquids demonstrate the recipe mechanism; units keep the plant model simple. |
| Filler dispenses and the fill is confirmed | The controller validates `dose1Done`, `dose2Done` and overflow, but the plant **echoes the commanded amounts** as `liquidA`/`liquidB` — there is no independent level measurement | Measured-fill verification is not possible with this plant model. |
| Photo eyes detect bottles at each table position | The rotary plant models only the index motion and reports alignment. Occupancy of the six positions is tracked in the coordinator's software | Alignment is the only plant evidence used for the table. |
| Label extension: product name/ID, serial, ingredients, completion time | Label payload is `workpieceId:batchId`, and the coordinator verifies the printed payload matches (`LABEL_MISMATCH` otherwise) | Proves the labelling handshake; a richer payload is a data change only. |
| Sort extension: completed bottles to per-batch storage, plus storage for discarded ones | One output collection point. Aborted bottles are routed to a reject location **logically** in the tracker; there is no physical diverter | Per-order attribution is done in software. |
| POS launched "on line", with notification to the customer on completion | A dedicated desktop application. Completion is visible by tracking the order; nothing is pushed to the customer | Push notification (e-mail etc.) is out of scope for a local simulation. |
| Batches processed by the line | One batch at a time; the line fully drains before the next batch starts, even for an identical formulation | The coordinator holds one recipe at a time. |
| A loading mechanism that avoids overloading the line | The coordinator admits a new bottle only when the input slot and table position 1 are both free | Back-pressure lives in the coordinator, not the loader. |
| Appendix device clock domains with their own signal interfaces | 22 CDs per profile. Controller-to-plant links keep device-level Boolean/Integer channels; the coordinator-facing interface is the uniform String protocol of § 7.1 | One protocol for seven stations. |

### 7.4 Known limitations

- **The safety signals still use loopback TCP** (ports 30103–30105) — the same transport
  class as the retired rotary signals. They have not failed in testing, but they are the
  remaining fragile link.
- **One simulation per machine at a time**, because of those ports.
- **The conveyor is symbolic**: transfers are serialised, not collision-accurate geometry.
- **Not hard real-time**: the tracker writes its file archive on the simulation thread
  when a bottle completes. Adequate for this workload; a larger one would need an
  asynchronous persistence boundary.

---

## 8. Troubleshooting

### 8.1 Compile errors about missing `nz.ac.auckland.eabs.eric.*`

The Zhiyuan project references Eric's code as an **Eclipse project reference**
(`<classpathentry kind="src" path="/Eric"/>`). Both projects must be in the same
workspace and Eric's must be named exactly `Eric`.

*Fix:* `File → Import → Existing Projects into Workspace`, select `Integration/`, import
both, then `Project → Clean`. From a terminal, add Eric's tree to the source path — it is
`src/main/java`, not `src`:

```bash
-sourcepath "src;generated-src;../Eric/src/main/java"
```

### 8.2 `ClassNotFoundException: CoordinatorCD` (or any CD)

`generated-src/` is empty or stale. Run **BuildAll**, then F5 and rebuild.

### 8.3 Build fails with *"expected 26 generated CD classes"*

The SystemJ compiler did not produce every clock domain — almost always a syntax error in a
`.sysj` file. Stage 2 prints each file as it translates; **the last file printed before the
failure** is the culprit. If you deliberately added or removed a CD, update the expected
count in `tooling/EclipseSystemJBuild.java`.

### 8.4 `SystemJ port 30103 is unavailable`

A previous simulation still holds the safety ports. Stop every process in Eclipse's
Console view (open the dropdown — there may be more than one), then retry. This is also
why `VerifyIntegration` refuses to start while a simulation is running.

### 8.5 `COORDINATOR HOLD <reason>` and the line stops

Intended behaviour, not a crash.

- `SAFETY_PERMIT_LOST_MANUAL_RECOVERY_REQUIRED` → `clear`, then `reset`.
- `TIMEOUT_<station>`, `<station>_<fault>`, `MISSING_COMPLETION_EVIDENCE_<station>`,
  `LABEL_MISMATCH`, `MISMATCHED_REPORT_<station>` → a machine-level fault. Restart the
  simulation; `reset` will not clear it.

### 8.6 `REJECTED|…|INVALID_BATCH_OR_RECIPE`

The frame broke a rule in § 6.1. The usual causes: 7 or 9 fields instead of 8; doses summing
over 100; a `batchId` already used this run. The line is still idle — send a corrected frame.

### 8.7 `COORDINATOR HOLD TABLE_ALIGNMENT_TIMEOUT`

Fixed by the rotary channel conversion (§ 7.2). A healthy run prints matching pairs:

```
ROTATE request received, rotation=7
Table aligned, rotation=7
```

If you see this timeout again, check that `generated-src/` was rebuilt after the fix. If
`ROTATION_REQUEST_SEQUENCE_ERROR` or `COORDINATOR ROTATION_DONE_MISMATCH` appears, the
coordinator and the rotary controller disagree about the sequence — keep the log.

### 8.8 The console says ready, but the POS still reports a hold

Fixed by the `RECOVERED|<batch>` frame (§ 7.2). If it recurs, the batch source never
received that frame — check the profile wires `SafetyMonitorCD` and that `reset` was
accepted (`COORDINATOR SAFETY RESET -- line reconciled`).

### 8.9 Place Order: `database error: customer_po … UNIQUE`

PO references are generated by the GUI. The counter used to restart at 1 on each launch, so
a second session against the same database collided. It is now seeded from the highest
reference on file. If it recurs, point `-Dip.database` at a new file.

### 8.10 Place Order: the submit button stays disabled

It is waiting for the PO counter to be seeded from the database. If it never enables, the
console will show `[IpBatchManager] Could not open database` — check that
`Zhiyuan/build/` exists and is writable.

### 8.11 The build is slow

Stage 2 genuinely takes several minutes; `coordinator.sysj` is the largest source. Only
re-run BuildAll after a `.sysj` or `.xml` change.

### 8.12 No window appears

`RunCoordinatorFault` is deliberately headless. Every other simulation shows Eric's
dashboard; the four-tab application opens only with `-Dip.gui=true` — the two `IpGui`
configs and `RunCoordinatorIpFault`.

---

## 9. Task allocation

| Member | Responsibility |
|---|---|
| **Tonny** | Bottle loader, conveyor, rotary table and two-liquid filler: controllers and plant models, including the hardened rotary handshake. |
| **Eric** | Lid loader, capper, labeller and unloader, the shim layer, `WorkpieceTracker`, and the EABS dashboard. |
| **Zhiyuan** | Production coordinator, safety monitor, POS and batch admission, system integration and the XML run profiles. Individual Project: see `README_IP.md`. |

---

## 10. Further reading

| Document | Contents |
|---|---|
| `README_IP.md` | The Individual Project: persistent digital twin. |
| `GP_Final_Report.docx` | Group Project final report. |
| `Zhiyuan/COORDINATOR_INTEGRATION.md` | Integration history. Parts pre-date the safety wiring and the rotary fix; this README is current. |
| `Zhiyuan/README.md` | Coordinator module notes. |
| `Eric/docs/INTEGRATION.md` | Eric's device interface contract and evidence keys. |
