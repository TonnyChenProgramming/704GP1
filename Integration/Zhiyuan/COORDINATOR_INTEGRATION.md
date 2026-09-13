# Coordinator integration handoff — 13 September 2026

## Verification recorded

Verified locally with JDK 26 and the bundled course SystemJ compiler/runtime:

- 544 coordinator model assertions.
- 26 generated CD classes compiled; runtime configurations select the required batch/safety/IP-persistence CDs.
- Normal runtime completed B1 (8 bottles, 20/80) before B2 (2 bottles, 35/65).
- 72 separate-JVM archive assertions for ten completed bottles and eight operations each.
- Actual LID plant/shim fault returned `FAULT|F1|LID_PLANT_SIMULATED_JAM`; no completed archive was created for that run.
- Existing Eric Java subsystem tests still pass.

Local evidence: `build/verification-54b41b9d08814c1bbcd3d32582db831b/acceptance.log`.
This is simulation acceptance, not completed POS, full safety, or hardware validation.

The pure Java/Eclipse launcher was subsequently verified with the same 544 model
checks, 72 archive checks and both runtime scenarios. Logs are in
`build/eclipse-build-10767836659479273593/` (`model.log`, `normal.log`,
`archive.log`, `fault.log`). No PowerShell was invoked in this verification.

Eclipse-specific follow-up: the test originally declared a package while sitting
at the tests source root. It is now under its matching package directory, and the
Java builder checks package/directory consistency. All sources were also compiled
with the user's installed Eclipse JDT 3.46 compiler, then the generated classes
ran under Eclipse's bundled Java 21. The normal 8+2 run exited with code 0, and
544 model checks plus 72 archive checks passed. Output was isolated under
`build/ecj-check-b07d551d6ef34eed94bbbee8c9ce3d00/`; no live Eclipse bin files were overwritten.
The complete Java build, normal runtime, archive and fault regression subsequently
passed again in `build/eclipse-build-16011211432191684307/`.

## What changed and why

| Area | Previous issue | Current design |
| --- | --- | --- |
| Compilation | Large nested parallel/channel/weak-abort structure exhausted the course compiler heap. It sometimes returned exit code 0 on an error. | Flat per-station SystemJ I/O reactions; a private Java state model; fresh output directories, bounded child processes, diagnostics and generated-class checks. |
| Java compatibility | Modern JDK properties missing; old class reader also silently exits on a helper class containing lambda bytecode. | Compile helpers first with Java 8 bytecode, use the existing compatibility launcher, avoid lambdas in the directly imported helper. |
| Handshake | START was sent before consuming READY; post-ACK READY was left pending. | Consume READY, send START, validate BUSY and DONE/FAULT, ACK only valid DONE, then consume READY again. |
| Correlation | Prefix checks could accept a stale/foreign result. | Match station, job ID and workpiece ID; check evidence before updating a twin. |
| Recipe / label | Filler START omitted dose targets; labeller START omitted payload. | Explicit two-integer dose targets and restricted `workpieceId:batchId` label. |
| Conveyor | No coordinator connection. | One shared conveyor worker: output transfer first, then input transfer, each confirmed by DONE before location updates. |
| Indexing | Output-capacity guard could deadlock; partial slot shifts did not represent one physical index. | Release completed downstream work first; clear P5; move all six slots atomically after alignment. P6 remains empty in this route. |
| Tracking | `println` replaced tracker calls. | Real Eric `WorkpieceTracker`, eight ordered operations, confirmed positions, fault status, file archive after unloading. |
| Measurements | Filler echoes command targets as `liquidA/B`. | Retain them as `commandedLiquidA/B` in event evidence, not measured `actualDosedAmounts`. |
| Fault handling | Clearing occupancy and restarting risked losing bottles or repeating operations. | Latch HOLD, preserve twins/locations, report failure, no automatic retry/reset. |
| XML | Connected idealised station stubs. | Connect actual source controller/plant pairs and finishing shims; only the batch source is a test harness. |

## Run from a clean checkout

Preferred workflow: import both `Eric` and `CoordinatorSystemJ`, then use **Run >
Run Configurations > Java Application > BuildAll**. Refresh/build Java, then run
**RunCoordinator**. **VerifyIntegration** performs the full build and tests. A
full JDK is required, but PowerShell and PATH tools are not. See README for exact
steps and how to avoid the obsolete External Tools configuration.

The Java launcher publishes verified generated Java into `generated-src`.
The source folder is present on a fresh clone; generated Java files are ignored.
Separate build outputs and logs go into `build/eclipse-build-<id>`.

Optional command-line workflow below requires Java/Javac on PATH and PowerShell 7.
The course jars already in `Integration/Zhiyuan/lib` are the default. Nothing is downloaded.

From the repository root:

```powershell
pwsh -NoProfile -File Integration/Zhiyuan/scripts/test-coordinator.ps1
# Optional read-only monitor of the same REAL running simulation:
pwsh -NoProfile -File Integration/Zhiyuan/scripts/test-coordinator.ps1 -Gui
# If using another course library directory:
pwsh -NoProfile -File Integration/Zhiyuan/scripts/test-coordinator.ps1 -SystemJLibPath 'D:\path\to\lib'
```

Each run has its own `Integration/Zhiyuan/build/verification-<id>/` with fresh classes, generated Java, an archive and, on full success, `acceptance.log`. The script runs model tests, compiles 25 CD classes (scripted/interactive batch and safety sources), runs the real controller/plant integration, then reopens all ten archived records in a separate JVM. It also runs a real simulated LID fault and checks that the batch returns FAULT, not DRAINED. Do not infer success from compiler exit code alone. A failed run has no success log.

The integration batch harness rejects one malformed request, then runs eight bottles with 20/80 and two bottles with 35/65. These are **simulation units**, not mL. The current filler plant capacity is 100. The run is bounded and terminates automatically. Generated files are build products, not source to edit or commit.

## Tony: batch interface changes required

The old five-field activation has no recipe amounts or order ID. It is now explicitly rejected rather than inventing metadata or doses. Resolve the recipe in the POS/batch manager, then send:

```text
ACTIVATE|batchId|recipeId|productId|quantity|doseA|doseB|orderId
ACTIVATE|B1|R1|PRODUCT_X|8|20|80|ORDER-1
```

IDs use `[A-Za-z0-9_.-]`, 1–60 characters. Quantity is 1–10000. Doses are integers >= 0 with sum 1–100. Batch IDs must be unique for the process. The default archive is also unique per run; if you deliberately reuse an archive file across process restarts, the POS must supply globally unique batch IDs to avoid reusing workpiece IDs. Do not activate another batch until its terminal result is received.

Results on `batchDrainedOut` (name retained for compatibility):

```text
DRAINED|batchId
REJECTED|batchId|INVALID_BATCH_OR_RECIPE
FAULT|batchId|reason
```

REJECTED leaves the line idle; a corrected activation may follow. FAULT latches HOLD and requires explicit engineering/operator recovery; it is NOT equivalent to DRAINED. The coordinator never automatically advances to another product following FAULT.

Replace `CoordinatorBatchHarnessCD` with the real batch manager in XML. The POS itself is **not** exercised by the harness, and the harness must not be described as a live POS integration.

## Tonny: confirmations and remaining gaps

- The tested input/processing sources are the copied files under `Integration/Zhiyuan/sysj`, not a second compilation of `Integration/Tonny/sysj`. Avoid duplicate class definitions; reconcile copies together before further edits.
- Confirm the agreed input/output conveyor route. The shared worker serializes the two transfers and uses existing conveyor sensor-confirmed DONE. This remains a symbolic plant, not collision-accurate geometry for multiple physical conveyor bottles.
- Rotary plant occupancy is currently always `000000`: it is not wired to bottle loading/unloading. The coordinator/tracker owns logical occupancy; only the alignment event is used to confirm its index. Do not display the plant mask as measured bottle occupancy.
- Existing rotary confirmation has no request ID. Normal repeated indices are tested, but stale/duplicate network signal fault scenarios need a correlated index/ack protocol for stronger guarantees.
- Filler DONE currently echoes the requested quantities. Independent level/flow measurements are needed before claiming measured fill amounts or measured-tolerance detection.
- The copied filler controller receives `dose1Done`/`dose2Done` but currently does not validate their Boolean values. Tonny should enforce these local interlocks and test false sensor cases before claiming filler fault detection. The normal all-true plant cannot expose this defect.

## Safety and recovery boundary — not complete hardware safety

This is the previously agreed **flat simulation** finishing profile, enabled explicitly by `-Deric.finishing.flatSimulation=true`. It supplies fixed finishing preconditions and may repair a simulated finishing device internally; the coordinator still does NOT retry the failed bottle.

The current flat station interface has no STOP/PERMIT/RESET channels. `safetyPermitLost` latches a coordinator HOLD and blocks new jobs/index requests; an already dispatched machine cycle may still finish. A channel send already offered cannot be retracted. It is **not an emergency stop**. A timeout detects a missing response but cannot physically stop that device.

The acceptance XML exposes the hazard input on loopback port 30103 but does not connect a real safety monitor. The read-only GUI therefore does not advertise a verified safety permit. Complete GP safety needs a shared permit/stop/reset contract, controller enforcement, environmental/human sensors and recovery tests; do not claim this requirement is finished.

No automatic recovery from uncertain indexing, faulted workpieces, or failed persistence is provided. Preserve the held state, inspect physical/simulated positions and decide disposition. The existing tracker archive API is not a crash-recovery checkpoint for in-process bottles.

## Eric: tracker and GUI

- `IntegratedCoordinator` owns a real `WorkpieceTracker`. Java helper state is private to its CoordinatorCD and shared only with its own SystemJ reactions through one valued local signal.
- Tracker updates use correlated controller completion, then conveyor DONE / table alignment for positions. Archives retain order/batch/product/recipe, route, actual station names, events and final label.
- `-Gui` opens the existing dashboard in READ_ONLY mode. No POS form or operator control is silently connected. The GUI closes when the bounded harness exits.
- This implementation does not depend on or commit the pre-existing unfinished `Integration/Eric/.../integration/` bridge files or dashboard edits. Those remain a separate work item.
- File persistence runs in the simulation thread and is suitable for this small acceptance test, not a hard-real-time architecture. Large archives need an asynchronous persistence boundary with confirmed commits before DRAINED.

## Source map

- `sysj/coordinator.sysj`: concurrent batch/result, station and rotary/safety I/O reactions.
- `src/nz/ac/auckland/eabs/zhiyuan/coordinator/IntegratedCoordinator.java`: phase policy, strict frame checks, occupancy, tracker and read-only view.
- `sysj/coordinator_harness.sysj`: test-only batch driver.
- `sysj/coordinator.xml`: 22-CD simulation wiring.
- `tests/nz/ac/auckland/eabs/zhiyuan/coordinator/IntegratedCoordinatorTest.java`: deterministic contract tests and separate-process archive checks.
- `scripts/test-coordinator.ps1`: reproducible, fail-checked clean build and acceptance run.
- `src/nz/ac/auckland/eabs/zhiyuan/tooling/EclipseSystemJBuild.java`: pure Java build/verification launcher; `BuildAll.launch` does not invoke a shell.
- `CoordinatorTests.launch`, `RunCoordinatorFault.launch`, `VerifyIntegration.launch`: separate fast model, fault runtime and full acceptance entry points.

The old `TrackerPort`, `TwinView` and `StubTracker` remain for source history/other adapters; they are not used by this runtime.
