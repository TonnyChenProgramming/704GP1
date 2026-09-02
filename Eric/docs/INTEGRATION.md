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

## GUI integration

The coordinator-facing adapter publishes immutable `DashboardState` snapshots
through `VisualizationBridge.publish(...)`. Operator actions return through an
`OperatorCommandSink`. The shared coordinator must validate every command and
then use normal controller channels; the GUI must never write actuator values.

The snapshot includes the active order/product/batch/recipe, requested,
completed, rejected, in-process and remaining counts, safety permit/reason,
diagnostic wait reason, machine states, six-position rotary occupancy and live
workpiece snapshots.

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
