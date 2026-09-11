# Dynamic finishing SystemJ contract

Status: implemented and tested in Eric's isolated subsystem on 11 September
2026. This is the proposed Eric-side interface, **not a group-wide agreement**
and not evidence that the complete EABS has been integrated.

## What runs

- `systemj/finishing_devices.sysj`: eight reusable CDs: a controller and a
  separate plant for lid loading, capping, labelling and unloading. No fixed
  job IDs, test-plan imports, POS policy or IP dependency.
- `systemj/finishing_contract.sysj`: four test-harness CDs, one per pair.
- `config/finishing-contract.xml`: twelve CDs connected by local String-valued
  rendezvous channels. The harnesses replace coordinator peers for testing only.
- `src/main/java/.../systemj/FinishingWire.java`: frame validation and encoding.
- `FinishingCycle.java`: each controller's correlated job, permission, terminal
  latch and evidence-validation data. It does not invoke the plant.
- `FinishingPlantEndpoint.java`: independent plant phase, fault and sensor model.
  It reuses device-specific precondition/evidence definitions in `plants/`.

SystemJ owns the concurrent reactions, channel transfers, cycle sequencing and
plant delays. Each controller has a control-message reaction and a job-cycle
reaction. They reference one per-CD `FinishingCycle` through a local valued
signal, emitted at initialization and read after a `pause`. No static global
registry or shared object between clock domains is used. Java synchronized
methods protect this local state; they are not a replacement for CD channels.

The job reaction is a flat FSM: advertise READY, accept a job, exchange plant
steps, report the terminal result, then wait for ACK/reset. This avoids nested
rendezvous-loop expansion and cross-reaction variable-renaming problems in the
supplied legacy compiler. Do not fix these issues by editing generated Java.

## Coordinator-facing channels

Use the same names on each controller CD, with distinct peer channels for each
machine. Exact existing connections can be copied from the test XML.

| Controller port | Direction | Message / purpose |
| --- | --- | --- |
| `jobIn` | Input | Correlated `START` with operation data |
| `reportOut` | Output | `READY`, `BUSY`, `DONE`, `FAULT`, `REJECTED`, `ACK_REJECTED`, `CLEARED` |
| `ackIn` | Input | `ACK`, `CLEAR_FAULT`, `RESET` for the latched job |
| `controlIn` | Input | `PERMIT`, `STOP`, `CLEAR_STOP`, independent of the job sequence |
| `controlReplyOut` | Output | `CONTROL_ACK`, `CONTROL_DUPLICATE`, `CONTROL_REJECTED` |
| `plantCommandOut` | Output to matching plant only | `BEGIN`, `STEP`, `ABORT`, `CLEAR`, `RESET` |
| `plantEvidenceIn` | Input from matching plant only | Correlated phase and sensor evidence |

Plant ports are `commandIn` and `evidenceOut`. Do not route the coordinator or
GUI directly to them. Replace only harness connections when assembling the GP;
keep the controller-to-plant pairs separate.

The coordinator must drain reports and control replies while other reactions
dispatch jobs or controls. These are rendezvous channels, not buffered queues.
Sending START before receiving READY, sending a new START while DONE/FAULT is
latched, or issuing multiple control requests without consuming replies can
deadlock a sequential caller. The isolated harness demonstrates the ordering;
it is not a full production scheduler.

## START and data encoding

```text
START|jobId|workpieceId|machineId|operation|key=value|key=value
```

`jobId` identifies one operation attempt, not a bottle. `workpieceId` follows
the bottle across machines. IDs use 1–100 ASCII letters, digits, `_ . : -`.
Frames are limited to 16,384 characters. Duplicate keys, unknown job fields,
invalid booleans/numbers and wrong machine/operation are rejected.

Only **data values** use UTF-8 URL-form encoding (`URLEncoder`/`URLDecoder`):
`|` becomes `%7C`, `=` becomes `%3D`, literal `+` becomes `%2B`, spaces become
`+`. Do not encode the whole frame or split a decoded label as frame fields.

| Machine / operation | Required data that must be true | Other required data | Completion evidence |
| --- | --- | --- | --- |
| `LidLoaderController` / `PLACE_LID` | `bottlePresent`, `filled`, `lidAvailable` | — | `lidPresent`, `placementComplete`, `armHome` |
| `CapperController` / `CAP_BOTTLE` | `bottlePresent`, `lidPresent` | — | `capSecured`, `clampHome`, `gripperHome`, `cycleComplete` |
| `LabelerController` / `APPLY_LABEL` | `bottlePresent`, `manufacturingComplete` | Nonblank `labelPayload` | `bottleStopped`, `labelApplied`, `bottleReleased`, matching `printedPayload` |
| `UnloaderController` / `UNLOAD` | `bottlePresent`, `collectionAvailable` | — | `bottleCollected`, `outputPathClear` |

Examples (one complete frame per line):

```text
START|LID-01|WP-42|LidLoaderController|PLACE_LID|bottlePresent=true|filled=true|lidAvailable=true
START|CAP-01|WP-42|CapperController|CAP_BOTTLE|bottlePresent=true|lidPresent=true
START|LABEL-01|WP-42|LabelerController|APPLY_LABEL|bottlePresent=true|manufacturingComplete=true|labelPayload=WP-42%7CBATCH-3
START|OUT-01|WP-42|UnloaderController|UNLOAD|bottlePresent=true|collectionAvailable=true
```

These booleans seed the current isolated plant's preconditions. They are not
live measurements from an integrated conveyor/table model. The group still
needs to connect authoritative occupancy/product state when assembling the
whole plant. Completion evidence is generated by the separate plant after
its simulated phases, never just by sending START.

Optional `timeoutTicks` is an integer 1–1000 (default 20). Despite the legacy
field name, this endpoint interprets it as a **STEP-response/poll budget**, not
a wall-clock duration or number of scheduler ticks. Agree final naming/units
with Tony and Tonny before adapting their coordinator.

## Lifecycle and acknowledgements

1. Receive `READY|machineId`. This means the START receiver is available;
   initial safety permission is still false.
2. Send `PERMIT|uniqueRequestId|true` on `controlIn`, consume its control reply.
3. Send START. A validated, permitted job returns
   `BUSY|jobId|workpieceId|machineId`, then a correlated terminal report.
4. A rejected START returns `REJECTED|jobId|workpieceId|machineId|reason=...`,
   then READY; it never sends BUSY or starts a plant cycle. Invalid IDs may be
   reported as UNKNOWN.
5. Successful completion returns
   `DONE|jobId|workpieceId|machineId|phase=...|safe=true|...sensorEvidence`.
   Send `ACK|jobId|workpieceId` on `ackIn`; READY releases the next job.
6. A failed accepted job returns
   `FAULT|jobId|workpieceId|machineId|safe=true/false|reason=...|...`.
   Ordinary ACK does not clear a fault. Wrong/stale ACK returns ACK_REJECTED
   correlated to the currently latched job and leaves it latched.

Both job and workpiece identity must match plant evidence and acknowledgements.
DONE additionally requires the expected terminal phase, safe/inactive plant,
required sensors and (for labelling) exact label content. A stale or incomplete
plant result triggers abort; it cannot produce DONE.

Accepted job IDs are remembered for this controller instance's lifetime.
Repeating an old START after ACK/reset is rejected and does not run another
cycle. Rejected jobs have not executed and are not in this accepted-ID set.
This is in-memory deduplication, **not restart recovery or a durable archive**.

## Safety and fault reset

```text
PERMIT|requestId|false
STOP|requestId
PERMIT|newRequestId|true
CLEAR_STOP|anotherRequestId
```

Permit loss latches a stop and active-job failure. STOP does the same without
changing the permission value. The separate control reaction receives these
while the job reaction is processing the plant; restoring permission alone
does not erase a fault or retry a bottle. CLEAR_STOP requires permission and
also does not reset a faulted job. Each accepted control ID is remembered;
replaying an old grant cannot reapply it after safety loss. Same ID with a
different payload is rejected. Use new request IDs for new intended actions.

On an active failure, the job reaction sends ABORT at its next plant-exchange
boundary. FAULT reports `safe=true` only after matching ABORTED evidence confirms
safe/inactive state. Bad abort evidence reports `safe=false` and denies reset.
A stop after BUSY but before BEGIN receives a correlated no-cycle-started abort
confirmation and does not actuate the previous bottle.

After resolving the hazard, restoring permission and clearing the stop latch:

```text
CLEAR_FAULT|faultedJobId|workpieceId
```

The model performs a simulated repair while quiescent, returns CLEARED, but
the controller remains FAULT. Then:

```text
RESET|faultedJobId|workpieceId
```

Only matching safe plant RESET evidence permits READY. RESET before repair is
rejected. The group must assign who may request repair/reset. This finite safe
return/repair model is not a hardware safety certification or proof of a
physical machine's recovery sequence.

Resetting a controller does **not** decide what happens to its faulted bottle.
The coordinator must inspect/reject/hold it according to group policy; issuing
a new job ID for the same bottle is not automatically safe. IP retry/rerouting
must remain a separate policy layer.

## Timing and remaining limitations

The plant CD delays each STEP and ABORT by 10 ms. Nominal successful phase
counts are lid 3, capper 4, labeller 3, unloader 2. These are simulation settings,
not measured machine timings. Existing Java plant definitions calculate planned
terminal evidence at BEGIN; the endpoint withholds it until the timed phases
finish. This is an abstract plant, not a detailed mechanical model.

The timeout test covers a responsive plant that reports no progress. If a
plant/channel disappears completely, the job reaction can remain blocked in a
rendezvous; the independent control reaction can latch STOP, but cannot cancel
that blocked exchange. A group-level communication watchdog/cancellable protocol
is still needed before claiming missing-channel fault tolerance or bounded
end-to-end safety latency. The test process's 30-second deadline detects a hung
test; it is **not** that production watchdog.

No live central coordinator, POS, tracker-update adapter, rotary reservation or
dashboard publisher is connected by this change. The Java subsystem/tracker/GUI
tests remain separate. Do not report these harness outputs as a full-line demo.

## Build, run and test evidence

Use PowerShell **7+**, a JDK with `java` and `javac` on PATH, and the supplied
course SystemJ JAR directory. From the `704GP1` checkout:

```powershell
pwsh -NoProfile -File .\Eric\scripts\build-and-test-systemj.ps1
# If the script cannot locate the lab libraries:
pwsh -NoProfile -File .\Eric\scripts\build-and-test-systemj.ps1 -SystemJLibPath 'D:\path\to\lab\lib'
```

This first runs all Java subsystem tests (including 348 new endpoint checks),
compiles both `.sysj` files using the course compiler, compiles their generated
Java, and executes all twelve CDs. It requires a successful process exit and:

```text
ERIC SYSTEMJ LID CONTRACT PASSED: 71 checked channel responses
ERIC SYSTEMJ CAPPER CONTRACT PASSED: 71 checked channel responses
ERIC SYSTEMJ LABELER CONTRACT PASSED: 71 checked channel responses
ERIC SYSTEMJ UNLOADER CONTRACT PASSED: 71 checked channel responses
ERIC SYSTEMJ DYNAMIC FINISHING ACCEPTANCE PASSED
ERIC SYSTEMJ BUILD AND CONTRACT TEST PASSED
```

The first four lines may appear in a different order. The successful runtime
log is `Eric/build/systemj-dynamic-last.log`; check its timestamp, since a failed
new run does not replace the previous successful log. Generated code/classes
and logs remain ignored under `build/`.

The Java suite and real channel tests cover multiple arbitrary IDs, wrong ACKs,
duplicate START, malformed jobs, precondition failure, jam, no-progress timeout,
stale evidence, missing terminal sensors, active STOP and safety loss, reset
denial, explicit repair/reset, and successful work after reset. Additional Java
checks exercise stale control grants and unconfirmed safe-stop evidence.

Fault injection uses `simMode=FAULT`, `STALL`, `BAD_CORRELATION` or
`MISSING_EVIDENCE`, accepted only with JVM property
`-Deric.finishing.testMode=true`. The test script sets it; production integration
must omit it. The test harness class exits the JVM after all four plans pass;
never include the harness CDs in the group's production XML.

Eclipse **Eric Tests** checks Java logic only. Passing it is useful but does not
compile `.sysj` or execute its channels. Use the script above for this slice.

## Next group integration checkpoint

1. Agree the wire fields, permission owner, report-drain reactions and reset
   authority with Tony/Tonny; adapt their current stubs, not their branch history.
2. Connect one coordinator job to one real pair (lid loader first), proving
   READY → START → BUSY → confirmed DONE → ACK → READY with matching IDs.
3. Forward each accepted dispatch/report once to the authoritative tracker;
   translate wire evidence into `FinishingJob`/`MachineReport`, and publish a real
   `DashboardState`. REJECTED is not a dispatched bottle's completion.
4. Add the other finishing pairs, occupancy/table guards and POS batch counts;
   then test the full line, including fault/hold/reset without accidental retry.

The GP controller/plant scope remains separate from IP database, deviation
detection and fault-tolerant routing extensions.
