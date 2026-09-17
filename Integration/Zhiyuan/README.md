# CoordinatorSystemJ — integrated simulation

This is the repaired, runnable coordinator on the integration branch. It uses real
SystemJ controller/plant pairs from the copied Tonny sources in this folder and
Eric's finishing shims/devices in `../Eric`. It is no longer the all-stub smoke test.

Start with [COORDINATOR_INTEGRATION.md](COORDINATOR_INTEGRATION.md) for the protocol,
changes/reasons, safety limitations and team handoff.

## Pure Eclipse workflow — no PowerShell required

1. Import **both** existing projects from `Integration/Eric` (project name
   **Eric**) and `Integration/Zhiyuan` (**CoordinatorSystemJ**). Do not import
   the old root-level Eric project under the same name.
2. Refresh both projects (**F5**), enable **Project > Build Automatically**, and
   let Eclipse compile the hand-written Java, including the new build launcher.
   The committed `generated-src` folder exists even before the first build, so
   a clean clone has no missing generated-source-folder dependency. If an old
   `build/eclipse-generated` source entry persists, close/reopen the project to
   reload `.classpath`; do not add both generated folders.
3. Select **Run > Run Configurations > Java Application > BuildAll**.
   Under **Main**, verify project `CoordinatorSystemJ` and main class
   `nz.ac.auckland.eabs.zhiyuan.tooling.EclipseSystemJBuild`.
   Under **JRE**, use a full JDK (tested with JDK 26), not a runtime without the
   Java compiler. This workflow does not require `pwsh`, `javac` on PATH or a
   PowerShell execution-policy change.
4. Click **Run**, then wait for **ECLIPSE SYSTEMJ BUILD PASSED**. This compiles
   Java helpers first, invokes the course SystemJ compiler, compiles/checks all
   26 generated CD classes, then publishes the generated Java. It can take a
   few minutes. It does not automatically run the simulation.
5. Press **F5** again and let Eclipse finish its Java build (or use **Project >
   Build Project**). Check **Problems** for errors. Generated Java is in
   `generated-src`; edit `.sysj` source, never generated Java.
6. Select **Run > Run Configurations > Java Application > RunCoordinator**.
   This opens the read-only GUI and runs the normal 8 + 2 bottle test batches.
   The process/GUI exits when both finish. Archives use a fresh UUID filename.
7. Use **CoordinatorTests** for the fast model tests. Use **RunCoordinatorFault**
   for the real-device fault harness after building. Use **VerifyIntegration**
   for a fresh build plus all model/runtime/archive/fault checks, still entirely
   through Java; it finishes with **ECLIPSE SYSTEMJ BUILD AND TEST PASSED**.

`RunCoordinatorReal` replaces the scripted batch harness with the interactive
console `BatchManagerCD`. Enter the seven requested order fields; after `DRAINED`
it accepts another order and remains running until manually terminated. This is
an interactive integration profile, not yet a connection to the actual POS CD.

`RunCoordinatorIp` replaces `BatchManagerCD` with `IpBatchManagerCD`, which wires
the IP's real persistence layer (`com.g7.ip.POS`/`BatchManager`/`Dao`) in front of
the same Coordinator. Stage 2 (IP report Section 6/10): every call that touches
the database runs on a dedicated `DbWorker` thread (`nz...coordinator.DbWorker`),
never on the console reader thread or the SystemJ tick thread that delivers
`batchDrainedOut` results -- submitting an order, typing `go`, and processing a
drained batch all just enqueue a request and return immediately. Enter a customer
purchase order (customer_po/customer_id/product_id/quantity/bottle_spec/recipe_id) and it
is validated and stored as a PENDING row in `Orders` -- it is NOT activated yet.
Type `go` (instead of a customer_po) once you are done entering orders for this
round; only then does the Batch Manager query all PENDING orders, consolidate
everyone waiting on the same product into a single batch, and activate it. This
two-step shape (enter orders, then `go`) is what lets several orders for the same
product actually merge into one batch: submitting an order and immediately
activating it solo, with nothing else pending yet, cannot demonstrate cross-order
merging. Two default recipes are seeded on first run and printed as a numbered
catalog; the `recipe_id` prompt accepts one of those numbers, or `new` to define a
custom recipe on the spot (prompts for doseA/doseB as 0-100 percentages) -- the new
recipe is persisted to `Recipes` before the order is submitted against it, so it
stays traceable from `BottleEvents`/`Faults` afterwards exactly like a seeded one;
the schema's own `CHECK` constraint (proportions summing to at most 100%) is the
final backstop if a nonsensical split ever reached the database. The database defaults to a
fresh `build/ip-<uuid>.db` per run; pass `-Dip.database=build/ip-dev.db` (already
set in this launch config) to reuse one fixed file across runs, e.g. for
inspecting it with a SQLite viewer while the simulation is running.

`IntegratedCoordinator` also publishes every confirmed transition it already gives
Eric's Tracker (DONE/FAULT/ABORTED) to `TwinEventBus`, a package-private, non-blocking
bridge. When `RunCoordinatorIp` is running, `IpBatchManagerModel` drains this bus on
its own consumer thread and feeds a real `DigitalTwinAssembler`/`DeviationDetector`
(IP report Section 4/7) -- so `BottleEvents`/`Faults` now fill from an actual production
run, not only from `TestHarness`'s stub data, and a deviation check runs automatically
the moment a bottle reaches `unloader`, printed as `[DigitalTwin] ... deviated=...`.
This hook is self-gating: it is a genuine no-op on `coordinator.xml`/`coordinator_real.xml`,
since their batch/recipe ids ("B1", console-typed ids) are not the numeric database ids
only `IpBatchManagerCD` ever supplies.

`RunCoordinatorGpPos` runs the exact same `IpBatchManagerCD`/`coordinator_ip.xml` with one extra
VM argument, `-Dip.autoActivate=true` (also using its own `build/gp-pos-demo.db` so it never
collides with `RunCoordinatorIp`'s own database file). This is the brief's POS-as-part-of-the-
core-ABS requirement (Section 4.2/5) demonstrated for the GP: a purchase order is submitted
through the real, persistence-backed POS and activates its own batch immediately, no `go`/
cross-order merge step -- that two-step demand-consolidation behaviour is still the IP's own
`RunCoordinatorIp` demo. No code path shared with `RunCoordinatorReal`/`BatchManagerCD` or the
plain `RunCoordinatorIp` changes: `autoActivate` defaults to `false`, so neither existing launch
config's behaviour is affected by this addition.

`RunCoordinatorPosGui` runs the same `IpBatchManagerCD`/`coordinator_ip.xml` with
`-Dip.gui=true` added: `IpBatchManagerModel`'s constructor then opens a Swing
`com.g7.ip.gui.PosGuiFrame` instead of starting the console order-entry thread
(`SharedConsole` still starts -- `SafetyMonitorModel`'s hazard/clear/reset commands
still read from it). Same in-process pattern as Eric's own `EabsDashboardPanel`: the
frame is handed a direct reference to the already-running model and calls straight
into its `Dao`/`POS`/`BatchManager`/`DbWorker`, no second persistence layer, no IPC.
"Place Order" is a cart -- add any number of lines, each just bottle capacity,
liquid A/B percentages and quantity, no product picker (brief 4.2 defines a product
BY its bottle size and liquid specification, so asking for one separately was
redundant). `product_id` is invisible to the operator now; `IpBatchManagerModel.
deriveProductId(bottleSpec, doseA, doseB)` derives it deterministically from exactly
those three fields (e.g. `FORM-500ML-60-40`), so two lines with the identical
capacity+mix always resolve to the same product/recipe instead of minting a
duplicate -- and since `BatchManager` already groups PENDING orders by `product_id`,
this also makes that grouping exactly "same formulation" now that capacity/ratio are
freely combinable, without touching `BatchManager.java` itself. Each line gets its
own generated PO reference and is submitted independently (`submitCartLine`);
`-Dip.autoActivate=true` (set in this launch config) activates each as its own batch
immediately, same as `RunCoordinatorGpPos`. "Track Order" resolves a PO reference via
the new `Dao.findOrderStatus` query (`Orders` left-joined through `OrderBatches`/
`Batches`) to admission state, assigned batch, and bottles completed so far -- live,
since the digital-twin pipeline in this same process is what increments
`OrderBatches.completed_quantity` as bottles finish. Uses its own
`build/pos-gui-demo.db` so it never collides with the other launch configs' database
files.

`IpBatchManagerModel` also recovers from an abrupt interruption on startup (IP report
Section 7): before checking for pending demand, it queries `Batches` for any row still
`RUNNING` -- which, at construction time, can only be work orphaned by a previous process
that never drained it -- marks every bottle in that batch with no terminal `DONE` at
`unloader` as `ABORTED`, and closes the batch as `FAULT`. This also fixed a latent bug in
`Dao.markBottleAborted`: it used to `UPDATE ... WHERE status<>'DONE'`, which touches nothing
for a bottle whose only rows are legitimate intermediate `DONE`s (e.g. it reached `loader`
and `conveyor_in` but never `unloader`) -- `BottleEvents` is append-only, so marking a bottle
aborted now appends one more row instead of trying to rewrite history that was never wrong
in the first place. Also fixed: a drained batch is now actually marked `COMPLETED`
(`dao.completeBatch`), which nothing previously called, so `Batches.status` no longer stays
`RUNNING` forever even for batches that finished normally.

### If Eclipse still launches PowerShell

Do not use **External Tools > Program > BuildAll** or the toolbar's previous-run
shortcut. That is the obsolete script configuration cached in your workspace.
Choose the new **Java Application > BuildAll** explicitly. You may delete only
the obsolete **Program** launch entry; do not delete project/source files.

If the new entry is not listed, refresh the project then right-click the shared
`BuildAll.launch` and choose **Run As > BuildAll** if offered. Alternatively,
create a Java Application configuration with the project/main class in step 3,
working directory `${workspace_loc:/CoordinatorSystemJ}` and VM argument
`-Xmx768m`.

### If Eclipse reports `declared package` / `IntegratedCoordinator cannot be resolved`

The coordinator test must be at
`tests/nz/ac/auckland/eabs/zhiyuan/coordinator/IntegratedCoordinatorTest.java`,
not directly under `tests`. Eclipse checks the declared package against the
directory below each source root; javac given explicit files may not catch this.
BuildAll now rejects this layout mismatch before compilation.

After pulling the layout fix: terminate any old simulation, refresh **both**
projects with **F5**, then **Project > Clean** for Eric and CoordinatorSystemJ.
Let the automatic Java build finish before running again. Never choose to launch
despite compile errors: Eclipse can emit problem classes that run partly and then
throw `Unresolved compilation problem` even after a test prints its PASS message.

Only one simulation may use ports 30101–30103 at a time. If a failed launch stays
alive, select that Console and press **Terminate** (red square). VerifyIntegration
now checks these ports first and reports a clear error rather than waiting for
an unrelated rotary-table timeout. BuildAll without tests does not need the ports.

### Which action after an edit?

| Change | Next action |
| --- | --- |
| `.sysj` controller/coordinator/plant | BuildAll, F5, Eclipse Java build, RunCoordinator |
| Java helper / tracker / GUI only | Eclipse Java build, then RunCoordinator; run VerifyIntegration before sharing |
| XML wiring only | Save, then RunCoordinator; referenced CDs must already be compiled |
| Preparing a commit or teammate handoff | VerifyIntegration |

BuildAll is a Java build launcher, not the production coordinator or a replacement
runtime. It directly uses the JDK compiler API and starts only the Java SystemJ
compiler processes. Per-build outputs/logs are under `build/eclipse-build-<id>`.
It checks compiler diagnostics even when the course compiler exits with code 0.
No Windows-wide settings are modified. The course compiler is still required;
ordinary Eclipse Java Build alone does not translate `.sysj`.

The exact Java entry point passed all compilation/model/runtime/archive/fault
checks outside Eclipse. Launch XML was validated; native Eclipse clicks and the
user's cached workspace configuration were not automated.

## Optional command-line verification

The existing `scripts/test-coordinator.ps1` remains an optional PowerShell 7
verification route, not a dependency of the Eclipse workflow. It now publishes
to the same `generated-src` directory. `scripts/eclipse-build.ps1` is retained
only for old workspace configurations and is no longer referenced by BuildAll.

## Not yet the finished Group Project

The batch driver is a test harness, not the real POS. Tony must send the explicit
eight-field activation and handle REJECTED/FAULT/DRAINED. The flat protocol has no
active STOP/PERMIT/RESET handshake; the current HOLD is not an emergency stop.
Tonny's rotary occupancy mask and independent fill measurements remain simulation
limitations. See the handoff document before claiming full GP completion.

The previous compiler investigation is preserved in Git history (baseline
`cb9ed9d`). Legacy `TrackerPort`, `TwinView`, and `StubTracker` sources are
retained but are not used by this integrated runtime.
