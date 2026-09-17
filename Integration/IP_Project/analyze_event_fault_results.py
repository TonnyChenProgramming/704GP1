#!/usr/bin/env python3
"""
Comprehensive analyser for the V4 event-aligned availability/fault-injection CSV.

Input:
    event_fault_raw.csv   (or any CSV/text file with the same header)

Outputs (under --output-dir):
    report.md
    baseline_summary.csv
    target_summary.csv
    event_target_summary.csv
    event_failure_matrix.csv
    event_success_matrix.csv
    event_reachability_matrix.csv
    event_remaining_time_matrix.csv
    event_timing_alignment.csv
    reference_comparison.csv
    fault_reason_summary.csv
    data_quality_flags.csv
    actual_injections.csv
    pre_event_failures.csv
    plots/*.png                (when matplotlib is available)

Primary interpretation:
  * mission_success_given_injection = P(DRAINED | haltTriggered=True)
  * mission_failure_given_injection = P(FAULT   | haltTriggered=True)
  * pre_event_failure_rate is kept separate; it is NOT attributed to injection.
  * remaining_time_ms = mission_ms - event_match_mission_ms for injected runs.

Important:
  This script does not call P(DRAINED | injection) "steady-state availability".
  It reports it as empirical mission success / fault-tolerance coverage. A true
  time-availability metric needs an explicit service-up/service-down definition.

The script intentionally does not use pandas/scipy so it is easy to run in the
course project environment.
"""

from __future__ import annotations

import argparse
import csv
import math
import statistics
from collections import Counter, OrderedDict, defaultdict
from pathlib import Path
from typing import Dict, Iterable, List, Optional, Sequence, Tuple


# -----------------------------------------------------------------------------
# Parsing helpers
# -----------------------------------------------------------------------------


def s(value) -> str:
    return "" if value is None else str(value).strip()


def as_bool(value) -> bool:
    return s(value).lower() == "true"


def as_float(value) -> Optional[float]:
    text = s(value)
    if text == "":
        return None
    try:
        return float(text)
    except ValueError:
        return None


def as_int(value) -> Optional[int]:
    text = s(value)
    if text == "":
        return None
    try:
        return int(float(text))
    except ValueError:
        return None


def finite(values: Iterable[Optional[float]]) -> List[float]:
    out = []
    for x in values:
        if x is not None and math.isfinite(x):
            out.append(float(x))
    return out


# -----------------------------------------------------------------------------
# Statistics helpers
# -----------------------------------------------------------------------------


def percentile(values: Sequence[float], p: float) -> Optional[float]:
    xs = sorted(finite(values))
    if not xs:
        return None
    if len(xs) == 1:
        return xs[0]
    p = min(1.0, max(0.0, p))
    pos = (len(xs) - 1) * p
    lo = int(math.floor(pos))
    hi = int(math.ceil(pos))
    if lo == hi:
        return xs[lo]
    frac = pos - lo
    return xs[lo] * (1.0 - frac) + xs[hi] * frac


def median(values: Iterable[Optional[float]]) -> Optional[float]:
    xs = finite(values)
    return statistics.median(xs) if xs else None


def mean(values: Iterable[Optional[float]]) -> Optional[float]:
    xs = finite(values)
    return statistics.mean(xs) if xs else None


def stdev(values: Iterable[Optional[float]]) -> Optional[float]:
    xs = finite(values)
    if not xs:
        return None
    return statistics.stdev(xs) if len(xs) >= 2 else 0.0


def mad(values: Iterable[Optional[float]]) -> Optional[float]:
    xs = finite(values)
    if not xs:
        return None
    m = statistics.median(xs)
    return statistics.median(abs(x - m) for x in xs)


def fmt(x, digits=3) -> str:
    if x is None or x == "":
        return ""
    if isinstance(x, bool):
        return "True" if x else "False"
    if isinstance(x, int):
        return str(x)
    try:
        return f"{float(x):.{digits}f}"
    except (ValueError, TypeError):
        return str(x)


def ratio(n: int, d: int) -> Optional[float]:
    return (float(n) / float(d)) if d else None


def wilson_interval(successes: int, n: int, z: float = 1.959963984540054) -> Tuple[Optional[float], Optional[float]]:
    """Wilson score interval for a binomial proportion."""
    if n <= 0:
        return None, None
    p = successes / float(n)
    z2 = z * z
    denom = 1.0 + z2 / n
    centre = (p + z2 / (2.0 * n)) / denom
    half = (z / denom) * math.sqrt((p * (1.0 - p) / n) + z2 / (4.0 * n * n))
    return max(0.0, centre - half), min(1.0, centre + half)


def safe_div(a: Optional[float], b: Optional[float]) -> Optional[float]:
    if a is None or b is None or b == 0:
        return None
    return a / b


def largest_sorted_gap(values: Iterable[Optional[float]]) -> Optional[float]:
    xs = sorted(finite(values))
    if len(xs) < 2:
        return 0.0 if xs else None
    return max(xs[i + 1] - xs[i] for i in range(len(xs) - 1))


# -----------------------------------------------------------------------------
# IO helpers
# -----------------------------------------------------------------------------


def load_rows(path: Path) -> List[dict]:
    with path.open("r", newline="", encoding="utf-8-sig") as f:
        reader = csv.DictReader(f)
        required = {
            "architecture", "target", "event", "occurrence", "repetition",
            "status", "terminal_status", "fault_reason", "event_matched",
            "event_match_mission_ms", "halt_triggered", "halt_recovered",
            "halt_start_after_event_ms", "requested_duration_ms",
            "actual_down_ms", "mission_ms"
        }
        missing = sorted(required - set(reader.fieldnames or []))
        if missing:
            raise SystemExit("Input CSV is missing required columns: " + ", ".join(missing))

        rows = []
        for index, raw in enumerate(reader, start=2):
            row = dict(raw)
            row["_line"] = index
            row["_event_matched"] = as_bool(raw.get("event_matched"))
            row["_halt_triggered"] = as_bool(raw.get("halt_triggered"))
            row["_halt_recovered"] = as_bool(raw.get("halt_recovered"))
            row["_occurrence"] = as_int(raw.get("occurrence"))
            row["_repetition"] = as_int(raw.get("repetition"))
            row["_event_match_ms"] = as_float(raw.get("event_match_mission_ms"))
            row["_halt_lag_ms"] = as_float(raw.get("halt_start_after_event_ms"))
            row["_requested_down_ms"] = as_float(raw.get("requested_duration_ms"))
            row["_actual_down_ms"] = as_float(raw.get("actual_down_ms"))
            row["_mission_ms"] = as_float(raw.get("mission_ms"))
            if row["_event_match_ms"] is not None and row["_mission_ms"] is not None and row["_event_match_ms"] >= 0:
                row["_remaining_time_ms"] = row["_mission_ms"] - row["_event_match_ms"]
            else:
                row["_remaining_time_ms"] = None
            if row["_actual_down_ms"] is not None and row["_requested_down_ms"] is not None:
                row["_down_error_ms"] = row["_actual_down_ms"] - row["_requested_down_ms"]
            else:
                row["_down_error_ms"] = None
            rows.append(row)
        return rows


def write_csv(path: Path, fieldnames: Sequence[str], rows: Sequence[dict]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", newline="", encoding="utf-8") as f:
        w = csv.DictWriter(f, fieldnames=fieldnames, extrasaction="ignore")
        w.writeheader()
        for row in rows:
            w.writerow({k: row.get(k, "") for k in fieldnames})


def copy_rows_as_csv(path: Path, original_header: Sequence[str], rows: Sequence[dict]) -> None:
    clean = []
    for r in rows:
        clean.append({k: r.get(k, "") for k in original_header})
    write_csv(path, original_header, clean)


# -----------------------------------------------------------------------------
# Key construction / grouping
# -----------------------------------------------------------------------------


def is_baseline(row: dict) -> bool:
    return s(row.get("target")).upper() == "NONE" or s(row.get("event")).upper() == "BASELINE"


def is_actual_injection(row: dict) -> bool:
    return (not is_baseline(row)) and row["_halt_triggered"]


def is_pre_event_failure(row: dict) -> bool:
    return (
        (not is_baseline(row))
        and (not row["_event_matched"])
        and (not row["_halt_triggered"])
        and s(row.get("terminal_status")).upper() == "FAULT"
    )


def event_key(row: dict) -> Tuple[str, int, str]:
    return (
        s(row.get("event")).upper(),
        row.get("_occurrence") or 0,
        s(row.get("event_label")),
    )


def key_label(key: Tuple[str, int, str]) -> str:
    event, occurrence, _label = key
    return f"{event}#{occurrence}" if occurrence else event


# -----------------------------------------------------------------------------
# Analyses
# -----------------------------------------------------------------------------


def baseline_analysis(rows: Sequence[dict]) -> List[dict]:
    by_arch = defaultdict(list)
    for r in rows:
        if is_baseline(r):
            by_arch[s(r.get("architecture"))].append(r)

    out = []
    for arch in sorted(by_arch):
        rs = by_arch[arch]
        successes = [r for r in rs if s(r.get("terminal_status")).upper() == "DRAINED" and r["_mission_ms"] is not None]
        failures = [r for r in rs if s(r.get("terminal_status")).upper() == "FAULT"]
        missions = [r["_mission_ms"] for r in successes]
        med = median(missions)
        sd = stdev(missions)
        robust_mad = mad(missions)
        p25 = percentile(missions, 0.25)
        p75 = percentile(missions, 0.75)
        rng = (max(missions) - min(missions)) if missions else None
        max_gap = largest_sorted_gap(missions)
        high_variance = bool(
            (sd is not None and sd > 500.0)
            or (rng is not None and rng > 1000.0)
        )
        out.append({
            "architecture": arch,
            "baseline_attempts": len(rs),
            "baseline_successes": len(successes),
            "baseline_failures": len(failures),
            "baseline_success_rate": ratio(len(successes), len(rs)),
            "mission_median_ms": med,
            "mission_mean_ms": mean(missions),
            "mission_stdev_ms": sd,
            "mission_mad_ms": robust_mad,
            "mission_q1_ms": p25,
            "mission_q3_ms": p75,
            "mission_iqr_ms": (p75 - p25) if p25 is not None and p75 is not None else None,
            "mission_min_ms": min(missions) if missions else None,
            "mission_max_ms": max(missions) if missions else None,
            "mission_range_ms": rng,
            "largest_sorted_gap_ms": max_gap,
            "high_variance_warning": high_variance,
            "failure_reasons": ";".join(f"{k}:{v}" for k, v in sorted(Counter(s(r.get('fault_reason')) for r in failures).items())),
        })
    return out


def event_target_analysis(rows: Sequence[dict], event_order: Sequence[Tuple[str, int, str]], target_order: Sequence[str]) -> List[dict]:
    grouped = defaultdict(list)
    for r in rows:
        if not is_baseline(r):
            grouped[(s(r.get("target")).upper(), event_key(r))].append(r)

    out = []
    for key in event_order:
        for target in target_order:
            rs = grouped.get((target, key), [])
            if not rs:
                continue

            matched = [r for r in rs if r["_event_matched"]]
            injected = [r for r in rs if r["_halt_triggered"]]
            drained = [r for r in injected if s(r.get("terminal_status")).upper() == "DRAINED"]
            faulted = [r for r in injected if s(r.get("terminal_status")).upper() == "FAULT"]
            recovered = [r for r in injected if r["_halt_recovered"]]
            pre_fail = [r for r in rs if is_pre_event_failure(r)]
            matched_not_triggered = [r for r in matched if not r["_halt_triggered"]]
            no_result = [r for r in rs if s(r.get("status")).upper() in {"NO_RESULT", "TIMEOUT", "PROCESS_ERROR"}]

            n_inj = len(injected)
            n_fault = len(faulted)
            n_drain = len(drained)
            fail_p = ratio(n_fault, n_inj)
            success_p = ratio(n_drain, n_inj)
            fail_lo, fail_hi = wilson_interval(n_fault, n_inj)
            succ_lo, succ_hi = wilson_interval(n_drain, n_inj)

            fault_reasons = Counter(s(r.get("fault_reason")) for r in faulted)
            pre_reasons = Counter(s(r.get("fault_reason")) for r in pre_fail)

            event_times = [r["_event_match_ms"] for r in matched]
            lags = [r["_halt_lag_ms"] for r in injected]
            down = [r["_actual_down_ms"] for r in injected]
            down_errors = [r["_down_error_ms"] for r in injected]
            drain_mission = [r["_mission_ms"] for r in drained]
            fault_mission = [r["_mission_ms"] for r in faulted]
            drain_remaining = [r["_remaining_time_ms"] for r in drained]
            fault_remaining = [r["_remaining_time_ms"] for r in faulted]

            out.append({
                "target": target,
                "architecture": s(rs[0].get("architecture")),
                "event": key[0],
                "occurrence": key[1],
                "event_label": key[2],
                "attempted_runs": len(rs),
                "event_matched_runs": len(matched),
                "event_reachability": ratio(len(matched), len(rs)),
                "actual_injections": n_inj,
                "injection_rate_per_attempt": ratio(n_inj, len(rs)),
                "matched_not_triggered_runs": len(matched_not_triggered),
                "not_reached_runs": len(rs) - len(matched),
                "pre_event_failures": len(pre_fail),
                "pre_event_failure_rate_per_attempt": ratio(len(pre_fail), len(rs)),
                "no_result_runs": len(no_result),
                "drained_after_injection": n_drain,
                "faults_after_injection": n_fault,
                "mission_success_given_injection": success_p,
                "mission_success_ci95_low": succ_lo,
                "mission_success_ci95_high": succ_hi,
                "mission_failure_given_injection": fail_p,
                "mission_failure_ci95_low": fail_lo,
                "mission_failure_ci95_high": fail_hi,
                "halt_recovered_runs": len(recovered),
                "halt_recovery_rate_given_injection": ratio(len(recovered), n_inj),
                "injected_fault_reasons": ";".join(f"{k}:{v}" for k, v in sorted(fault_reasons.items())),
                "pre_event_failure_reasons": ";".join(f"{k}:{v}" for k, v in sorted(pre_reasons.items())),
                "event_time_median_ms": median(event_times),
                "event_time_q1_ms": percentile(event_times, 0.25),
                "event_time_q3_ms": percentile(event_times, 0.75),
                "halt_start_lag_median_ms": median(lags),
                "halt_start_lag_p95_ms": percentile(lags, 0.95),
                "actual_down_median_ms": median(down),
                "actual_down_p05_ms": percentile(down, 0.05),
                "actual_down_p95_ms": percentile(down, 0.95),
                "down_error_median_ms": median(down_errors),
                "down_abs_error_p95_ms": percentile([abs(x) for x in finite(down_errors)], 0.95),
                "drained_mission_median_ms": median(drain_mission),
                "fault_terminal_mission_median_ms": median(fault_mission),
                "drained_remaining_time_median_ms": median(drain_remaining),
                "drained_remaining_time_q1_ms": percentile(drain_remaining, 0.25),
                "drained_remaining_time_q3_ms": percentile(drain_remaining, 0.75),
                "fault_remaining_time_median_ms": median(fault_remaining),
            })
    return out


def target_analysis(event_summary: Sequence[dict], raw_rows: Sequence[dict], target_order: Sequence[str]) -> List[dict]:
    out = []
    by_target_raw = defaultdict(list)
    for r in raw_rows:
        if not is_baseline(r):
            by_target_raw[s(r.get("target")).upper()].append(r)

    by_target_points = defaultdict(list)
    for r in event_summary:
        by_target_points[r["target"]].append(r)

    for target in target_order:
        raw = by_target_raw.get(target, [])
        pts = by_target_points.get(target, [])
        if not raw:
            continue
        injected = [r for r in raw if r["_halt_triggered"]]
        drained = [r for r in injected if s(r.get("terminal_status")).upper() == "DRAINED"]
        faulted = [r for r in injected if s(r.get("terminal_status")).upper() == "FAULT"]
        pre = [r for r in raw if is_pre_event_failure(r)]
        n = len(injected)
        nf = len(faulted)
        ns = len(drained)
        f_lo, f_hi = wilson_interval(nf, n)
        s_lo, s_hi = wilson_interval(ns, n)

        point_fail_rates = [p["mission_failure_given_injection"] for p in pts if p["mission_failure_given_injection"] is not None]
        point_success_rates = [p["mission_success_given_injection"] for p in pts if p["mission_success_given_injection"] is not None]
        worst = max(pts, key=lambda p: (p["mission_failure_given_injection"] if p["mission_failure_given_injection"] is not None else -1.0), default=None)

        out.append({
            "target": target,
            "architecture": s(raw[0].get("architecture")),
            "fault_attempts": len(raw),
            "actual_injections": n,
            "drained_after_injection": ns,
            "faults_after_injection": nf,
            "pooled_mission_success_given_injection": ratio(ns, n),
            "pooled_success_ci95_low": s_lo,
            "pooled_success_ci95_high": s_hi,
            "pooled_mission_failure_given_injection": ratio(nf, n),
            "pooled_failure_ci95_low": f_lo,
            "pooled_failure_ci95_high": f_hi,
            "event_balanced_mean_success_probability": mean(point_success_rates),
            "event_balanced_mean_failure_probability": mean(point_fail_rates),
            "median_event_failure_probability": median(point_fail_rates),
            "pre_event_failures": len(pre),
            "pre_event_failure_rate_per_attempt": ratio(len(pre), len(raw)),
            "median_actual_down_ms": median(r["_actual_down_ms"] for r in injected),
            "p95_abs_down_error_ms": percentile([abs(r["_down_error_ms"]) for r in injected if r["_down_error_ms"] is not None], 0.95),
            "median_halt_start_lag_ms": median(r["_halt_lag_ms"] for r in injected),
            "median_drained_remaining_time_ms": median(r["_remaining_time_ms"] for r in drained),
            "worst_event": key_label((worst["event"], int(worst["occurrence"]), worst.get("event_label", ""))) if worst else "",
            "worst_event_failure_probability": worst["mission_failure_given_injection"] if worst else None,
            "worst_event_actual_injections": worst["actual_injections"] if worst else 0,
        })
    return out


def event_timing_analysis(event_summary: Sequence[dict], event_order: Sequence[Tuple[str, int, str]], target_order: Sequence[str]) -> List[dict]:
    lookup = {(r["target"], (r["event"], int(r["occurrence"]), r.get("event_label", ""))): r for r in event_summary}
    out = []
    for key in event_order:
        times = []
        row = {"event": key[0], "occurrence": key[1], "event_label": key[2]}
        for t in target_order:
            r = lookup.get((t, key))
            value = r.get("event_time_median_ms") if r else None
            row[f"{t}_event_time_median_ms"] = value
            if value is not None:
                times.append(value)
        row["cross_target_event_time_min_ms"] = min(times) if times else None
        row["cross_target_event_time_max_ms"] = max(times) if times else None
        row["cross_target_event_time_range_ms"] = (max(times) - min(times)) if len(times) >= 2 else None
        out.append(row)
    return out


def reference_comparison(event_summary: Sequence[dict], event_order: Sequence[Tuple[str, int, str]], target_order: Sequence[str], reference_target: str) -> List[dict]:
    lookup = {(r["target"], (r["event"], int(r["occurrence"]), r.get("event_label", ""))): r for r in event_summary}
    out = []
    for key in event_order:
        ref = lookup.get((reference_target, key))
        if not ref:
            continue
        for target in target_order:
            if target == reference_target:
                continue
            cur = lookup.get((target, key))
            if not cur:
                continue
            rp = ref.get("mission_failure_given_injection")
            cp = cur.get("mission_failure_given_injection")
            rr = safe_div(cp, rp)
            ref_rem = ref.get("drained_remaining_time_median_ms")
            cur_rem = cur.get("drained_remaining_time_median_ms")
            out.append({
                "event": key[0],
                "occurrence": key[1],
                "event_label": key[2],
                "reference_target": reference_target,
                "target": target,
                "reference_actual_injections": ref.get("actual_injections"),
                "target_actual_injections": cur.get("actual_injections"),
                "reference_failure_probability": rp,
                "target_failure_probability": cp,
                "failure_probability_delta_pp_vs_reference": ((cp - rp) * 100.0) if cp is not None and rp is not None else None,
                "failure_risk_ratio_vs_reference": rr,
                "reference_success_probability": ref.get("mission_success_given_injection"),
                "target_success_probability": cur.get("mission_success_given_injection"),
                "success_probability_delta_pp_vs_reference": ((cur.get("mission_success_given_injection") - ref.get("mission_success_given_injection")) * 100.0)
                    if cur.get("mission_success_given_injection") is not None and ref.get("mission_success_given_injection") is not None else None,
                "reference_drained_remaining_median_ms": ref_rem,
                "target_drained_remaining_median_ms": cur_rem,
                "drained_remaining_delta_vs_reference_ms": (cur_rem - ref_rem) if cur_rem is not None and ref_rem is not None else None,
                "reference_reachability": ref.get("event_reachability"),
                "target_reachability": cur.get("event_reachability"),
            })
    return out


def matrix_rows(event_summary: Sequence[dict], event_order: Sequence[Tuple[str, int, str]], target_order: Sequence[str], field: str) -> Tuple[List[str], List[dict]]:
    lookup = {(r["target"], (r["event"], int(r["occurrence"]), r.get("event_label", ""))): r for r in event_summary}
    fields = ["event", "occurrence", "event_label"] + list(target_order)
    out = []
    for key in event_order:
        row = {"event": key[0], "occurrence": key[1], "event_label": key[2]}
        for target in target_order:
            r = lookup.get((target, key))
            row[target] = r.get(field) if r else None
        out.append(row)
    return fields, out


def fault_reason_analysis(rows: Sequence[dict], target_order: Sequence[str]) -> List[dict]:
    counts = Counter()
    for r in rows:
        if is_baseline(r):
            category = "BASELINE"
        elif r["_halt_triggered"] and s(r.get("terminal_status")).upper() == "FAULT":
            category = "POST_INJECTION"
        elif is_pre_event_failure(r):
            category = "PRE_EVENT"
        else:
            continue
        counts[(category, s(r.get("architecture")), s(r.get("target")).upper(), s(r.get("fault_reason")))] += 1

    out = []
    for (category, arch, target, reason), count in sorted(counts.items()):
        out.append({
            "category": category,
            "architecture": arch,
            "target": target,
            "fault_reason": reason,
            "count": count,
        })
    return out


def quality_flags(baseline_summary: Sequence[dict], event_summary: Sequence[dict], target_summary: Sequence[dict]) -> List[dict]:
    flags = []

    def add(severity, scope, key, message, value=None, threshold=None):
        flags.append({
            "severity": severity,
            "scope": scope,
            "key": key,
            "value": value,
            "threshold": threshold,
            "message": message,
        })

    for b in baseline_summary:
        if b.get("baseline_failures", 0) > 0:
            add("WARN", "BASELINE", b["architecture"], "No-fault baseline contained one or more terminal FAULT outcomes.", b["baseline_failures"], 0)
        if b.get("high_variance_warning"):
            add("WARN", "BASELINE", b["architecture"], "Successful baseline mission time is highly variable/non-stationary; avoid relying on one global baseline median for causal latency attribution.", b.get("mission_stdev_ms"), 500.0)

    for e in event_summary:
        key = f"{e['target']}:{e['event']}#{e['occurrence']}"
        reach = e.get("event_reachability")
        if reach is not None and reach < 0.90:
            add("WARN", "EVENT_POINT", key, "Event reachability below 90%; many runs terminated before the intended injection point.", reach, 0.90)
        if e.get("actual_injections", 0) < 10:
            add("INFO", "EVENT_POINT", key, "Fewer than 10 actual injections; probability estimate is statistically coarse.", e.get("actual_injections"), 10)
        pre = e.get("pre_event_failure_rate_per_attempt")
        if pre is not None and pre >= 0.10:
            add("WARN", "EVENT_POINT", key, "At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution.", pre, 0.10)
        lag = e.get("halt_start_lag_median_ms")
        if lag is not None and abs(lag) > 5.0:
            add("WARN", "EVENT_POINT", key, "Median event-to-HALT alignment error exceeds 5 ms.", lag, 5.0)
        down_err = e.get("down_abs_error_p95_ms")
        if down_err is not None and down_err > 20.0:
            add("WARN", "EVENT_POINT", key, "95th percentile absolute HALT-duration error exceeds 20 ms.", down_err, 20.0)

    for t in target_summary:
        pre = t.get("pre_event_failure_rate_per_attempt")
        if pre is not None and pre >= 0.10:
            add("WARN", "TARGET", t["target"], "High pooled pre-event/background failure burden across attempts.", pre, 0.10)

    return flags


# -----------------------------------------------------------------------------
# Markdown report
# -----------------------------------------------------------------------------


def md_pct(x: Optional[float], digits=1) -> str:
    return "n/a" if x is None else f"{100.0 * x:.{digits}f}%"


def md_ms(x: Optional[float], digits=1) -> str:
    return "n/a" if x is None else f"{x:.{digits}f} ms"


def make_report(
    input_path: Path,
    rows: Sequence[dict],
    baseline_summary: Sequence[dict],
    target_summary: Sequence[dict],
    event_summary: Sequence[dict],
    timing_summary: Sequence[dict],
    flags: Sequence[dict],
    target_order: Sequence[str],
) -> str:
    baseline_rows = [r for r in rows if is_baseline(r)]
    fault_rows = [r for r in rows if not is_baseline(r)]
    injected = [r for r in fault_rows if r["_halt_triggered"]]
    pre_fail = [r for r in fault_rows if is_pre_event_failure(r)]

    lines = []
    lines.append("# Event-Aligned Fault-Injection Analysis")
    lines.append("")
    lines.append(f"Input: `{input_path.name}`")
    lines.append("")
    lines.append("## Dataset overview")
    lines.append("")
    lines.append(f"- Total CSV rows: **{len(rows)}**")
    lines.append(f"- Baseline attempts: **{len(baseline_rows)}**")
    lines.append(f"- Fault-test attempts: **{len(fault_rows)}**")
    lines.append(f"- Actual fault injections (`haltTriggered=True`): **{len(injected)}**")
    lines.append(f"- Terminal failures before the intended event/injection: **{len(pre_fail)}**")
    lines.append("")
    lines.append("Primary probability in this report is **P(DRAINED | actual injection)**. It is an empirical mission-success/fault-tolerance-coverage metric, not steady-state time availability.")
    lines.append("")

    lines.append("## Baselines")
    lines.append("")
    lines.append("| Architecture | attempts | successes | failures | median mission | stdev | range | warning |")
    lines.append("|---|---:|---:|---:|---:|---:|---:|---|")
    for b in baseline_summary:
        lines.append(
            f"| {b['architecture']} | {b['baseline_attempts']} | {b['baseline_successes']} | {b['baseline_failures']} | "
            f"{md_ms(b['mission_median_ms'])} | {md_ms(b['mission_stdev_ms'])} | {md_ms(b['mission_range_ms'])} | "
            f"{'HIGH VARIANCE' if b['high_variance_warning'] else ''} |"
        )
    lines.append("")

    lines.append("## Overall fault outcomes by target")
    lines.append("")
    lines.append("| Target | injections | DRAINED | FAULT | mission success | 95% CI | mission failure | event-balanced failure | pre-event failures |")
    lines.append("|---|---:|---:|---:|---:|---:|---:|---:|---:|")
    ts_by_target = {r["target"]: r for r in target_summary}
    for t in target_order:
        r = ts_by_target.get(t)
        if not r:
            continue
        ci = f"{md_pct(r['pooled_success_ci95_low'])}–{md_pct(r['pooled_success_ci95_high'])}"
        lines.append(
            f"| {t} | {r['actual_injections']} | {r['drained_after_injection']} | {r['faults_after_injection']} | "
            f"{md_pct(r['pooled_mission_success_given_injection'])} | {ci} | "
            f"{md_pct(r['pooled_mission_failure_given_injection'])} | {md_pct(r['event_balanced_mean_failure_probability'])} | {r['pre_event_failures']} |"
        )
    lines.append("")

    lines.append("## Worst injected-fault events per target")
    lines.append("")
    for target in target_order:
        pts = [r for r in event_summary if r["target"] == target and r["actual_injections"] > 0]
        pts.sort(key=lambda r: (r["mission_failure_given_injection"] if r["mission_failure_given_injection"] is not None else -1.0, r["actual_injections"]), reverse=True)
        lines.append(f"### {target}")
        lines.append("")
        if not pts:
            lines.append("No actual injections.")
            lines.append("")
            continue
        lines.append("| Event | injections | FAULT | failure probability | DRAINED remaining-time median | pre-event failures |")
        lines.append("|---|---:|---:|---:|---:|---:|")
        for r in pts[:5]:
            lines.append(
                f"| {r['event']} #{r['occurrence']} | {r['actual_injections']} | {r['faults_after_injection']} | "
                f"{md_pct(r['mission_failure_given_injection'])} | {md_ms(r['drained_remaining_time_median_ms'])} | {r['pre_event_failures']} |"
            )
        lines.append("")

    lines.append("## Event alignment quality")
    lines.append("")
    ranges = finite(r.get("cross_target_event_time_range_ms") for r in timing_summary)
    if ranges:
        lines.append(f"- Median cross-target difference in semantic-event timing: **{statistics.median(ranges):.2f} ms**")
        lines.append(f"- Maximum cross-target difference in semantic-event timing: **{max(ranges):.2f} ms**")
    down = finite(r["_actual_down_ms"] for r in injected)
    errors = finite(abs(r["_down_error_ms"]) if r["_down_error_ms"] is not None else None for r in injected)
    lags = finite(r["_halt_lag_ms"] for r in injected)
    if down:
        lines.append(f"- Median actual HALT duration: **{statistics.median(down):.3f} ms**")
    if errors:
        lines.append(f"- 95th percentile absolute HALT-duration error: **{percentile(errors, 0.95):.3f} ms**")
    if lags:
        lines.append(f"- Median event-to-HALT start lag: **{statistics.median(lags):.3f} ms**")
    lines.append("")

    lines.append("## Interpretation guardrails")
    lines.append("")
    lines.append("- Rows that terminate before the selected semantic event are counted as **pre-event/background failures**, not injection-induced failures.")
    lines.append("- The raw `completion_delta_vs_arch_baseline_median_ms` column is not used as the primary causal latency metric when baseline mission time is non-stationary.")
    lines.append("- `drained_remaining_time_median_ms = mission_ms - event_match_mission_ms` is an event-relative descriptive recovery metric and is more robust to pre-event timing drift.")
    lines.append("- Pooled target probabilities can be biased when later events have lower reachability. The report therefore also gives an **event-balanced mean failure probability**.")
    lines.append("- A true steady-state availability value requires an explicit production-service up/down definition and a fault-arrival/exposure model.")
    lines.append("")

    lines.append("## Data-quality flags")
    lines.append("")
    if not flags:
        lines.append("No automatic warnings were raised.")
    else:
        lines.append("| Severity | Scope | Key | Message |")
        lines.append("|---|---|---|---|")
        for f in flags[:50]:
            lines.append(f"| {f['severity']} | {f['scope']} | {f['key']} | {f['message']} |")
        if len(flags) > 50:
            lines.append(f"\nOnly the first 50 flags are shown here; see `data_quality_flags.csv` for all {len(flags)} flags.")
    lines.append("")

    lines.append("## Recommended primary tables for the report")
    lines.append("")
    lines.append("1. `target_summary.csv` — overall mission-success/failure coverage by failure domain.")
    lines.append("2. `event_target_summary.csv` — the full event × target criticality map.")
    lines.append("3. `reference_comparison.csv` — event-aligned comparison of each decomposed CD against the centralised Coordinator.")
    lines.append("4. `event_timing_alignment.csv` — evidence that the semantic events are aligned across architectures/targets.")
    lines.append("5. `baseline_summary.csv` + `data_quality_flags.csv` — controls and limitations.")
    lines.append("")

    return "\n".join(lines)


# -----------------------------------------------------------------------------
# Plots (optional)
# -----------------------------------------------------------------------------


def make_plots(
    out_dir: Path,
    rows: Sequence[dict],
    baseline_summary: Sequence[dict],
    target_summary: Sequence[dict],
    event_summary: Sequence[dict],
    event_order: Sequence[Tuple[str, int, str]],
    target_order: Sequence[str],
) -> List[str]:
    try:
        import matplotlib.pyplot as plt
    except Exception as exc:
        return [f"matplotlib unavailable; plots skipped: {exc}"]

    plot_dir = out_dir / "plots"
    plot_dir.mkdir(parents=True, exist_ok=True)
    notes = []

    # 1) Baseline sequence -- useful for spotting timing regimes/drift.
    fig, ax = plt.subplots(figsize=(10, 5))
    for arch in sorted({s(r.get('architecture')) for r in rows if is_baseline(r)}):
        rs = [r for r in rows if is_baseline(r) and s(r.get("architecture")) == arch]
        xs = list(range(1, len(rs) + 1))
        ys = [r["_mission_ms"] for r in rs]
        ax.plot(xs, ys, marker="o", label=arch)
    ax.set_xlabel("Baseline attempt")
    ax.set_ylabel("Mission time (ms)")
    ax.set_title("No-fault baseline mission time by run order")
    ax.legend()
    fig.tight_layout()
    p = plot_dir / "baseline_sequence.png"
    fig.savefig(p, dpi=160)
    plt.close(fig)

    # 2) Overall failure probability with Wilson interval.
    ts = [r for t in target_order for r in target_summary if r["target"] == t]
    fig, ax = plt.subplots(figsize=(9, 5))
    xs = list(range(len(ts)))
    ys = [100.0 * r["pooled_mission_failure_given_injection"] for r in ts]
    lo = [100.0 * (r["pooled_mission_failure_given_injection"] - r["pooled_failure_ci95_low"]) for r in ts]
    hi = [100.0 * (r["pooled_failure_ci95_high"] - r["pooled_mission_failure_given_injection"]) for r in ts]
    ax.bar(xs, ys)
    ax.errorbar(xs, ys, yerr=[lo, hi], fmt="none", capsize=4)
    ax.set_xticks(xs, [r["target"] for r in ts], rotation=20)
    ax.set_ylabel("Mission failure probability after actual injection (%)")
    ax.set_title("Overall injected-fault outcome by target")
    fig.tight_layout()
    p = plot_dir / "overall_failure_probability.png"
    fig.savefig(p, dpi=160)
    plt.close(fig)

    # 3) Failure-probability heatmap.
    lookup = {(r["target"], (r["event"], int(r["occurrence"]), r.get("event_label", ""))): r for r in event_summary}
    matrix = []
    for key in event_order:
        row = []
        for t in target_order:
            r = lookup.get((t, key))
            v = r.get("mission_failure_given_injection") if r else None
            row.append(float("nan") if v is None else 100.0 * v)
        matrix.append(row)
    fig, ax = plt.subplots(figsize=(9, max(6, 0.45 * len(event_order))))
    im = ax.imshow(matrix, aspect="auto")
    ax.set_xticks(range(len(target_order)), target_order, rotation=20)
    ax.set_yticks(range(len(event_order)), [key_label(k) for k in event_order])
    ax.set_title("Mission failure probability after actual injection (%)")
    fig.colorbar(im, ax=ax, label="Failure probability (%)")
    fig.tight_layout()
    p = plot_dir / "failure_probability_heatmap.png"
    fig.savefig(p, dpi=160)
    plt.close(fig)

    # 4) Event reachability heatmap.
    matrix = []
    for key in event_order:
        row = []
        for t in target_order:
            r = lookup.get((t, key))
            v = r.get("event_reachability") if r else None
            row.append(float("nan") if v is None else 100.0 * v)
        matrix.append(row)
    fig, ax = plt.subplots(figsize=(9, max(6, 0.45 * len(event_order))))
    im = ax.imshow(matrix, aspect="auto")
    ax.set_xticks(range(len(target_order)), target_order, rotation=20)
    ax.set_yticks(range(len(event_order)), [key_label(k) for k in event_order])
    ax.set_title("Event reachability before injection (%)")
    fig.colorbar(im, ax=ax, label="Reachability (%)")
    fig.tight_layout()
    p = plot_dir / "event_reachability_heatmap.png"
    fig.savefig(p, dpi=160)
    plt.close(fig)

    # 5) Event-relative remaining time for successful missions.
    fig, ax = plt.subplots(figsize=(12, 6))
    x = list(range(len(event_order)))
    for t in target_order:
        ys = []
        for key in event_order:
            r = lookup.get((t, key))
            v = r.get("drained_remaining_time_median_ms") if r else None
            ys.append(float("nan") if v is None else v)
        ax.plot(x, ys, marker="o", label=t)
    ax.set_xticks(x, [key_label(k) for k in event_order], rotation=60, ha="right")
    ax.set_ylabel("Median remaining time from event to DRAINED (ms)")
    ax.set_title("Event-relative recovery/completion time")
    ax.legend()
    fig.tight_layout()
    p = plot_dir / "remaining_time_by_event.png"
    fig.savefig(p, dpi=160)
    plt.close(fig)

    # 6) Semantic event timing across targets.
    fig, ax = plt.subplots(figsize=(12, 6))
    for t in target_order:
        ys = []
        for key in event_order:
            r = lookup.get((t, key))
            v = r.get("event_time_median_ms") if r else None
            ys.append(float("nan") if v is None else v)
        ax.plot(x, ys, marker="o", label=t)
    ax.set_xticks(x, [key_label(k) for k in event_order], rotation=60, ha="right")
    ax.set_ylabel("Median event time from mission start (ms)")
    ax.set_title("Semantic-event timing alignment across targets")
    ax.legend()
    fig.tight_layout()
    p = plot_dir / "event_timing_alignment.png"
    fig.savefig(p, dpi=160)
    plt.close(fig)

    # 7) Actual HALT-duration distribution.
    downs = finite(r["_actual_down_ms"] for r in rows if is_actual_injection(r))
    if downs:
        fig, ax = plt.subplots(figsize=(9, 5))
        ax.hist(downs, bins=min(40, max(10, int(math.sqrt(len(downs))))))
        ax.set_xlabel("Actual HALT duration (ms)")
        ax.set_ylabel("Count")
        ax.set_title("Injected HALT-duration distribution")
        fig.tight_layout()
        p = plot_dir / "halt_duration_distribution.png"
        fig.savefig(p, dpi=160)
        plt.close(fig)

    return notes


# -----------------------------------------------------------------------------
# Main
# -----------------------------------------------------------------------------


def main() -> None:
    ap = argparse.ArgumentParser(description="Analyse V4 event-aligned fault-injection CSV data.")
    ap.add_argument("input_csv", help="Raw event_fault_raw.csv (a .txt file is also accepted if CSV-formatted)")
    ap.add_argument("--output-dir", default="event_fault_analysis", help="Output directory")
    ap.add_argument("--reference-target", default="COORDINATOR", help="Reference target for event-aligned comparison")
    ap.add_argument("--no-plots", action="store_true", help="Skip PNG plots")
    args = ap.parse_args()

    input_path = Path(args.input_csv).resolve()
    if not input_path.is_file():
        raise SystemExit("Input file not found: " + str(input_path))

    out_dir = Path(args.output_dir).resolve()
    out_dir.mkdir(parents=True, exist_ok=True)

    rows = load_rows(input_path)
    if not rows:
        raise SystemExit("Input CSV contains no data rows.")

    # Preserve first-seen semantic event and target ordering from the raw file.
    event_seen = OrderedDict()
    target_seen = OrderedDict()
    for r in rows:
        if is_baseline(r):
            continue
        event_seen.setdefault(event_key(r), None)
        target_seen.setdefault(s(r.get("target")).upper(), None)
    event_order = list(event_seen.keys())
    target_order = list(target_seen.keys())

    baseline_summary = baseline_analysis(rows)
    event_summary = event_target_analysis(rows, event_order, target_order)
    target_summary = target_analysis(event_summary, rows, target_order)
    timing_summary = event_timing_analysis(event_summary, event_order, target_order)
    reference_summary = reference_comparison(event_summary, event_order, target_order, args.reference_target.upper())
    reasons = fault_reason_analysis(rows, target_order)
    flags = quality_flags(baseline_summary, event_summary, target_summary)

    # Primary tables.
    baseline_fields = [
        "architecture", "baseline_attempts", "baseline_successes", "baseline_failures", "baseline_success_rate",
        "mission_median_ms", "mission_mean_ms", "mission_stdev_ms", "mission_mad_ms",
        "mission_q1_ms", "mission_q3_ms", "mission_iqr_ms", "mission_min_ms", "mission_max_ms",
        "mission_range_ms", "largest_sorted_gap_ms", "high_variance_warning", "failure_reasons"
    ]
    target_fields = [
        "target", "architecture", "fault_attempts", "actual_injections", "drained_after_injection", "faults_after_injection",
        "pooled_mission_success_given_injection", "pooled_success_ci95_low", "pooled_success_ci95_high",
        "pooled_mission_failure_given_injection", "pooled_failure_ci95_low", "pooled_failure_ci95_high",
        "event_balanced_mean_success_probability", "event_balanced_mean_failure_probability", "median_event_failure_probability",
        "pre_event_failures", "pre_event_failure_rate_per_attempt", "median_actual_down_ms", "p95_abs_down_error_ms",
        "median_halt_start_lag_ms", "median_drained_remaining_time_ms", "worst_event", "worst_event_failure_probability",
        "worst_event_actual_injections"
    ]
    event_fields = [
        "target", "architecture", "event", "occurrence", "event_label", "attempted_runs", "event_matched_runs",
        "event_reachability", "actual_injections", "injection_rate_per_attempt", "matched_not_triggered_runs", "not_reached_runs",
        "pre_event_failures", "pre_event_failure_rate_per_attempt", "no_result_runs", "drained_after_injection", "faults_after_injection",
        "mission_success_given_injection", "mission_success_ci95_low", "mission_success_ci95_high",
        "mission_failure_given_injection", "mission_failure_ci95_low", "mission_failure_ci95_high",
        "halt_recovered_runs", "halt_recovery_rate_given_injection", "injected_fault_reasons", "pre_event_failure_reasons",
        "event_time_median_ms", "event_time_q1_ms", "event_time_q3_ms", "halt_start_lag_median_ms", "halt_start_lag_p95_ms",
        "actual_down_median_ms", "actual_down_p05_ms", "actual_down_p95_ms", "down_error_median_ms", "down_abs_error_p95_ms",
        "drained_mission_median_ms", "fault_terminal_mission_median_ms", "drained_remaining_time_median_ms",
        "drained_remaining_time_q1_ms", "drained_remaining_time_q3_ms", "fault_remaining_time_median_ms"
    ]
    timing_fields = ["event", "occurrence", "event_label"] + [f"{t}_event_time_median_ms" for t in target_order] + [
        "cross_target_event_time_min_ms", "cross_target_event_time_max_ms", "cross_target_event_time_range_ms"
    ]
    reference_fields = [
        "event", "occurrence", "event_label", "reference_target", "target",
        "reference_actual_injections", "target_actual_injections", "reference_failure_probability", "target_failure_probability",
        "failure_probability_delta_pp_vs_reference", "failure_risk_ratio_vs_reference", "reference_success_probability",
        "target_success_probability", "success_probability_delta_pp_vs_reference", "reference_drained_remaining_median_ms",
        "target_drained_remaining_median_ms", "drained_remaining_delta_vs_reference_ms", "reference_reachability", "target_reachability"
    ]
    reason_fields = ["category", "architecture", "target", "fault_reason", "count"]
    flag_fields = ["severity", "scope", "key", "value", "threshold", "message"]

    write_csv(out_dir / "baseline_summary.csv", baseline_fields, baseline_summary)
    write_csv(out_dir / "target_summary.csv", target_fields, target_summary)
    write_csv(out_dir / "event_target_summary.csv", event_fields, event_summary)
    write_csv(out_dir / "event_timing_alignment.csv", timing_fields, timing_summary)
    write_csv(out_dir / "reference_comparison.csv", reference_fields, reference_summary)
    write_csv(out_dir / "fault_reason_summary.csv", reason_fields, reasons)
    write_csv(out_dir / "data_quality_flags.csv", flag_fields, flags)

    # Matrix tables.
    for filename, field in [
        ("event_failure_matrix.csv", "mission_failure_given_injection"),
        ("event_success_matrix.csv", "mission_success_given_injection"),
        ("event_reachability_matrix.csv", "event_reachability"),
        ("event_remaining_time_matrix.csv", "drained_remaining_time_median_ms"),
    ]:
        fields, data = matrix_rows(event_summary, event_order, target_order, field)
        write_csv(out_dir / filename, fields, data)

    # Clean raw subsets.
    original_header = [k for k in rows[0].keys() if not k.startswith("_")]
    actual_rows = [r for r in rows if is_actual_injection(r)]
    pre_rows = [r for r in rows if is_pre_event_failure(r)]
    baseline_rows = [r for r in rows if is_baseline(r)]
    copy_rows_as_csv(out_dir / "actual_injections.csv", original_header, actual_rows)
    copy_rows_as_csv(out_dir / "pre_event_failures.csv", original_header, pre_rows)
    copy_rows_as_csv(out_dir / "baseline_rows.csv", original_header, baseline_rows)

    # Markdown report.
    report = make_report(
        input_path, rows, baseline_summary, target_summary, event_summary,
        timing_summary, flags, target_order
    )
    (out_dir / "report.md").write_text(report, encoding="utf-8")

    plot_notes = []
    if not args.no_plots:
        plot_notes = make_plots(out_dir, rows, baseline_summary, target_summary, event_summary, event_order, target_order)

    print("Analysis complete")
    print("Input:", input_path)
    print("Output:", out_dir)
    print("Rows:", len(rows))
    print("Targets:", ", ".join(target_order))
    print("Semantic event points:", len(event_order))
    print("Actual injections:", len(actual_rows))
    print("Pre-event terminal failures:", len(pre_rows))
    print("Primary report:", out_dir / "report.md")
    if plot_notes:
        for note in plot_notes:
            print(note)


if __name__ == "__main__":
    main()
