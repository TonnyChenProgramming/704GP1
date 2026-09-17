# Event-Aligned Fault-Injection Analysis

Input: `event_fault_raw.csv`

## Dataset overview

- Total CSV rows: **1141**
- Baseline attempts: **21**
- Fault-test attempts: **1120**
- Actual fault injections (`haltTriggered=True`): **1009**
- Terminal failures before the intended event/injection: **111**

Primary probability in this report is **P(DRAINED | actual injection)**. It is an empirical mission-success/fault-tolerance-coverage metric, not steady-state time availability.

## Baselines

| Architecture | attempts | successes | failures | median mission | stdev | range | warning |
|---|---:|---:|---:|---:|---:|---:|---|
| CENTRALISED | 11 | 10 | 1 | 13828.4 ms | 1872.7 ms | 4140.9 ms | HIGH VARIANCE |
| DECOMPOSED_3CD | 10 | 10 | 0 | 14346.0 ms | 201.2 ms | 688.9 ms |  |

## Overall fault outcomes by target

| Target | injections | DRAINED | FAULT | mission success | 95% CI | mission failure | event-balanced failure | pre-event failures |
|---|---:|---:|---:|---:|---:|---:|---:|---:|
| COORDINATOR | 248 | 233 | 15 | 94.0% | 90.3%–96.3% | 6.0% | 6.1% | 32 |
| BATCH | 255 | 240 | 15 | 94.1% | 90.5%–96.4% | 5.9% | 5.7% | 25 |
| PRODUCTION | 253 | 223 | 30 | 88.1% | 83.6%–91.6% | 11.9% | 11.1% | 27 |
| SAFETY | 253 | 239 | 14 | 94.5% | 90.9%–96.7% | 5.5% | 5.2% | 27 |

## Worst injected-fault events per target

### COORDINATOR

| Event | injections | FAULT | failure probability | DRAINED remaining-time median | pre-event failures |
|---|---:|---:|---:|---:|---:|
| FILLER_DONE #1 | 16 | 4 | 25.0% | 7079.5 ms | 4 |
| LID_DONE #1 | 16 | 4 | 25.0% | 4970.1 ms | 4 |
| CONVEYOR_DONE #1 | 20 | 3 | 15.0% | 8616.0 ms | 0 |
| CONVEYOR_DONE #2 | 19 | 2 | 10.5% | 6056.7 ms | 1 |
| FILLER_DONE #2 | 19 | 1 | 5.3% | 4517.0 ms | 1 |

### BATCH

| Event | injections | FAULT | failure probability | DRAINED remaining-time median | pre-event failures |
|---|---:|---:|---:|---:|---:|
| LID_DONE #1 | 18 | 3 | 16.7% | 4979.1 ms | 2 |
| LOADER_DONE #2 | 18 | 3 | 16.7% | 6598.9 ms | 2 |
| CONVEYOR_DONE #1 | 20 | 3 | 15.0% | 8127.9 ms | 0 |
| TABLE_ALIGNED #1 | 19 | 2 | 10.5% | 7610.1 ms | 1 |
| CAPPER_DONE #1 | 18 | 1 | 5.6% | 3424.7 ms | 2 |

### PRODUCTION

| Event | injections | FAULT | failure probability | DRAINED remaining-time median | pre-event failures |
|---|---:|---:|---:|---:|---:|
| CONVEYOR_DONE #1 | 20 | 7 | 35.0% | 8622.5 ms | 0 |
| LOADER_DONE #1 | 20 | 6 | 30.0% | 9639.4 ms | 0 |
| BATCH_ACCEPTED #1 | 20 | 5 | 25.0% | 10675.8 ms | 0 |
| TABLE_ALIGNED #3 | 20 | 4 | 20.0% | 4005.2 ms | 0 |
| CONVEYOR_DONE #2 | 20 | 3 | 15.0% | 6063.4 ms | 0 |

### SAFETY

| Event | injections | FAULT | failure probability | DRAINED remaining-time median | pre-event failures |
|---|---:|---:|---:|---:|---:|
| LOADER_DONE #1 | 20 | 4 | 20.0% | 9151.9 ms | 0 |
| CONVEYOR_DONE #1 | 20 | 3 | 15.0% | 8132.6 ms | 0 |
| LID_DONE #1 | 19 | 2 | 10.5% | 4971.7 ms | 1 |
| FILLER_DONE #1 | 18 | 1 | 5.6% | 6592.2 ms | 2 |
| CONVEYOR_DONE #2 | 18 | 1 | 5.6% | 5566.1 ms | 2 |

## Event alignment quality

- Median cross-target difference in semantic-event timing: **3.99 ms**
- Maximum cross-target difference in semantic-event timing: **10.71 ms**
- Median actual HALT duration: **500.727 ms**
- 95th percentile absolute HALT-duration error: **1.926 ms**
- Median event-to-HALT start lag: **0.000 ms**

## Interpretation guardrails

- Rows that terminate before the selected semantic event are counted as **pre-event/background failures**, not injection-induced failures.
- The raw `completion_delta_vs_arch_baseline_median_ms` column is not used as the primary causal latency metric when baseline mission time is non-stationary.
- `drained_remaining_time_median_ms = mission_ms - event_match_mission_ms` is an event-relative descriptive recovery metric and is more robust to pre-event timing drift.
- Pooled target probabilities can be biased when later events have lower reachability. The report therefore also gives an **event-balanced mean failure probability**.
- A true steady-state availability value requires an explicit production-service up/down definition and a fault-arrival/exposure model.

## Data-quality flags

| Severity | Scope | Key | Message |
|---|---|---|---|
| WARN | BASELINE | CENTRALISED | No-fault baseline contained one or more terminal FAULT outcomes. |
| WARN | BASELINE | CENTRALISED | Successful baseline mission time is highly variable/non-stationary; avoid relying on one global baseline median for causal latency attribution. |
| WARN | EVENT_POINT | COORDINATOR:TABLE_ALIGNED#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | COORDINATOR:TABLE_ALIGNED#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | COORDINATOR:FILLER_DONE#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | COORDINATOR:FILLER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | PRODUCTION:FILLER_DONE#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | PRODUCTION:FILLER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | SAFETY:FILLER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | COORDINATOR:LID_DONE#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | COORDINATOR:LID_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | BATCH:LID_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | PRODUCTION:LID_DONE#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | PRODUCTION:LID_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | COORDINATOR:CAPPER_DONE#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | COORDINATOR:CAPPER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | BATCH:CAPPER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | PRODUCTION:CAPPER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | SAFETY:CAPPER_DONE#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | SAFETY:CAPPER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | COORDINATOR:LABELLER_DONE#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | COORDINATOR:LABELLER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | BATCH:LABELLER_DONE#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | BATCH:LABELLER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | SAFETY:LABELLER_DONE#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | SAFETY:LABELLER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | COORDINATOR:UNLOADER_DONE#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | COORDINATOR:UNLOADER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | BATCH:UNLOADER_DONE#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | BATCH:UNLOADER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | PRODUCTION:UNLOADER_DONE#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | PRODUCTION:UNLOADER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | SAFETY:UNLOADER_DONE#1 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | SAFETY:UNLOADER_DONE#1 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | SAFETY:UNLOADER_DONE#1 | 95th percentile absolute HALT-duration error exceeds 20 ms. |
| WARN | EVENT_POINT | COORDINATOR:LOADER_DONE#2 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | COORDINATOR:LOADER_DONE#2 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | BATCH:LOADER_DONE#2 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | PRODUCTION:LOADER_DONE#2 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | PRODUCTION:LOADER_DONE#2 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | SAFETY:LOADER_DONE#2 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | SAFETY:LOADER_DONE#2 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | SAFETY:CONVEYOR_DONE#2 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | BATCH:FILLER_DONE#2 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | PRODUCTION:FILLER_DONE#2 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | PRODUCTION:FILLER_DONE#2 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | BATCH:TABLE_ALIGNED#3 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | SAFETY:TABLE_ALIGNED#3 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |
| WARN | EVENT_POINT | COORDINATOR:TABLE_ALIGNED#5 | Event reachability below 90%; many runs terminated before the intended injection point. |
| WARN | EVENT_POINT | COORDINATOR:TABLE_ALIGNED#5 | At least 10% of attempts failed before reaching the injection event; interpret injected-fault probability with censoring/background-failure caution. |

Only the first 50 flags are shown here; see `data_quality_flags.csv` for all 57 flags.

## Recommended primary tables for the report

1. `target_summary.csv` — overall mission-success/failure coverage by failure domain.
2. `event_target_summary.csv` — the full event × target criticality map.
3. `reference_comparison.csv` — event-aligned comparison of each decomposed CD against the centralised Coordinator.
4. `event_timing_alignment.csv` — evidence that the semantic events are aligned across architectures/targets.
5. `baseline_summary.csv` + `data_quality_flags.csv` — controls and limitations.
