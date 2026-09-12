# Eric - Group Project work package

## Integration branch: Tony flat shim handoff

The `Integration/Eric` copy now includes four tested flat-protocol shim CDs for
Tony's coordinator interface. See [docs/FLAT_FINISHING_SHIM.md](docs/FLAT_FINISHING_SHIM.md)
for wiring, exact grammar, simulation assumptions and the first real-coordinator
checkpoint. This does not modify the underlying controller/plant pairs or prove
full-line integration. Use `Integration/Eric/scripts/build-and-test-systemj.ps1`
from the repository root to run both rich and flat SystemJ contracts.

This folder implements Eric's assigned contribution to the COMPSYS 704
Project 1 Group Project:

- lid-loader controller and separate simulated plant;
- capper controller and separate simulated plant;
- labeller controller and separate simulated plant;
- unloader controller and separate simulated plant;
- live Workpiece Tracker and baseline recoverable persistence; and
- Visualization Bridge and operator GUI.

The implementation is deliberately independent of any Individual Project
extension. A database, advanced fault-tolerant routing, or a richer digital
twin can replace the small interfaces later, but none is required to compile,
test, or demonstrate this GP baseline.

## Implemented behaviour

Each finishing controller implements the common lifecycle
`OFFLINE -> READY -> BUSY -> DONE/FAULT`. It validates machine, operation,
safety permit and preconditions; commands only its matching plant; and reports
`DONE` only after that plant returns sensor evidence. `DONE` and `FAULT` remain
latched until a correlated acknowledgement or safe reset. Optional
`timeoutTicks` job data exercises the timeout-to-FAULT path.

The executable SystemJ slice now accepts dynamic job/workpiece IDs for all four
finishing pairs, instead of a fixed one-bottle smoke scenario. Separate control
reactions receive permission/stop requests during a stepped plant cycle. Strict
correlation, terminal sensor checks, duplicate-job rejection, and explicit
ACK/repair/reset are covered by real channel tests. These are still isolated
pairs, not a connected central coordinator or full EABS. The SystemJ timeout is
a no-progress poll budget; a missing/unresponsive channel still needs a shared
watchdog strategy. See [docs/FINISHING_SYSTEMJ.md](docs/FINISHING_SYSTEMJ.md).

The Workpiece Tracker keeps the GP bottle twin: order, batch, product, recipe,
confirmed location/table position, current/next/completed operations, actual
stations, evidence timestamps, status/fault outcome and label payload. A
dispatch alone never changes a confirmed operation or location. Completed
twins are written atomically to a recoverable properties file.

The Swing dashboard shows order/batch counts, safety and diagnostic status,
input/rotary/output occupancy, controller states and live bottle twins. Its
controls emit `OperatorCommand` objects through the `VisualizationBridge`; the
GUI has no plant-actuator reference and therefore cannot bypass the shared
Production Coordinator.

The panel is also reusable as `EabsDashboardPanel(bridge, Mode.READ_ONLY)`
without operator controls. It displays extra reported controllers (for example
a backup filler), distinguishes `NO DATA` from OFFLINE, and shows confirmed
liquid A/B raw values without assuming units. The original constructor keeps
operator mode. Recovery-specific policy and controls remain in the IP; see
the shared-dashboard contract in [docs/INTEGRATION.md](docs/INTEGRATION.md).

## Layout

```text
Eric/
|-- config/                   # Executable SystemJ CD/channel configuration
|-- docs/                     # Shared integration contract and open decisions
|-- scripts/                  # Build, test and GUI launch scripts
|-- src/main/java/.../
|   |-- controllers/          # Four local machine controllers
|   |-- plants/               # Four separate deterministic plant models
|   |-- tracking/             # Authoritative live workpiece state
|   |-- persistence/          # Replaceable repository + recoverable GP baseline
|   |-- gui/                  # Visualization boundary and Swing dashboard
|   |-- model/                # Correlated immutable messages/state
|   |-- systemj/              # Dynamic wire codec and per-CD cycle/plant state
|   `-- tooling/              # Legacy SystemJ compiler compatibility launcher
|-- src/test/java/.../        # Dependency-free acceptance tests
`-- systemj/                  # Eight reusable device CDs + four test-only harnesses
```

The proposed cross-member interface, integration steps and decisions requiring
group agreement are in [docs/INTEGRATION.md](docs/INTEGRATION.md).

## Build and test

### Eclipse

Import `Eric` using **File > Import > General > Existing Projects into Workspace**.
Select the project `Eric` and leave **Copy projects into workspace**
unchecked to work directly in this Git checkout. The project uses the workspace's
default JRE and Java 8 source compatibility. Use an installed JDK 9 or newer for
the PowerShell scripts (`javac --release 8`).

Right-click `Eric Tests.launch` or `Eric GUI.launch` and choose **Run As >
Eric Tests** or **Run As > Eric GUI**, respectively (some Eclipse versions show
**Launch Configuration**). The test launch uses the Eric directory as its working
directory so generated previews and archives are placed in `build/`.

This configuration builds the Java components. Compile and run the SystemJ
contract using `scripts/build-and-test-systemj.ps1`; `.sysj` compilation is not
part of the Eclipse Java builder. Eclipse classes have a separate output directory,
`build/eclipse-classes`, from the scripts' `build/classes` output.

Prerequisites are a JDK with `java` and `javac` on `PATH`. The SystemJ build
script also requires PowerShell 7+ (`pwsh`), not Windows PowerShell 5.1.

```powershell
.\Eric\scripts\build-and-test.ps1
```

This compiles with Java 8-compatible bytecode and tests the normal bottle
route, all four controller/plant pairs, evidence-gated tracking, stale-result
rejection, latching/acknowledgement, fault/reset/safety/timeout behaviour,
multiple simultaneous workpieces, persistence recovery and the GUI boundary.
The shared GP/IP panel checks print `SHARED GP-IP DASHBOARD TESTS PASSED`
and render `build/shared-dashboard-readonly-preview.png` and
`build/shared-dashboard-operator-preview.png` for visual inspection.

To compile and execute the SystemJ clock-domain/channel contract, use:

```powershell
pwsh -NoProfile -File .\Eric\scripts\build-and-test-systemj.ps1
```

The script locates the supplied course SystemJ libraries automatically in the
known lab locations, or accepts `-SystemJLibPath <path>`. It compiles both
`finishing_devices.sysj` and `finishing_contract.sysj`, runs four
coordinator-harness/controller/plant paths (12 CDs), and requires a pass marker
for each plus an overall successful exit. Each path checks 71 channel responses:
multiple jobs, invalid/duplicate inputs, ACK correlation, precondition/fault/
timeout paths, active stop, safety loss and explicit recovery. The Java suite
also runs 348 dynamic endpoint checks. The last successful runtime output is
`build/systemj-dynamic-last.log`; its timestamp matters after any failed rerun.
The harness CDs stand in for the shared Production Coordinator during isolated
testing; they are not an alternative coordinator implementation.

To open the standalone visualization preview:

```powershell
.\Eric\scripts\run-gui.ps1
```

Runtime archives and compiled files are written under `Eric/build/`, which is
ignored by Git.
