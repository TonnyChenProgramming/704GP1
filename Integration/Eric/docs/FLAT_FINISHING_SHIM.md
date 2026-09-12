# Tony flat finishing adapter: integration handoff

Implemented in `Integration/Eric` on `eric-integration`. The user selected Tony's
[Finishing Adapter Interface Spec](https://claude.ai/code/artifact/d233852d-7476-4fa9-9279-95ca384355f4)
on 12 September 2026. This implements that **simulation compatibility profile**,
not the alternative safety-aware protocol proposed during discussion.

## What is ready

- Four shim CDs in `systemj/finishing_shims.sysj`, one private endpoint per CD.
- Unchanged real finishing controller/plant CDs in `systemj/finishing_devices.sysj`.
- Shared strict translation/state model: `FlatFinishingShim.java`.
- Real sixteen-CD acceptance XML: four flat test peers, four shims, four
  controllers and four plants. This is NOT Tony's live CoordinatorCD.
- Java tests and actual course SystemJ compiler/runtime tests pass. The flat
  peers check 30 reports each for lid/capper/unloader and 32 for labeller (122
  total). Existing rich-controller tests still pass 71 responses per pair and
  the 348 Java endpoint checks. These are checks, not 122 distinct scenarios.

Original controllers/plants and root `Eric/` are not changed. No Tony/Tonny
source, shared coordinator XML, POS, tracker implementation or IP is rewritten.
The earlier uncommitted tracker/GUI bridge work is separate from this handoff.

## Required runtime profile and limitations

Set this VM argument on the **group simulation runtime** when these shims are used:

```text
-Deric.finishing.flatSimulation=true
```

Without it the shim fails startup explicitly. This flag opts into the behaviours
Tony requested: one boot permission grant, synthetic true preconditions, and
automatic repair/reset of a fault whose controller confirms `safe=true`.
Each shim prints a startup notice naming those assumptions. This does not connect
live occupancy, safety-monitor state, resource availability or human repair.

| Station ID | Synthesized job data |
| --- | --- |
| LID | bottlePresent=true, filled=true, lidAvailable=true |
| CAPPER | bottlePresent=true, lidPresent=true |
| LABELLER | bottlePresent=true, manufacturingComplete=true, supplied labelPayload |
| UNLOADER | bottlePresent=true, collectionAvailable=true |

These assumptions seed the simulated plant. Completion still requires the real
controller's correlated, timed plant evidence. Do not describe assumed input
booleans as measured sensors or claim this profile verifies upstream processing.

The outer interface has NO active STOP/permit update channel. Therefore stopping
admission alone does not stop an active finishing operation. The original rich
controllers retain that capability, but this flat profile does not expose it.
Full-line safety integration is still a group dependency, not solved by this shim.
No bounded missing-channel watchdog or hardware-safety claim is made.

## Outer ports and grammar

Every shim exposes exactly:

```text
commandIn   input String channel
reportOut   output String channel
ackIn       input String channel
```

| Coordinator ID | Shim CD | Existing controller CD | Operation |
| --- | --- | --- | --- |
| LID | LidLoaderShimCD | LidLoaderControllerCD | PLACE_LID |
| CAPPER | CapperShimCD | CapperControllerCD | CAP_BOTTLE |
| LABELLER | LabelerShimCD | LabelerControllerCD | APPLY_LABEL |
| UNLOADER | UnloaderShimCD | UnloaderControllerCD | UNLOAD |

```text
START|jobId|workpieceId|stationId|operation
START|jobId|workpieceId|LABELLER|APPLY_LABEL|WP-42:BATCH-3
READY|stationId
BUSY|jobId|workpieceId|stationId
DONE|jobId|workpieceId|stationId|OK|key=value...
FAULT|jobId|workpieceId|stationId|reason
ACK|jobId|workpieceId
```

The successful DONE preserves controller evidence, with plain outer values.
Parse evidence beginning at field index 5 after `OK`; do NOT URL-decode this flat
frame. Internal values retain the existing UTF-8 URL-form encoding.

Accepted jobs always emit BUSY, then exactly one DONE or FAULT. A rejected START
emits a single FAULT with reason prefix `START_REJECTED_`, followed by READY and
no BUSY. This preserves Tony's flat outer grammar while allowing the tracker
adapter to distinguish non-admission from a failed accepted operation.
**Do not register a rejected job as executed or completed.**

IDs keep the rich codec's bounded ASCII grammar. Unknown station/operation,
malformed frames, unsupported data and ambiguous labels are rejected. Extra
production data fields are not silently dropped or interpreted.

### Label delimiter restriction

Tony's current label profile is exactly two colon-free ID components separated
by one colon: `[A-Za-z0-9_.-]{1,100}:[A-Za-z0-9_.-]{1,100}`. The shim changes that
single colon into a pipe in the internal label, then uses the existing encoder.
On DONE it checks exact printed content and restores the colon on the outer wire.

Example: `WP-42:BATCH-3` becomes internal `WP-42%7CBATCH-3` and returns
`printedPayload=WP-42:BATCH-3`. A third colon, raw pipe, arbitrary free text or
ingredients-rich label is unsupported in this profile and is rejected, not
silently corrupted. Extend the group's encoding/label contract before requiring
those contents; this restriction is not a declaration of full labelling compliance.

## Exact rendezvous lifecycle

1. Shim receives the real controller's initial READY first.
2. It sends `PERMIT|shim-boot-STATION|true` and consumes the matching CONTROL_ACK.
3. It advertises outer READY. Coordinator must consume this before START.
4. START is validated/mapped, then sent to internal jobIn. BUSY and terminal
   feedback are received and translated, never synthesized as a successful result.
5. On DONE, coordinator sends matching ACK. Shim forwards it, waits for the real
   READY, then publishes outer READY. A stale/wrong ACK is logged and ignored,
   with no extra terminal report. Coordinator must still send the correct ACK.
6. On a safe accepted FAULT, publish FAULT, internally CLEAR_FAULT, receive matching
   CLEARED, RESET, receive real READY, then publish READY. There is no outer ACK
   after FAULT. The shim NEVER reissues START or retries the faulted bottle.
7. Internal REJECTED is translated to FAULT, then its actual READY is drained.
   Locally rejected input never reaches the controller, so there is no internal
   READY to receive. These two cases intentionally have different internal flows.

If the controller reports `safe=false`, the shim holds and does NOT repair or
advertise READY. Corrupt/mismatched internal replies or denied repair fail loudly,
not as successful recovery. Such protocol errors currently require investigation
and restarting the simulation; there is no outer manual-recovery channel here.
Coordinator still owns what happens to the faulted bottle. Controller READY is
machine availability, not product completion or permission to refill that bottle.

## Tony's first real connection

Compile `finishing_devices.sysj` and `finishing_shims.sysj`, plus the Eric Java
main sources. Keep `finishing_contract.sysj`, `finishing_shim_contract.sysj` and
their Java test peers OUT of the group runtime configuration. Test peers exit
the JVM on success; they are not production coordinators.

For the first lid pair, copy the shim/controller/plant elements from
`config/finishing-shim-contract.xml`, remove `LidLoaderFlatHarnessCD`, and connect:

| Coordinator port | Peer |
| --- | --- |
| lidJobOut | LidLoaderShimCD.commandIn |
| lidReportIn | LidLoaderShimCD.reportOut |
| lidAckOut | LidLoaderShimCD.ackIn |

Update both XML endpoints. Keep all five shim-to-controller channels and both
controller-to-plant channels as shown in the tested XML. Leave controller/plant
class names unchanged. Remove/rename conflicting coordinator stub declarations
before compiling everything (notably CapperControllerCD and UnloaderControllerCD).

Coordinator must read initial READY, consume reports by type (a rejection has no
BUSY), ACK only DONE, and drain the post-fault READY. A caller that always reads
two reports or waits for another READY before acknowledging DONE can deadlock.
Channel receivers must be available across stations; these are rendezvous,
not buffered message queues. This slice does not certify the existing coordinator
as compatible until its real runtime is connected and tested.

For labeller dispatch, coordinator must append the actual flat label field.
Do not use a placeholder label or hard-coded workpiece IDs in shared production.
Do not forward the same report twice to the tracker. Its rich report parser is
not automatically the parser for this new flat dialect, especially REJECTED-as-
FAULT and restored label delimiters. Tracker/GUI wiring needs separate tests.

## Run and verify

From the repository root, with PowerShell 7+, JDK and course libraries:

```powershell
pwsh -NoProfile -File .\Integration\Eric\scripts\build-and-test.ps1
pwsh -NoProfile -File .\Integration\Eric\scripts\build-and-test-systemj.ps1
```

The second script compiles sources afresh, runs the existing twelve-CD rich
contract, then the sixteen-CD flat contract in a separate bounded process.
It sets both the flat simulation opt-in and `eric.finishing.testMode=true` for
the fault-injection harness only. Omit the testMode flag from the group runtime.

Test-only START suffixes `simMode=FAULT/STALL/BAD_CORRELATION/MISSING_EVIDENCE`
and `timeoutTicks=3` are accepted only under testMode. The budget is still a
responsive-plant polling limit, not a scheduler clock or communication timeout.

Coverage: three successful distinct jobs per station, two wrong ACKs, duplicate
job, wrong station/operation, malformed input, ambiguous label, simulated jam,
no-progress timeout, corrupt/missing plant evidence, automatic repair and new
work after repair. Endpoint-only checks additionally cover opt-in/test gates,
unsafe fault hold, bad report correlation, incomplete DONE and denied repair.

Expected final marker:

```text
ERIC SYSTEMJ RICH AND FLAT CONTRACT TESTS PASSED
```

Success logs (ignored build outputs): `build/systemj-dynamic-last.log` and
`build/systemj-flat-shim-last.log`. A process deadline is a test-runner safeguard,
not the full system's fault watchdog. Check the current run's exit status; old
success logs may remain after a later failed run.

Next checkpoint: Tony drives this shim from the actual CoordinatorCD, initially
with one LidLoader pair, before full-line tracker/GUI, safety and POS claims.
