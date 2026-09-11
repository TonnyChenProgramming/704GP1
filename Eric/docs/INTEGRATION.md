# Eric subsystem integration contract

This file records the current proposed boundary between Eric's GP work package
and the shared EABS. It describes implemented interfaces, but it does not turn
unconfirmed design choices into group decisions.

## Ownership boundary

The shared Production Coordinator decides when an operation may start. Eric's
local controllers decide how their own machine cycle executes. Each controller
talks only to its matching plant model. The Workpiece Tracker accepts confirmed
reports, and the Visualization Bridge exchanges snapshots and operator requests
with the coordinator-facing integration layer.

The isolated SystemJ model uses four harness CDs to stand in for the shared
coordinator. During integration, replace those harness connections with the
group coordinator's channels; do not copy coordinator policy into Eric's local
controllers.

## Proposed correlated message contract

The executable SystemJ smoke model currently uses pipe-delimited String-valued
channels because the supplied legacy SystemJ compiler cannot reliably process
the modern immutable Java message classes on the installed JDK.

```text
START|jobId|workpieceId|machineId|operation
READY|machineId
BUSY|jobId|workpieceId|machineId
DONE|jobId|workpieceId|machineId|sensorEvidence
FAULT|jobId|workpieceId|machineId|reason
ACK|jobId|workpieceId
```

The Java model represents the same semantics with `FinishingJob` and
`MachineReport`, including a map for `operationData` and sensor evidence. The
final cross-member encoding and exact channel names remain a group interface
decision. Whatever encoding is selected must retain both `jobId` and
`workpieceId` so stale or mismatched completions can be rejected.

## Machine operations and completion evidence

| Machine | Operation | Required preconditions | Evidence required before DONE |
| --- | --- | --- | --- |
| LidLoaderController | `PLACE_LID` | bottle present, filling complete, lid available | lid present, placement complete, arm home |
| CapperController | `CAP_BOTTLE` | bottle present, lid present | cap secured, clamp home, gripper home, cycle complete |
| LabelerController | `APPLY_LABEL` | bottle present, manufacturing complete, label payload | bottle stopped, label applied, bottle released, label payload |
| UnloaderController | `UNLOAD` | bottle present, collection available | bottle collected, output path clear, cycle complete |

All four controllers require the global safety permit. A successful report is
latched at `DONE` until the matching `ACK`; a failure is latched at `FAULT`
until the hazard/plant fault is cleared and a safe reset is requested.

## Tracker integration

1. Create a unique workpiece twin when the loader admits a real bottle.
2. Call `dispatch(job)` when the coordinator sends a correlated operation.
3. Forward `BUSY`, `DONE` and `FAULT` reports to `acceptReport(report)`.
4. Call `confirmLocation(...)` only after position/photo-eye evidence.
5. After confirmed unloading, call `completeAndArchive(...)` with the final
   label payload.

The tracker checks route order and the active job correlation. It does not mark
an operation complete merely because `START` was sent. Its recoverable-file
repository is the GP baseline; a database-backed repository is an optional
replacement and must not be required for normal GP operation.

### Confirmed filler amounts (2026-09-09 addition)

Tony's tracker change request is implemented at the existing Java
`acceptReport(MachineReport)` boundary (called `confirmOperation()` in the
request). Existing identity, batch, location, operation and status update rules
are unchanged. No other controller or persistence implementation was changed.

The provisional incoming evidence keys are `liquidA` and `liquidB`, exposed as
`WorkpieceTracker.LIQUID_A_EVIDENCE_KEY` and `LIQUID_B_EVIDENCE_KEY`.
`WorkpieceSnapshot.getActualDosedAmounts()` returns an immutable
`Map<String, String>` containing those two entries, or `null` if unavailable.

```java
// fillJob must be the active, correlated FILL_TWO_LIQUIDS job.
Map<String, String> result = new LinkedHashMap<String, String>();
result.put(WorkpieceTracker.LIQUID_A_EVIDENCE_KEY, "100.1250");
result.put(WorkpieceTracker.LIQUID_B_EVIDENCE_KEY, "400.5000");
tracker.acceptReport(MachineReport.done(fillJob, "filled", result));
Map<String, String> actual = tracker.findActive(fillJob.getWorkpieceId())
        .get().getActualDosedAmounts();
// Assembler must handle actual == null; missing is not zero.
```

- Only a matching filler `DONE` with both nonblank values populates the field.
  Dispatch, BUSY and FAULT do not populate it; rejected stale/mismatched reports
  cannot change it. Legacy DONE without amounts still completes normally.
- Values remain the original strings, with no rounding, conversion or numerical
  validation. Units are NOT established by this implementation. Tonny/Tony must
  confirm the actual payload keys, types and units before integration; the
  assembler must compare measurements and recipe targets in compatible units.
- Non-filler reports do not supply this measurement. The bottle's last confirmed
  filler amounts are retained through later operations and unloading, rather
  than erased. This is the implementation interpretation of the request's
  absent/null wording, not a newly agreed group decision.
- The shared coordinator adapter still needs to forward the full filler result
  into `MachineReport` evidence. This change does not establish or verify the
  group's SystemJ wire format or end-to-end filler connection.
- Tony's assembler can read a live snapshot, or the final snapshot returned by
  `completeAndArchive(...)`. After archival the active twin is removed.
  The existing recoverable-file schema is unchanged: **reloading that file does
  not restore this new structured field**. Existing textual event evidence is
  unchanged; dedicated structured persistence remains Tony's responsibility.

Run `Eric/scripts/build-and-test.ps1` from the repository root, or refresh Eric
in Eclipse and run **Eric Tests**. The dosing tests check confirmation timing,
missing/partial evidence, zero values, stale and duplicate reports, immutable
snapshots, bottle isolation and retention through finishing/unloading.

## GUI integration

The coordinator-facing adapter publishes immutable `DashboardState` snapshots
through `VisualizationBridge.publish(...)`. Operator actions return through an
`OperatorCommandSink`. The shared coordinator must validate every command and
then use normal controller channels; the GUI must never write actuator values.

The snapshot includes the active order/product/batch/recipe, requested,
completed, rejected, in-process and remaining counts, safety permit/reason,
diagnostic wait reason, machine states, six-position rotary occupancy and live
workpiece snapshots.

### Shared GP/IP dashboard (2026-09-11)

The same Swing panel can be embedded in a GP or IP window. Its implementation
has no IP dependency and the existing constructor remains operator-enabled:

```java
// Existing GP caller: all commands still go to this bridge's coordinator sink.
EabsDashboardPanel operatorView = new EabsDashboardPanel(bridge);

// IP/integration observer: no operator controls or command listeners are built.
EabsDashboardPanel observerView = new EabsDashboardPanel(
        bridge, EabsDashboardPanel.Mode.READ_ONLY);
```

Construct Swing components on the event-dispatch thread. Snapshots may be
published from the simulation thread; the panel schedules rendering on the
Swing thread. READ_ONLY limits this view only: the coordinator must still
validate all commands received through other views or adapters.

- `DashboardState.machineStates` is a **full current snapshot**, not a delta.
  The eight baseline controller cards remain visible. Additional nonblank IDs
  such as `BackupFillerController` appear automatically in sorted order. Extra
  cards disappear if omitted from a later snapshot. An omitted/null baseline
  state displays `NO DATA`; only explicit `MachineState.OFFLINE` means OFFLINE.
  The view cannot distinguish real feedback from placeholders supplied by an
  adapter. Do not publish READY for an unconnected machine as integration proof.
- Bottle rows include `Confirmed A (raw)` and `Confirmed B (raw)` from
  `getActualDosedAmounts()`. Original strings, including zero and precision,
  are retained. Unavailable values display `Not available`, not zero. These
  values are confirmed filler results, not live/partial dosing, and the panel
  neither infers mL units nor decides whether a bottle passes recipe tolerance.
- IP-specific attempt/version, recovery route, partial dose, station
  reservation and retry decisions stay in the IP-owned view/model. Reusing this
  panel does not move recovery policy into the GP or add a GP dependency on IP.
- Sharing a panel class does **not** connect separate processes. The embedding
  application must publish the intended tracker/controller snapshots through
  its bridge and retain one authoritative command path and tracker-update owner.

The local `IP_FillerRecovery/integration/gp/src/ip/filler/live/DemoWindow.java`
now uses READ_ONLY for its GP tab, replacing recursive disabling of controls.
That IP source is outside this Git repository. On another checkout, apply the
two-argument constructor there and rebuild the IP against this updated Eric
source folder (`RunDemo.cmd` without `-SkipBuild`). The IP's existing
`Live recovery` tab remains its operator interface. The standalone GP preview
still only logs requests; it is not a fully connected production coordinator.

Run `Eric/scripts/build-and-test.ps1`, or **Eric Tests** in Eclipse, to include
`SharedDashboardTest`. It checks dynamic cards and removal, missing versus
explicit OFFLINE state, confirmed amounts and unavailable data, read-only
command isolation, legacy operator actions and background-thread publication.
It also renders read-only and operator previews under `Eric/build/` as
`shared-dashboard-readonly-preview.png` and
`shared-dashboard-operator-preview.png`. Fixtures are not live production data.

Verification on 11 September: the GP Java subsystem suite (including confirmed
dosing and shared-dashboard tests) passed. Rebuilding the sibling IP against
these sources also passed its 52 adapter checks and all nine live SystemJ
scenarios: normal, pre-start primary fault, mid-fill primary fault, backup
busy/timeout, mid-fill backup fault, pause/resume, safety stop, manual primary
fault and restoration during the P5 wait. The panel previews were inspected
for both modes. These results establish isolated subsystem/IP-demo
compatibility, not completion of the shared group's EABS integration.

## Decisions for the group meeting

- Freeze the final SystemJ channel names, payload encoding and whether a
  separate `START_ACK` is required.
- Agree timeout values, safe-stop points, fault-clear ownership and reset
  acknowledgement semantics.
- Confirm rotary positions and the physical path from rotary table to labeller
  and unloader.
- Freeze final label data and the POS progress/completion schema.
- Decide which CD/reaction owns creation of `DashboardState` and forwarding of
  tracker updates.
- Merge each member's controller/plant pairs into one shared XML configuration
  and agree the end-to-end integration test entry point.

These are intentionally recorded as open decisions, not as agreed facts.
