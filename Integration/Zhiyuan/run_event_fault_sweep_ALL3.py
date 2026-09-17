#!/usr/bin/env python3
"""
EVENT-ALIGNED availability experiment.

Common testbench:
    ACTIVATE|B2|R2|PRODUCT_Y|2|35|65|ORDER-EXP

Architectures / fault targets:
    CENTRALISED: COORDINATOR
    DECOMPOSED:  BATCH, PRODUCTION, SAFETY, ALL3

ALL3 means one decomposed mission in which Batch, Production, and Safety
share one simultaneous outage window. Therefore --fault-reps 20 means
20 missions total, not 60.

The script no longer chooses fault points from elapsed milliseconds.
Instead, each fresh JVM waits for a semantic production event such as:
    FILLER_DONE occurrence 1
and injects the same HALT immediately after that event.

This is intended to isolate failure-domain effects from cross-architecture
scheduler/runtime differences.
"""

from __future__ import print_function

import argparse
import csv
import os
from pathlib import Path
import re
import shutil
import statistics
import subprocess
import time

RESULT_RE = re.compile(r"EXPERIMENT_RESULT\s+(?P<body>.*)$")

TARGETS = ("COORDINATOR", "BATCH", "PRODUCTION", "SAFETY", "ALL3")

RAW_FIELDS = [
    "architecture", "target", "event", "occurrence", "event_label",
    "repetition", "status", "terminal_status", "fault_reason",
    "event_matched", "event_match_mission_ms",
    "halt_triggered", "halt_recovered", "halt_start_after_event_ms",
    "requested_duration_ms", "actual_down_ms", "mission_ms",
    "completion_delta_vs_arch_baseline_median_ms",
    "coordinator_elapsed_ticks", "batch_elapsed_ticks",
    "production_elapsed_ticks", "safety_elapsed_ticks",
    "wall_seconds", "result_file", "debug_log"
]

SUMMARY_FIELDS = [
    "architecture", "target", "event", "occurrence", "event_label",
    "attempted_runs", "event_matched_runs", "actual_injections",
    "not_reached_runs", "matched_but_not_triggered_runs",
    "drained_after_injection", "faults_after_injection",
    "no_result_runs", "halt_recovered_runs",
    "failure_probability_given_injection", "fault_reasons",
    "median_event_match_mission_ms",
    "median_halt_start_after_event_ms",
    "requested_duration_ms", "median_actual_down_ms",
    "median_drained_mission_ms",
    "median_drained_completion_delta_ms",
    "median_impact_ratio_drained",
    "baseline_successes", "baseline_attempts", "baseline_failures",
    "baseline_success_rate", "baseline_median_ms", "baseline_mean_ms",
    "baseline_stdev_ms", "baseline_mad_ms", "baseline_noise_band_ms",
    "drained_impact_exceeds_noise_band"
]

BASELINE_FIELDS = [
    "architecture", "xml",
    "successful_baselines_required", "baseline_attempts",
    "baseline_failures", "baseline_success_rate",
    "median_ms", "mean_ms", "stdev_ms", "mad_ms", "noise_band_ms",
    "successful_mission_times_ms"
]


def parse_bool(value):
    return str(value).lower() == "true"


def parse_result_line(line):
    m = RESULT_RE.search(line.strip())
    if not m:
        return None

    f = {}
    for token in m.group("body").split():
        if "=" in token:
            k, v = token.split("=", 1)
            f[k] = v

    required = [
        "batch", "terminalStatus", "target", "injectMode",
        "injectEvent", "injectOccurrence", "eventMatched",
        "requestedDurationMs", "haltTriggered", "haltRecovered",
        "actualDownMs", "missionMs"
    ]

    if any(k not in f for k in required):
        return None

    return {
        "batch": f["batch"],
        "terminal_status": f["terminalStatus"],
        "fault_reason": f.get("faultReason", "NONE"),
        "target": f["target"],
        "inject_mode": f["injectMode"],
        "inject_event": f["injectEvent"],
        "inject_occurrence": int(f["injectOccurrence"]),
        "after_event_ms": int(f.get("afterEventMs", "0")),
        "event_matched": parse_bool(f["eventMatched"]),
        "matched_event_occurrence": int(f.get("matchedEventOccurrence", "0")),
        "event_match_mission_ms": float(f.get("eventMatchMissionMs", "-1")),
        "requested_duration_ms": int(f["requestedDurationMs"]),
        "halt_triggered": parse_bool(f["haltTriggered"]),
        "halt_recovered": parse_bool(f["haltRecovered"]),
        "halt_start_after_event_ms": float(f.get("haltStartAfterEventMs", "-1")),
        "actual_down_ms": float(f["actualDownMs"]),
        "mission_ms": float(f["missionMs"]),
        "coordinator_elapsed_ticks": int(f.get("coordinatorElapsedTicks", "0")),
        "batch_elapsed_ticks": int(f.get("batchElapsedTicks", "0")),
        "production_elapsed_ticks": int(f.get("productionElapsedTicks", "0")),
        "safety_elapsed_ticks": int(f.get("safetyElapsedTicks", "0")),
    }


def find_java():
    java_home = os.environ.get("JAVA_HOME")
    candidates = []

    if java_home:
        candidates.append(
            Path(java_home) / "bin" / ("java.exe" if os.name == "nt" else "java")
        )

    path_java = shutil.which("java")
    if path_java:
        candidates.append(Path(path_java))

    for p in candidates:
        if p.is_file():
            return str(p)

    raise SystemExit("Java executable not found. Set JAVA_HOME or put java on PATH.")


def default_classpath(root):
    build_root = root / "build"
    candidates = []

    if build_root.is_dir():
        for d in build_root.glob("eclipse-build-*"):
            classes = d / "classes"
            if classes.is_dir():
                try:
                    stamp = classes.stat().st_mtime
                except OSError:
                    stamp = 0
                candidates.append((stamp, classes))

    entries = []

    if candidates:
        candidates.sort(key=lambda x: x[0], reverse=True)
        entries.append(candidates[0][1])
        print("Using BuildAll classes:", candidates[0][1])
    else:
        zhiyuan_bin = root / "bin"
        eric_bin = root.parent / "Eric" / "bin"

        if not zhiyuan_bin.exists() or not eric_bin.exists():
            raise SystemExit(
                "No BuildAll classes directory and Eclipse bin fallback is incomplete."
            )

        entries.extend([zhiyuan_bin, eric_bin])

    lib = root / "lib"
    if lib.is_dir():
        entries.extend(sorted(lib.glob("*.jar")))

    return os.pathsep.join(str(x.resolve()) for x in entries)


def median_abs_deviation(values):
    med = statistics.median(values)
    return statistics.median([abs(x - med) for x in values])


def append_csv(path, fields, row):
    path.parent.mkdir(parents=True, exist_ok=True)
    new_file = not path.exists() or path.stat().st_size == 0

    with path.open("a", newline="", encoding="utf-8") as f:
        w = csv.DictWriter(f, fieldnames=fields)
        if new_file:
            w.writeheader()
        w.writerow(row)
        f.flush()


def write_csv(path, fields, rows):
    path.parent.mkdir(parents=True, exist_ok=True)

    with path.open("w", newline="", encoding="utf-8") as f:
        w = csv.DictWriter(f, fieldnames=fields)
        w.writeheader()
        w.writerows(rows)


def architecture_for_target(target, central_xml, decomposed_xml):
    if target == "COORDINATOR":
        return "CENTRALISED", central_xml

    return "DECOMPOSED_3CD", decomposed_xml


def load_event_plan(path):
    rows = []

    with path.open("r", newline="", encoding="utf-8-sig") as f:
        for row in csv.DictReader(f):
            event = row["event"].strip().upper()
            occurrence = int(row["occurrence"])
            label = row.get("label", "").strip()

            if not event or occurrence < 1:
                raise SystemExit("Invalid event plan row: %r" % row)

            rows.append({
                "event": event,
                "occurrence": occurrence,
                "label": label,
            })

    if not rows:
        raise SystemExit("Event plan is empty: " + str(path))

    return rows


def run_once(
    java, classpath, root, xml, target, batch,
    event, occurrence, after_event_ms, duration_ms,
    timeout_s, results_dir, run_name,
    fast_harness, debug_output, extra_props
):
    result_file = results_dir / (run_name + ".result.txt")
    debug_log = results_dir / (run_name + ".log")
    results_dir.mkdir(parents=True, exist_ok=True)

    if result_file.exists():
        result_file.unlink()

    props = [
        "-Djava.awt.headless=true",
        "-Deric.finishing.flatSimulation=true",
        "-Dexperiment.enabled=true",
        "-Dexperiment.exitOnResult=true",
        "-Dexperiment.target=" + target,
        "-Dexperiment.batch=" + batch,
        "-Dexperiment.injectMode=EVENT",
        "-Dexperiment.injectEvent=" + event,
        "-Dexperiment.injectOccurrence=" + str(occurrence),
        "-Dexperiment.afterEventMs=" + str(after_event_ms),
        "-Dexperiment.durationMs=" + str(duration_ms),
        "-Dexperiment.resultFile=" + str(result_file.resolve()),
    ]

    if fast_harness:
        props.append("-Davailability.fastHarness=true")

    for item in extra_props:
        props.append("-D" + item)

    cmd = [java, "-Xmx256m"] + props + [
        "-cp", classpath,
        "com.systemj.SystemJRunner",
        xml
    ]

    started = time.time()

    try:
        if debug_output:
            with debug_log.open("w", encoding="utf-8") as log:
                proc = subprocess.run(
                    cmd, cwd=str(root),
                    stdout=log, stderr=subprocess.STDOUT,
                    universal_newlines=True, timeout=timeout_s
                )
        else:
            proc = subprocess.run(
                cmd, cwd=str(root),
                stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL,
                timeout=timeout_s
            )

        process_status = "PROCESS_OK" if proc.returncode == 0 else "PROCESS_ERROR"

    except subprocess.TimeoutExpired:
        process_status = "TIMEOUT"

    parsed = None

    if result_file.is_file():
        try:
            for line in result_file.read_text(
                encoding="utf-8", errors="replace"
            ).splitlines():
                candidate = parse_result_line(line)
                if candidate is not None:
                    parsed = candidate
        except Exception:
            parsed = None

    if parsed is None:
        status = process_status if process_status != "PROCESS_OK" else "NO_RESULT"
    elif not parsed["event_matched"]:
        status = "EVENT_NOT_REACHED"
    elif not parsed["halt_triggered"]:
        status = "MATCHED_NOT_TRIGGERED"
    elif parsed["terminal_status"] == "DRAINED":
        status = "DRAINED"
    elif parsed["terminal_status"] == "FAULT":
        status = "FAULT"
    else:
        status = parsed["terminal_status"]

    return {
        "status": status,
        "wall_seconds": round(time.time() - started, 6),
        "result": parsed,
        "result_file": str(result_file),
        "debug_log": str(debug_log) if debug_output else "",
    }


def collect_baseline(
    architecture, xml, java, cp, root, batch,
    successes_required, max_attempts,
    timeout_s, results_dir,
    fast_harness, debug_output, extra_props,
    raw_path
):
    missions = []
    attempts = 0
    failures = 0

    print("")
    print("[%s] collect %d successful no-fault baselines (max %d attempts)"
          % (architecture, successes_required, max_attempts))

    while len(missions) < successes_required and attempts < max_attempts:
        attempts += 1

        run_name = "baseline_%s_%02d" % (architecture.lower(), attempts)

        r = run_once(
            java, cp, root, xml,
            "NONE", batch,
            "BATCH_ACCEPTED", 1, 0, 0,
            timeout_s, results_dir, run_name,
            fast_harness, debug_output, extra_props
        )

        p = r["result"]
        ok = p is not None and p["terminal_status"] == "DRAINED"

        if ok:
            missions.append(p["mission_ms"])
            print("  attempt %d: PASS %.3f ms (%d/%d)"
                  % (attempts, p["mission_ms"], len(missions), successes_required))
        else:
            failures += 1
            reason = p["fault_reason"] if p else r["status"]
            print("  attempt %d: FAIL status=%s reason=%s"
                  % (attempts, r["status"], reason))

        append_csv(raw_path, RAW_FIELDS, {
            "architecture": architecture,
            "target": "NONE",
            "event": "BASELINE",
            "occurrence": "",
            "event_label": "",
            "repetition": attempts,
            "status": r["status"],
            "terminal_status": p["terminal_status"] if p else "",
            "fault_reason": p["fault_reason"] if p else "",
            "event_matched": p["event_matched"] if p else "",
            "event_match_mission_ms": p["event_match_mission_ms"] if p else "",
            "halt_triggered": p["halt_triggered"] if p else "",
            "halt_recovered": p["halt_recovered"] if p else "",
            "halt_start_after_event_ms": p["halt_start_after_event_ms"] if p else "",
            "requested_duration_ms": 0,
            "actual_down_ms": p["actual_down_ms"] if p else "",
            "mission_ms": p["mission_ms"] if p else "",
            "completion_delta_vs_arch_baseline_median_ms": "",
            "coordinator_elapsed_ticks": p["coordinator_elapsed_ticks"] if p else "",
            "batch_elapsed_ticks": p["batch_elapsed_ticks"] if p else "",
            "production_elapsed_ticks": p["production_elapsed_ticks"] if p else "",
            "safety_elapsed_ticks": p["safety_elapsed_ticks"] if p else "",
            "wall_seconds": r["wall_seconds"],
            "result_file": r["result_file"],
            "debug_log": r["debug_log"],
        })

    if len(missions) < successes_required:
        raise SystemExit(
            "%s could not collect %d successful baselines within %d attempts."
            % (architecture, successes_required, max_attempts)
        )

    med = statistics.median(missions)
    mean = statistics.mean(missions)
    stdev = statistics.stdev(missions) if len(missions) > 1 else 0.0
    mad = median_abs_deviation(missions)

    noise = max(100.0, 3.0 * mad, 2.0 * stdev)

    return {
        "architecture": architecture,
        "xml": xml,
        "missions": missions,
        "successes": len(missions),
        "attempts": attempts,
        "failures": failures,
        "success_rate": float(len(missions)) / float(attempts),
        "median": med,
        "mean": mean,
        "stdev": stdev,
        "mad": mad,
        "noise": noise,
    }


def summarize_point(rows, baseline, architecture, target, plan, duration_ms):
    attempted = len(rows)

    matched = [
        r for r in rows
        if r["parsed"] is not None and r["parsed"]["event_matched"]
    ]

    injected = [
        r for r in matched
        if r["parsed"]["halt_triggered"]
    ]

    not_reached = attempted - len(matched)
    matched_not_triggered = len(matched) - len(injected)

    drained = [
        r for r in injected
        if r["parsed"]["terminal_status"] == "DRAINED"
    ]

    faulted = [
        r for r in injected
        if r["parsed"]["terminal_status"] == "FAULT"
    ]

    no_result = [r for r in rows if r["parsed"] is None]

    recovered = [
        r for r in injected
        if r["parsed"]["halt_recovered"]
    ]

    event_times = [
        r["parsed"]["event_match_mission_ms"]
        for r in matched
        if r["parsed"]["event_match_mission_ms"] >= 0.0
    ]

    start_lags = [
        r["parsed"]["halt_start_after_event_ms"]
        for r in injected
        if r["parsed"]["halt_start_after_event_ms"] >= 0.0
    ]

    down = [r["parsed"]["actual_down_ms"] for r in injected]

    drained_mission = [r["parsed"]["mission_ms"] for r in drained]

    drained_delta = [
        r["parsed"]["mission_ms"] - baseline["median"]
        for r in drained
    ]

    fail_prob = (
        float(len(faulted)) / float(len(injected))
        if injected else ""
    )

    reasons = {}

    for r in faulted:
        reason = r["parsed"]["fault_reason"]
        reasons[reason] = reasons.get(reason, 0) + 1

    reason_text = ";".join(
        "%s:%d" % (k, reasons[k]) for k in sorted(reasons)
    )

    med_down = statistics.median(down) if down else ""
    med_delta = statistics.median(drained_delta) if drained_delta else ""

    if med_down != "" and med_delta != "" and med_down > 0.0:
        impact_ratio = float(med_delta) / float(med_down)
    else:
        impact_ratio = ""

    return {
        "architecture": architecture,
        "target": target,
        "event": plan["event"],
        "occurrence": plan["occurrence"],
        "event_label": plan["label"],
        "attempted_runs": attempted,
        "event_matched_runs": len(matched),
        "actual_injections": len(injected),
        "not_reached_runs": not_reached,
        "matched_but_not_triggered_runs": matched_not_triggered,
        "drained_after_injection": len(drained),
        "faults_after_injection": len(faulted),
        "no_result_runs": len(no_result),
        "halt_recovered_runs": len(recovered),
        "failure_probability_given_injection": fail_prob,
        "fault_reasons": reason_text,
        "median_event_match_mission_ms":
            statistics.median(event_times) if event_times else "",
        "median_halt_start_after_event_ms":
            statistics.median(start_lags) if start_lags else "",
        "requested_duration_ms": duration_ms,
        "median_actual_down_ms": med_down,
        "median_drained_mission_ms":
            statistics.median(drained_mission) if drained_mission else "",
        "median_drained_completion_delta_ms": med_delta,
        "median_impact_ratio_drained": impact_ratio,
        "baseline_successes": baseline["successes"],
        "baseline_attempts": baseline["attempts"],
        "baseline_failures": baseline["failures"],
        "baseline_success_rate": baseline["success_rate"],
        "baseline_median_ms": baseline["median"],
        "baseline_mean_ms": baseline["mean"],
        "baseline_stdev_ms": baseline["stdev"],
        "baseline_mad_ms": baseline["mad"],
        "baseline_noise_band_ms": baseline["noise"],
        "drained_impact_exceeds_noise_band":
            1 if med_delta != "" and abs(float(med_delta)) >= baseline["noise"] else 0,
    }


def main():
    ap = argparse.ArgumentParser()

    ap.add_argument("--project-root", default=".")
    ap.add_argument("--central-xml", default="sysj/coordinator.xml")
    ap.add_argument(
        "--decomposed-xml",
        default="sysj/IntegratedCoordinatorAcceptance_3CD.xml"
    )

    ap.add_argument(
        "--targets",
        default="COORDINATOR,BATCH,PRODUCTION,SAFETY"
    )

    ap.add_argument("--batch", default="B2")
    ap.add_argument("--event-plan", default="event_plan.csv")

    ap.add_argument("--fault-reps", type=int, default=5)
    ap.add_argument("--duration-ms", type=int, default=500)
    ap.add_argument("--after-event-ms", type=int, default=0)

    ap.add_argument("--baseline-runs", type=int, default=5)
    ap.add_argument("--max-baseline-attempts", type=int, default=15)

    ap.add_argument("--timeout", type=float, default=60.0)

    ap.add_argument("--raw-output", default="event_fault_raw.csv")
    ap.add_argument("--summary-output", default="event_fault_summary.csv")
    ap.add_argument("--baseline-output", default="event_fault_baselines.csv")
    ap.add_argument("--results-dir", default="experiment_runs_event_v4")

    ap.add_argument("--full-acceptance", action="store_true")
    ap.add_argument("--debug-output", action="store_true")
    ap.add_argument("--java-prop", action="append", default=[])

    args = ap.parse_args()

    if args.fault_reps < 1:
        raise SystemExit("--fault-reps must be >= 1")

    if args.duration_ms <= 0:
        raise SystemExit("--duration-ms must be > 0")

    if args.after_event_ms < 0:
        raise SystemExit("--after-event-ms must be >= 0")

    if args.baseline_runs < 1:
        raise SystemExit("--baseline-runs must be >= 1")

    if args.max_baseline_attempts < args.baseline_runs:
        raise SystemExit(
            "--max-baseline-attempts must be >= --baseline-runs"
        )

    root = Path(args.project_root).resolve()

    central_xml = root / args.central_xml
    decomposed_xml = root / args.decomposed_xml
    event_plan_path = root / args.event_plan

    for p in (central_xml, decomposed_xml, event_plan_path):
        if not p.is_file():
            raise SystemExit("Required file not found: " + str(p))

    requested_targets = [
        x.strip().upper()
        for x in args.targets.split(",")
        if x.strip()
    ]

    bad = [x for x in requested_targets if x not in TARGETS]
    if bad:
        raise SystemExit(
            "Unknown targets: " + ",".join(bad)
        )

    plan = load_event_plan(event_plan_path)

    java = find_java()
    cp = default_classpath(root)

    fast_harness = not args.full_acceptance

    raw_path = root / args.raw_output
    summary_path = root / args.summary_output
    baseline_path = root / args.baseline_output
    results_dir = root / args.results_dir

    for p in (raw_path, summary_path, baseline_path):
        if p.exists():
            p.unlink()

    print("EVENT-ALIGNED availability experiment")
    print("Harness:", "FAST B2-only" if fast_harness else "FULL acceptance")
    print("Targets:", ",".join(requested_targets))
    if "ALL3" in requested_targets:
        print("ALL3 mode: BATCH + PRODUCTION + SAFETY share one simultaneous outage window.")
        print("IMPORTANT: GlobalExperiment.java must support experiment.target=ALL3.")
    print("Event points:", len(plan))
    print("Fault repetitions:", args.fault_reps)
    print("HALT duration: %d ms" % args.duration_ms)
    print("Post-event delay: %d ms" % args.after_event_ms)
    print("Nominal fault JVM runs:",
          len(plan) * len(requested_targets) * args.fault_reps)

    needed_architectures = []

    for target in requested_targets:
        architecture, xml = architecture_for_target(
            target, args.central_xml, args.decomposed_xml
        )

        if architecture not in [x[0] for x in needed_architectures]:
            needed_architectures.append((architecture, xml))

    baselines = {}

    for architecture, xml in needed_architectures:
        baseline = collect_baseline(
            architecture, xml,
            java, cp, root, args.batch,
            args.baseline_runs, args.max_baseline_attempts,
            args.timeout, results_dir,
            fast_harness, args.debug_output,
            args.java_prop, raw_path
        )

        baselines[architecture] = baseline

        append_csv(baseline_path, BASELINE_FIELDS, {
            "architecture": architecture,
            "xml": xml,
            "successful_baselines_required": args.baseline_runs,
            "baseline_attempts": baseline["attempts"],
            "baseline_failures": baseline["failures"],
            "baseline_success_rate": baseline["success_rate"],
            "median_ms": baseline["median"],
            "mean_ms": baseline["mean"],
            "stdev_ms": baseline["stdev"],
            "mad_ms": baseline["mad"],
            "noise_band_ms": baseline["noise"],
            "successful_mission_times_ms":
                ";".join("%.6f" % x for x in baseline["missions"]),
        })

        print("[%s] baseline median %.3f ms, noise %.3f ms"
              % (architecture, baseline["median"], baseline["noise"]))

    summary_rows = []

    for target in requested_targets:
        architecture, xml = architecture_for_target(
            target, args.central_xml, args.decomposed_xml
        )

        baseline = baselines[architecture]

        print("")
        print("==== %s / %s ====" % (architecture, target))

        for event_case in plan:
            point_rows = []

            event = event_case["event"]
            occurrence = event_case["occurrence"]

            for rep in range(1, args.fault_reps + 1):
                print("[%s] %s#%d rep=%d/%d"
                      % (target, event, occurrence, rep, args.fault_reps))

                safe_event = re.sub(r"[^A-Za-z0-9]+", "_", event.lower())

                run_name = "%s_%s_%02d_rep%02d" % (
                    target.lower(),
                    safe_event,
                    occurrence,
                    rep
                )

                r = run_once(
                    java, cp, root, xml,
                    target, args.batch,
                    event, occurrence,
                    args.after_event_ms, args.duration_ms,
                    args.timeout, results_dir, run_name,
                    fast_harness, args.debug_output,
                    args.java_prop
                )

                p = r["result"]

                delta = ""

                if (
                    p is not None
                    and p["halt_triggered"]
                    and p["terminal_status"] == "DRAINED"
                ):
                    delta = p["mission_ms"] - baseline["median"]

                append_csv(raw_path, RAW_FIELDS, {
                    "architecture": architecture,
                    "target": target,
                    "event": event,
                    "occurrence": occurrence,
                    "event_label": event_case["label"],
                    "repetition": rep,
                    "status": r["status"],
                    "terminal_status": p["terminal_status"] if p else "",
                    "fault_reason": p["fault_reason"] if p else "",
                    "event_matched": p["event_matched"] if p else "",
                    "event_match_mission_ms":
                        p["event_match_mission_ms"] if p else "",
                    "halt_triggered": p["halt_triggered"] if p else "",
                    "halt_recovered": p["halt_recovered"] if p else "",
                    "halt_start_after_event_ms":
                        p["halt_start_after_event_ms"] if p else "",
                    "requested_duration_ms": args.duration_ms,
                    "actual_down_ms": p["actual_down_ms"] if p else "",
                    "mission_ms": p["mission_ms"] if p else "",
                    "completion_delta_vs_arch_baseline_median_ms": delta,
                    "coordinator_elapsed_ticks":
                        p["coordinator_elapsed_ticks"] if p else "",
                    "batch_elapsed_ticks":
                        p["batch_elapsed_ticks"] if p else "",
                    "production_elapsed_ticks":
                        p["production_elapsed_ticks"] if p else "",
                    "safety_elapsed_ticks":
                        p["safety_elapsed_ticks"] if p else "",
                    "wall_seconds": r["wall_seconds"],
                    "result_file": r["result_file"],
                    "debug_log": r["debug_log"],
                })

                point_rows.append({
                    "parsed": p,
                    "runner_status": r["status"],
                })

            summary_rows.append(
                summarize_point(
                    point_rows,
                    baseline,
                    architecture,
                    target,
                    event_case,
                    args.duration_ms
                )
            )

    write_csv(summary_path, SUMMARY_FIELDS, summary_rows)

    print("")
    print("DONE")
    print("Raw:", raw_path)
    print("Summary:", summary_path)
    print("Baselines:", baseline_path)
    print("Result files:", results_dir)


if __name__ == "__main__":
    main()
