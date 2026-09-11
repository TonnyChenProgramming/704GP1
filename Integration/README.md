# Integration

This folder puts all three subsystems side by side as a starting point for
group integration. Each subfolder is a straight copy of that person's
already-working project (source, SystemJ files, build/run configs, and the
libraries it needs to compile) with compiled `.class` output, IDE `.settings`
caches, and non-source art files (`.xcf`, `.drawio`) stripped out.

| Folder | Owner | Contents |
|---|---|---|
| `Tonny/` | Tonny | BottleLoader/Conveyor/RotaryTable/TwoLiquidFiller controllers, plants, and SystemJ models |
| `Eric/` | Eric | Finishing subsystem: controllers, plants, dashboard, and SystemJ finishing models |
| `Zhiyuan/` | Zhiyuan | Production Coordinator SystemJ model and its supporting Java classes |

Each project still builds and runs independently, exactly as it did in its
original folder — nothing here has been rewired.

## What's NOT done yet

These three subsystems are not wired to each other. Per Eric's own
[`Eric/docs/INTEGRATION.md`](../Eric/docs/INTEGRATION.md), the group still
needs to agree on:

- The shared SystemJ channel names and payload encoding between the
  coordinator and each subsystem (each subsystem currently talks to
  harness/stub CDs standing in for the real coordinator).
- Merging each member's controller/plant XML wiring into one shared XML
  configuration with a single end-to-end run entry point.
- Timeout values, safe-stop points, fault-clear ownership, and reset
  acknowledgement semantics.

This folder is meant to make that next conversation easier by having
everyone's real code in one place — it is not itself a finished integration.
