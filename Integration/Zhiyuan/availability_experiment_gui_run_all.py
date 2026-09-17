"""
Availability Experiment GUI — full three-version campaign

One-button full campaign:
    1. Baseline 1CD / COORDINATOR
    2. Improved 3CD / BATCH
    3. Improved 3CD / PRODUCTION
    4. Improved 3CD / SAFETY
    5. Improved 3CD / COMBINED (ALL3 simultaneous)
    6. Hardened 1CD / COORDINATOR

COMBINED (ALL3) is a true single-mission simultaneous outage:
BATCH + PRODUCTION + SAFETY share one HALT window. With fault-reps=20,
this feature executes 20 missions, not 60.

Place this file beside:
    run_event_fault_sweep.py

The runner and GlobalExperiment.java must support target=ALL3.
"""

from __future__ import annotations

import csv
import os
from pathlib import Path
import queue
import re
import signal
import statistics
import subprocess
import sys
import threading
import time
import tkinter as tk
from tkinter import ttk, messagebox

ROOT = Path(__file__).resolve().parent
RUNNER = ROOT / "run_event_fault_sweep.py"

BASELINE_XML = "sysj/01_Baseline_1CD/coordinator.xml"
IMPROVED_XML = "sysj/02_Improved_3CD/IntegratedCoordinatorAcceptance_3CD.xml"
HARDENED_XML = "sysj/03_Hardened_1CD/HardenedCoordinatorCD.xml"

COMBINED_LABEL = "COMBINED (ALL3)"
COMBINED_RUNNER_TARGET = "ALL3"
IMPROVED_TARGETS = ["BATCH", "PRODUCTION", "SAFETY", COMBINED_LABEL]

MODES = {
    "Baseline 1CD": {
        "central_xml": BASELINE_XML,
        "decomposed_xml": IMPROVED_XML,
        "targets": ["COORDINATOR"],
        "default_target": "COORDINATOR",
    },
    "Improved 3CD": {
        "central_xml": BASELINE_XML,
        "decomposed_xml": IMPROVED_XML,
        "targets": IMPROVED_TARGETS,
        "default_target": COMBINED_LABEL,
    },
    "Hardened 1CD": {
        "central_xml": HARDENED_XML,
        "decomposed_xml": IMPROVED_XML,
        "targets": ["COORDINATOR"],
        "default_target": "COORDINATOR",
    },
}

MODE_SLUG = {
    "Baseline 1CD": "baseline_1cd",
    "Improved 3CD": "improved_3cd",
    "Hardened 1CD": "hardened_1cd",
}

MODE_SHORT = {
    "Baseline 1CD": "Baseline",
    "Improved 3CD": "Improved",
    "Hardened 1CD": "Hardened",
}

RUN_LINE_RE = re.compile(r"^\[(?P<target>[A-Z]+)\]\s+(?P<event>.+?)\s+rep=(?P<rep>\d+)/(?P<total>\d+)")


def truthy(value):
    return str(value).strip().lower() == "true"


def new_metrics():
    return {
        "attempted": 0,
        "injections": 0,
        "drained": 0,
        "faulted": 0,
        "other": 0,
        "event_not_reached": 0,
        "last_status": "—",
        "last_reason": "—",
        "success_rate": None,
        "median_down_ms": None,
        "downs": [],
    }


def finalize_metrics(m):
    downs = [x for x in m.pop("downs", []) if x is not None]
    if m["injections"]:
        m["success_rate"] = m["drained"] / m["injections"]
    else:
        m["success_rate"] = None
    m["median_down_ms"] = statistics.median(downs) if downs else None
    return m


def accumulate_row(m, row):
    terminal = (row.get("terminal_status") or "").strip().upper()
    status = (row.get("status") or "").strip().upper()
    reason = (row.get("fault_reason") or "").strip() or "NONE"

    m["attempted"] += 1
    m["last_status"] = terminal or status or "—"
    m["last_reason"] = reason

    if status == "EVENT_NOT_REACHED":
        m["event_not_reached"] += 1

    if truthy(row.get("halt_triggered")):
        m["injections"] += 1
        if terminal == "DRAINED":
            m["drained"] += 1
        elif terminal == "FAULT":
            m["faulted"] += 1
        else:
            m["other"] += 1
        try:
            down = float(row.get("actual_down_ms") or "")
            if down >= 0:
                m["downs"].append(down)
        except ValueError:
            pass


def read_live_breakdown(raw_csv: Path):
    bundle = {
        "baseline_attempts": 0,
        "baseline_drained": 0,
        "all": new_metrics(),
        "targets": {t: new_metrics() for t in ("COORDINATOR", "BATCH", "PRODUCTION", "SAFETY", "ALL3")},
    }

    if not raw_csv.is_file() or raw_csv.stat().st_size == 0:
        bundle["all"] = finalize_metrics(bundle["all"])
        for k in list(bundle["targets"]):
            bundle["targets"][k] = finalize_metrics(bundle["targets"][k])
        return bundle

    try:
        with raw_csv.open("r", newline="", encoding="utf-8-sig") as f:
            for row in csv.DictReader(f):
                target = (row.get("target") or "").strip().upper()
                terminal = (row.get("terminal_status") or "").strip().upper()

                if target == "NONE":
                    bundle["baseline_attempts"] += 1
                    if terminal == "DRAINED":
                        bundle["baseline_drained"] += 1
                    continue

                if target not in bundle["targets"]:
                    continue

                accumulate_row(bundle["all"], row)
                accumulate_row(bundle["targets"][target], row)

    except (OSError, csv.Error):
        pass

    bundle["all"] = finalize_metrics(bundle["all"])
    for k in list(bundle["targets"]):
        bundle["targets"][k] = finalize_metrics(bundle["targets"][k])
    return bundle


class AvailabilityGUI(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("EABS Availability Experiment")
        self.minsize(1120, 680)

        self.proc = None
        self.reader_thread = None
        self.log_queue = queue.Queue()
        self.current_raw = None
        self.current_mode = None
        self.current_target = None
        self.stop_requested = False
        self.sequence = []
        self.last_bundle = None
        self.full_campaign = False
        self.full_campaign_total = 0
        self.full_campaign_completed = 0

        self.mode_var = tk.StringVar(value="Baseline 1CD")
        self.target_var = tk.StringVar(value="COORDINATOR")
        self.event_plan_var = tk.StringVar(value="event_plan_trace.csv")
        self.fault_reps_var = tk.StringVar(value="20")
        self.baseline_runs_var = tk.StringVar(value="10")
        self.max_baseline_var = tk.StringVar(value="20")
        self.duration_var = tk.StringVar(value="500")
        self.timeout_var = tk.StringVar(value="60")
        self.fast_var = tk.BooleanVar(value=True)
        self.debug_var = tk.BooleanVar(value=True)

        self.status_var = tk.StringVar(value="Idle")
        self.current_run_var = tk.StringVar(value="—")
        self.rate_var = tk.StringVar(value="—")
        self.injected_var = tk.StringVar(value="0")
        self.drained_var = tk.StringVar(value="0")
        self.faulted_var = tk.StringVar(value="0")
        self.not_reached_var = tk.StringVar(value="0")
        self.down_var = tk.StringVar(value="—")
        self.last_result_var = tk.StringVar(value="—")
        self.baseline_var = tk.StringVar(value="0 / 0")

        self._build_ui()
        self._mode_changed()
        self.after(100, self._pump_log_queue)
        self.after(400, self._poll_live_csv)
        self.after(250, self._poll_process)
        self.protocol("WM_DELETE_WINDOW", self._on_close)

    def _build_ui(self):
        self.columnconfigure(0, weight=3)
        self.columnconfigure(1, weight=2)
        self.rowconfigure(0, weight=1)

        left = ttk.Frame(self, padding=12)
        right = ttk.Frame(self, padding=(0, 12, 12, 12))
        left.grid(row=0, column=0, sticky="nsew")
        right.grid(row=0, column=1, sticky="nsew")
        left.columnconfigure(0, weight=1)
        left.rowconfigure(2, weight=1)
        right.columnconfigure(0, weight=1)
        right.rowconfigure(1, weight=1)

        config = ttk.LabelFrame(left, text="Experiment", padding=10)
        config.grid(row=0, column=0, sticky="ew")
        for col in (1, 3):
            config.columnconfigure(col, weight=1)

        ttk.Label(config, text="Mode").grid(row=0, column=0, sticky="w", padx=(0, 6), pady=4)
        mode_box = ttk.Combobox(config, textvariable=self.mode_var, values=list(MODES), state="readonly")
        mode_box.grid(row=0, column=1, sticky="ew", pady=4)
        mode_box.bind("<<ComboboxSelected>>", lambda _e: self._mode_changed())

        ttk.Label(config, text="Fault target").grid(row=0, column=2, sticky="w", padx=(12, 6), pady=4)
        self.target_box = ttk.Combobox(config, textvariable=self.target_var, state="readonly")
        self.target_box.grid(row=0, column=3, sticky="ew", pady=4)

        ttk.Label(config, text="Event plan").grid(row=1, column=0, sticky="w", padx=(0, 6), pady=4)
        ttk.Entry(config, textvariable=self.event_plan_var).grid(row=1, column=1, columnspan=3, sticky="ew", pady=4)

        ttk.Label(config, text="Fault repetitions").grid(row=2, column=0, sticky="w", padx=(0, 6), pady=4)
        ttk.Entry(config, textvariable=self.fault_reps_var).grid(row=2, column=1, sticky="ew", pady=4)

        ttk.Label(config, text="Baseline successes").grid(row=2, column=2, sticky="w", padx=(12, 6), pady=4)
        ttk.Entry(config, textvariable=self.baseline_runs_var).grid(row=2, column=3, sticky="ew", pady=4)

        ttk.Label(config, text="Max baseline attempts").grid(row=3, column=0, sticky="w", padx=(0, 6), pady=4)
        ttk.Entry(config, textvariable=self.max_baseline_var).grid(row=3, column=1, sticky="ew", pady=4)

        ttk.Label(config, text="Fault duration (ms)").grid(row=3, column=2, sticky="w", padx=(12, 6), pady=4)
        ttk.Entry(config, textvariable=self.duration_var).grid(row=3, column=3, sticky="ew", pady=4)

        ttk.Label(config, text="Run timeout (s)").grid(row=4, column=0, sticky="w", padx=(0, 6), pady=4)
        ttk.Entry(config, textvariable=self.timeout_var).grid(row=4, column=1, sticky="ew", pady=4)

        options = ttk.Frame(config)
        options.grid(row=4, column=2, columnspan=2, sticky="w", padx=(12, 0))
        ttk.Checkbutton(options, text="Fast B2 harness", variable=self.fast_var).pack(side="left")
        ttk.Checkbutton(options, text="Save debug logs", variable=self.debug_var).pack(side="left", padx=(10, 0))

        actions = ttk.Frame(left)
        actions.grid(row=1, column=0, sticky="ew", pady=10)
        self.run_btn = ttk.Button(actions, text="Run selected", command=self.run_selected)
        self.run_btn.pack(side="left")
        self.run_all_btn = ttk.Button(actions, text="RUN ALL — 3 Versions / All Features", command=self.run_all)
        self.run_all_btn.pack(side="left", padx=(8, 0))
        self.stop_btn = ttk.Button(actions, text="Stop", command=self.stop_run, state="disabled")
        self.stop_btn.pack(side="left", padx=(8, 0))
        ttk.Label(actions, textvariable=self.status_var).pack(side="left", padx=(14, 0))

        log_frame = ttk.LabelFrame(left, text="Live runner output", padding=6)
        log_frame.grid(row=2, column=0, sticky="nsew")
        log_frame.rowconfigure(0, weight=1)
        log_frame.columnconfigure(0, weight=1)

        self.log = tk.Text(log_frame, wrap="word", height=18, font=("Consolas", 9))
        self.log.grid(row=0, column=0, sticky="nsew")
        scrollbar = ttk.Scrollbar(log_frame, orient="vertical", command=self.log.yview)
        scrollbar.grid(row=0, column=1, sticky="ns")
        self.log.configure(yscrollcommand=scrollbar.set)

        live = ttk.LabelFrame(right, text="Live mission success", padding=12)
        live.grid(row=0, column=0, sticky="ew")
        live.columnconfigure(0, weight=1)

        ttk.Label(live, textvariable=self.rate_var, font=("Segoe UI", 30, "bold")).grid(row=0, column=0, sticky="w")
        ttk.Label(live, text="fault-conditioned mission success").grid(row=1, column=0, sticky="w")

        self.rate_bar = ttk.Progressbar(live, maximum=100, value=0)
        self.rate_bar.grid(row=2, column=0, sticky="ew", pady=(10, 12))

        metric_grid = ttk.Frame(live)
        metric_grid.grid(row=3, column=0, sticky="ew")
        metric_grid.columnconfigure(1, weight=1)
        self._metric(metric_grid, 0, "Current run", self.current_run_var)
        self._metric(metric_grid, 1, "Baseline", self.baseline_var)
        self._metric(metric_grid, 2, "Actual injections", self.injected_var)
        self._metric(metric_grid, 3, "DRAINED", self.drained_var)
        self._metric(metric_grid, 4, "FAULT", self.faulted_var)
        self._metric(metric_grid, 5, "Event not reached", self.not_reached_var)
        self._metric(metric_grid, 6, "Median downtime", self.down_var)
        self._metric(metric_grid, 7, "Last result", self.last_result_var)

        compare = ttk.LabelFrame(right, text="Session comparison", padding=8)
        compare.grid(row=1, column=0, sticky="nsew", pady=(12, 0))

        columns = ("mode", "target", "injected", "drained", "fault", "rate")
        self.table = ttk.Treeview(compare, columns=columns, show="headings", height=10)
        heads = {
            "mode": "Mode",
            "target": "Target",
            "injected": "Injected",
            "drained": "DRAINED",
            "fault": "FAULT",
            "rate": "Success",
        }
        widths = {"mode": 110, "target": 160, "injected": 70, "drained": 70, "fault": 60, "rate": 75}
        for col in columns:
            self.table.heading(col, text=heads[col])
            self.table.column(col, width=widths[col], anchor="center" if col != "rate" else "e")
        self.table.pack(fill="both", expand=True)

        note = (
            "Success rate = DRAINED / actual injections. "
            "Runs where the semantic event was not reached are not counted as injections. "
            "RUN ALL executes Baseline, all four Improved features, and Hardened. COMBINED / ALL3 is one simultaneous three-CD outage per mission."
        )
        ttk.Label(right, text=note, wraplength=420).grid(row=2, column=0, sticky="ew", pady=(8, 0))

    @staticmethod
    def _metric(parent, row, label, variable):
        ttk.Label(parent, text=label).grid(row=row, column=0, sticky="w", pady=2)
        ttk.Label(parent, textvariable=variable).grid(row=row, column=1, sticky="e", padx=(12, 0), pady=2)

    def _mode_changed(self):
        cfg = MODES[self.mode_var.get()]
        self.target_box.configure(values=cfg["targets"], state="readonly")
        self.target_var.set(cfg["default_target"])

    def _validate_settings(self):
        if not RUNNER.is_file():
            raise ValueError(f"Runner not found: {RUNNER}")
        event_plan = (ROOT / self.event_plan_var.get()).resolve()
        if not event_plan.is_file():
            raise ValueError(f"Event plan not found: {event_plan}")

        numeric = {
            "Fault repetitions": self.fault_reps_var.get(),
            "Baseline successes": self.baseline_runs_var.get(),
            "Max baseline attempts": self.max_baseline_var.get(),
            "Fault duration": self.duration_var.get(),
        }
        parsed = {}
        for name, value in numeric.items():
            try:
                parsed[name] = int(value)
            except ValueError:
                raise ValueError(f"{name} must be an integer.")
            if parsed[name] < 1:
                raise ValueError(f"{name} must be >= 1.")

        try:
            timeout = float(self.timeout_var.get())
        except ValueError:
            raise ValueError("Run timeout must be numeric.")
        if timeout <= 0:
            raise ValueError("Run timeout must be > 0.")
        if parsed["Max baseline attempts"] < parsed["Baseline successes"]:
            raise ValueError("Max baseline attempts must be >= baseline successes.")
        return event_plan, timeout

    def _runner_target(self, mode, target):
        if mode == "Improved 3CD" and target == COMBINED_LABEL:
            return COMBINED_RUNNER_TARGET
        return target

    def run_selected(self):
        if self.proc is not None:
            return
        try:
            self._validate_settings()
        except ValueError as e:
            messagebox.showerror("Invalid settings", str(e))
            return
        self.sequence = []
        self.full_campaign = False
        self.full_campaign_total = 0
        self.full_campaign_completed = 0
        self._start_mode(self.mode_var.get(), self.target_var.get())

    def run_all(self):
        """Run every experimental feature across all three architecture versions."""
        if self.proc is not None:
            return

        try:
            self._validate_settings()
        except ValueError as e:
            messagebox.showerror("Invalid settings", str(e))
            return

        # Start a clean comparison for this full campaign.
        for iid in self.table.get_children():
            self.table.delete(iid)

        campaign = [
            ("Baseline 1CD", "COORDINATOR"),
            ("Improved 3CD", "BATCH"),
            ("Improved 3CD", "PRODUCTION"),
            ("Improved 3CD", "SAFETY"),
            ("Improved 3CD", COMBINED_LABEL),
            ("Hardened 1CD", "COORDINATOR"),
        ]

        self.full_campaign = True
        self.full_campaign_total = len(campaign)
        self.full_campaign_completed = 0

        first_mode, first_target = campaign[0]
        self.sequence = campaign[1:]

        self._append_log("\n" + "#" * 72 + "\n")
        self._append_log("FULL CAMPAIGN START\n")
        self._append_log(
            "Sequence: Baseline → Improved BATCH → Improved PRODUCTION → "
            "Improved SAFETY → Improved ALL3 → Hardened\n"
        )
        self._append_log("#" * 72 + "\n")

        self._start_mode(first_mode, first_target)

    def _start_mode(self, mode, target):
        event_plan, timeout = self._validate_settings()
        cfg = MODES[mode]
        runner_target = self._runner_target(mode, target)

        timestamp = time.strftime("%Y%m%d_%H%M%S")
        target_slug = target.lower().replace(",", "_").replace(" ", "_")
        out_dir = ROOT / "gui_experiments" / MODE_SLUG[mode] / f"{timestamp}_{target_slug}"
        out_dir.mkdir(parents=True, exist_ok=True)

        raw = out_dir / "raw.csv"
        summary = out_dir / "summary.csv"
        baselines = out_dir / "baselines.csv"
        runs = out_dir / "runs"

        cmd = [
            sys.executable, "-u", str(RUNNER),
            "--project-root", str(ROOT),
            "--central-xml", cfg["central_xml"],
            "--decomposed-xml", cfg["decomposed_xml"],
            "--targets", runner_target,
            "--event-plan", str(event_plan),
            "--fault-reps", self.fault_reps_var.get(),
            "--duration-ms", self.duration_var.get(),
            "--after-event-ms", "0",
            "--baseline-runs", self.baseline_runs_var.get(),
            "--max-baseline-attempts", self.max_baseline_var.get(),
            "--timeout", str(timeout),
            "--raw-output", str(raw),
            "--summary-output", str(summary),
            "--baseline-output", str(baselines),
            "--results-dir", str(runs),
        ]
        if not self.fast_var.get():
            cmd.append("--full-acceptance")
        if self.debug_var.get():
            cmd.append("--debug-output")

        self.current_raw = raw
        self.current_mode = mode
        self.current_target = target
        self.stop_requested = False
        self.last_bundle = None
        self._reset_live_metrics()
        if self.full_campaign:
            current_index = self.full_campaign_completed + 1
            self.status_var.set(
                f"Full campaign {current_index}/{self.full_campaign_total} · {mode} · {target}"
            )
        else:
            self.status_var.set(f"Running {mode} · {target}")
        self.current_run_var.set("Starting…")
        self.run_btn.configure(state="disabled")
        self.run_all_btn.configure(state="disabled")
        self.stop_btn.configure(state="normal")
        self._append_log("\n" + "=" * 72 + "\n")
        self._append_log(f"{mode} | target={target} | runner-target={runner_target}\n")
        self._append_log(" ".join(f'\"{x}\"' if " " in x else x for x in cmd) + "\n\n")

        popen_kwargs = dict(cwd=str(ROOT), stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True, bufsize=1)
        if os.name == "nt":
            popen_kwargs["creationflags"] = subprocess.CREATE_NEW_PROCESS_GROUP
        else:
            popen_kwargs["start_new_session"] = True

        try:
            self.proc = subprocess.Popen(cmd, **popen_kwargs)
        except OSError as e:
            self.proc = None
            self.status_var.set("Launch failed")
            self._set_idle_buttons()
            messagebox.showerror("Launch failed", str(e))
            return

        self.reader_thread = threading.Thread(target=self._reader, daemon=True)
        self.reader_thread.start()

    def _reader(self):
        proc = self.proc
        if proc is None or proc.stdout is None:
            return
        try:
            for line in proc.stdout:
                self.log_queue.put(line)
        finally:
            try:
                proc.stdout.close()
            except Exception:
                pass

    def _pump_log_queue(self):
        try:
            while True:
                line = self.log_queue.get_nowait()
                self._append_log(line)
                m = RUN_LINE_RE.match(line.strip())
                if m:
                    self.current_run_var.set(f'{m.group("target")} · {m.group("event")} · {m.group("rep")}/{m.group("total")}')
        except queue.Empty:
            pass
        self.after(100, self._pump_log_queue)

    def _append_log(self, text):
        self.log.insert("end", text)
        self.log.see("end")

    def _live_metrics_to_show(self, bundle):
        if not bundle:
            return None
        if self.current_mode == "Improved 3CD" and self.current_target == COMBINED_LABEL:
            return bundle["targets"]["ALL3"]
        if self.current_mode == "Improved 3CD" and self.current_target in bundle["targets"]:
            return bundle["targets"][self.current_target]
        if self.current_target in bundle["targets"]:
            return bundle["targets"][self.current_target]
        return bundle["all"]

    def _poll_live_csv(self):
        if self.current_raw is not None:
            bundle = read_live_breakdown(self.current_raw)
            self.last_bundle = bundle
            self._show_metrics(bundle)
        self.after(400, self._poll_live_csv)

    def _show_metrics(self, bundle):
        m = self._live_metrics_to_show(bundle)
        if m is None:
            return
        rate = m["success_rate"]
        if rate is None:
            self.rate_var.set("—")
            self.rate_bar["value"] = 0
        else:
            pct = rate * 100.0
            self.rate_var.set(f"{pct:.1f}%")
            self.rate_bar["value"] = pct

        self.injected_var.set(str(m["injections"]))
        self.drained_var.set(str(m["drained"]))
        self.faulted_var.set(str(m["faulted"]))
        self.not_reached_var.set(str(m["event_not_reached"]))
        self.baseline_var.set(f'{bundle["baseline_drained"]} / {bundle["baseline_attempts"]}')
        self.down_var.set("—" if m["median_down_ms"] is None else f'{m["median_down_ms"]:.2f} ms')

        result = m["last_status"]
        if m["last_reason"] not in ("", "NONE", "—"):
            result += f' · {m["last_reason"]}'
        self.last_result_var.set(result)

    def _reset_live_metrics(self):
        self.rate_var.set("—")
        self.rate_bar["value"] = 0
        self.injected_var.set("0")
        self.drained_var.set("0")
        self.faulted_var.set("0")
        self.not_reached_var.set("0")
        self.down_var.set("—")
        self.last_result_var.set("—")
        self.baseline_var.set("0 / 0")

    def _poll_process(self):
        if self.proc is not None:
            rc = self.proc.poll()
            if rc is not None:
                if self.current_raw is not None:
                    self.last_bundle = read_live_breakdown(self.current_raw)
                    self._show_metrics(self.last_bundle)

                finished_mode = self.current_mode
                finished_target = self.current_target
                stopped = self.stop_requested
                self.proc = None

                if stopped:
                    self.status_var.set("Stopped")
                    self.sequence = []
                    self._set_idle_buttons()
                else:
                    self._update_comparison(finished_mode, finished_target, self.last_bundle)

                    if self.full_campaign:
                        self.full_campaign_completed += 1

                    if rc == 0 and self.sequence:
                        if self.full_campaign:
                            self.status_var.set(
                                f"Full campaign {self.full_campaign_completed}/"
                                f"{self.full_campaign_total} complete"
                            )
                        else:
                            self.status_var.set("Complete")

                        next_mode, next_target = self.sequence.pop(0)
                        self.after(500, lambda: self._start_mode(next_mode, next_target))
                    else:
                        self.sequence = []

                        if self.full_campaign and rc == 0:
                            self.status_var.set(
                                f"Full campaign complete · "
                                f"{self.full_campaign_completed}/{self.full_campaign_total}"
                            )
                            self._append_log("\n" + "#" * 72 + "\n")
                            self._append_log("FULL CAMPAIGN COMPLETE\n")
                            self._append_log("#" * 72 + "\n")
                        else:
                            self.status_var.set("Complete" if rc == 0 else f"Exited {rc}")

                        self.full_campaign = False
                        self._set_idle_buttons()
        self.after(250, self._poll_process)

    def _comparison_values(self, mode_label, target_label, metrics):
        rate = "—" if metrics.get("success_rate") is None else f'{100 * metrics["success_rate"]:.1f}%'
        return (mode_label, target_label, metrics.get("injections", 0), metrics.get("drained", 0), metrics.get("faulted", 0), rate)

    def _upsert_row(self, iid, values):
        if self.table.exists(iid):
            self.table.item(iid, values=values)
        else:
            self.table.insert("", "end", iid=iid, values=values)

    def _update_comparison(self, mode, target, bundle):
        if not mode or not bundle:
            return
        mode_label = MODE_SHORT[mode]
        mode_slug = MODE_SLUG[mode]

        if mode == "Improved 3CD" and target == COMBINED_LABEL:
            metrics = bundle["targets"]["ALL3"]
            values = self._comparison_values(
                mode_label,
                "COMBINED / ALL3",
                metrics
            )
            self._upsert_row(f"{mode_slug}:ALL3", values)
            return

        target_key = target
        metrics = bundle["targets"].get(target_key, bundle["all"])
        values = self._comparison_values(mode_label, target_key, metrics)
        self._upsert_row(f"{mode_slug}:{target_key}", values)

    def stop_run(self):
        if self.proc is None:
            return
        self.stop_requested = True
        self.sequence = []
        self.full_campaign = False
        pid = self.proc.pid
        self.status_var.set("Stopping…")
        try:
            if os.name == "nt":
                subprocess.run(["taskkill", "/PID", str(pid), "/T", "/F"], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, timeout=5)
            else:
                os.killpg(os.getpgid(pid), signal.SIGTERM)
        except Exception:
            try:
                self.proc.terminate()
            except Exception:
                pass

    def _set_idle_buttons(self):
        self.run_btn.configure(state="normal")
        self.run_all_btn.configure(state="normal")
        self.stop_btn.configure(state="disabled")

    def _on_close(self):
        if self.proc is not None:
            if not messagebox.askyesno("Experiment running", "Stop the experiment and close?"):
                return
            self.stop_run()
        self.destroy()


def main():
    AvailabilityGUI().mainloop()


if __name__ == "__main__":
    main()
