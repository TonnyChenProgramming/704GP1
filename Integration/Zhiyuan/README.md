# CoordinatorSystemJ — integrated simulation

This is the repaired, runnable coordinator on the integration branch. It uses real
SystemJ controller/plant pairs from the copied Tonny sources in this folder and
Eric's finishing shims/devices in `../Eric`. It is no longer the all-stub smoke test.

Start with [COORDINATOR_INTEGRATION.md](COORDINATOR_INTEGRATION.md) for the protocol,
changes/reasons, safety limitations and team handoff.

## Build and test

From the repository root, in PowerShell 7:

```powershell
pwsh -NoProfile -File Integration/Zhiyuan/scripts/test-coordinator.ps1
# Add -Gui to show the read-only dashboard during the normal acceptance run.
```

The script checks Java/model tests, generates and compiles 23 CD classes, runs a
22-CD normal integration (8 + 2 bottles), checks ten persisted records in another
JVM, then runs the alternative LID-fault harness against the real device graph.
It rejects compiler diagnostics even when the legacy compiler exits with code 0.
All output goes under an ignored, unique `build/verification-<id>/`.

## Eclipse steps

1. Import **both** existing projects from `Integration/Eric` (project name
   **Eric**) and `Integration/Zhiyuan` (**CoordinatorSystemJ**). Do not import
   the old root-level Eric project under the same name.
2. Use a JDK with `java` and `javac` on PATH. PowerShell 7 is also required.
   The Eclipse entry script uses `pwsh` on PATH, with the existing local bundled
   runtime as a fallback on Eric's computer.
3. Select **Run > External Tools > External Tools Configurations > Program >
   BuildAll**. This is now an External Tools entry, not the old Java-application
   compiler entry. If Eclipse still shows an old Java **BuildAll**, use the new
   shared `BuildAll.launch` or remove only that obsolete launch configuration.
4. Run it and wait for **COORDINATOR BUILD AND TEST PASSED**. A full clean build
   can take several minutes with the course compiler. Before the first successful
   run Eclipse may flag the missing `build/eclipse-generated` source folder;
   the build script creates it after verification.
5. Refresh both projects (**F5**), enable **Project > Build Automatically**, and
   inspect **Problems** for errors. Generated SystemJ Java is in
   `build/eclipse-generated`; edit `.sysj` source, not generated Java.
6. Use **Run > Run Configurations > Java Application > RunCoordinator** to run
   the verified normal batch harness again with the read-only GUI. It exits
   after both test batches finish. Output archives use a fresh UUID filename.
7. To test source changes, rerun **BuildAll** first. Eclipse's Java builder alone
   does not translate SystemJ source.

These launch files were configured and XML-checked; the automated verification
uses the same compiler/runtime directly. Native Eclipse clicks are not part of
the automated acceptance test.

## Not yet the finished Group Project

The batch driver is a test harness, not the real POS. Tony must send the explicit
eight-field activation and handle REJECTED/FAULT/DRAINED. The flat protocol has no
active STOP/PERMIT/RESET handshake; the current HOLD is not an emergency stop.
Tonny's rotary occupancy mask and independent fill measurements remain simulation
limitations. See the handoff document before claiming full GP completion.

The previous compiler investigation is preserved in Git history (baseline
`cb9ed9d`). Legacy `TrackerPort`, `TwinView`, and `StubTracker` sources are
retained but are not used by this integrated runtime.
