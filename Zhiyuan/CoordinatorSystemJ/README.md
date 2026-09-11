# CoordinatorSystemJ

A self-contained Eclipse project for compiling and running
`sysj/coordinator.sysj` (the Production Coordinator, Coordinator_Spec.md) --
structured the same way as the course's `COMPSYS704_Lab_2`/`COMPSYS704_Lab_3`
projects, with the same SystemJ toolchain (`lib/`) copied in, so it should
compile and run with no extra setup beyond Eclipse + a JDK.

This file has **never been run through the SystemJ compiler** -- it was
written on a machine without the toolchain, using syntax grounded in
Lab 2/3's own compiled examples and Eric's delivered `finishing_contract.sysj`,
but never actually compiled. That's the point of this project: compile it
here and fix whatever surfaces.

## What's here (and what isn't)

| Path | Contents |
|---|---|
| `sysj/coordinator.sysj` | The Coordinator CD + stub Controller/RotaryTable/Safety/BatchManager-harness CDs (all in one file, like Eric's `finishing_contract.sysj`) |
| `sysj/coordinator.xml` | Wiring config for `RunCoordinator.launch` |
| `src/nz/ac/auckland/eabs/zhiyuan/coordinator/{TrackerPort,TwinView,StubTracker}.java` | The only 3 plain-Java files `coordinator.sysj` actually imports |
| `lib/` | SystemJ compiler + runtime + dependencies, copied from `COMPSYS704_Lab_3/lib` |
| `BuildAll.launch`, `RunCoordinator.launch` | Eclipse run configurations (see below) |

**Not included** (deliberately, per the brief to keep this folder minimal):
the full Java-side `Coordinator` state machine and its own acceptance test
suite (already Java-verified separately), Eric's/Tonny's real controllers,
and the IP persistence layer / `BatchManager` Java classes. `coordinator.sysj`
doesn't call into any of those -- see the file's header comment for exactly
what it does depend on and why.

## Opening and running in Eclipse

1. **File > Import... > General > Existing Projects into Workspace**, browse
   to this `CoordinatorSystemJ` folder, finish. (Or just copy this whole
   folder into your workspace directory and it should show up / can be
   imported the same way.)
2. Refresh the project (select it, press **F5**).
3. **Run > Run Configurations...**, find **BuildAll** under Java
   Application, click **Run**. This invokes the SystemJ compiler
   (`com.systemj.compiler.JavaPrettyPrinter -d src --nojavac --silence
   sysj/*.sysj`), which reads every `.sysj` file in `sysj/` and generates
   one `.java` file per clock-domain directly into `src/` (top-level,
   alongside the 3 hand-written files above, which it won't touch). Eclipse
   auto-compiles the generated `.java` files as usual.
4. Fix whatever compiler errors/warnings come up in `coordinator.sysj` --
   this is expected on the first pass. Re-run **BuildAll** after each fix.
5. Once it compiles clean, refresh (**F5**) again, then run **RunCoordinator**
   (`com.systemj.SystemJRunner sysj/coordinator.xml`). Since every CD in
   `coordinator.xml` lives in one `Local="true"` SubSystem (same process,
   no networking), this single run configuration is enough -- unlike Lab 3's
   Controller/Plant split, there's no second program to launch separately.
6. `BatchManagerHarnessCD` (the smoke-test driver -- see `coordinator.sysj`'s
   bottom section) activates one 2-bottle batch and prints
   `COORDINATOR SYSTEMJ SMOKE CONTRACT PASSED` to the console once it drains
   cleanly. If nothing prints, or it hangs, that's the next thing to debug.

## After it compiles and runs

Report back (or just fix forward) whatever the compiler/runtime surfaces --
most likely candidates, in rough order of suspicion:
- the `{branch}||{branch}` synchronous-join blocks used to wait on several
  stations' channels at once (grounded in Lab 2 Exercise 6, but only tested
  there with 2 branches + `await`, not `send`/`receive` pairs like here)
- array/local-variable declarations inside a CD body (`String[]
  rotaryOccupant = new String[6];` etc.) -- SystemJ extends Java so this
  should work, but hasn't been verified for this exact shape
- the `weak abort(safetyPermitLost){ while(...) { ... } }` wrapping a loop
  containing nested `{}||{}` blocks and `receive` statements
