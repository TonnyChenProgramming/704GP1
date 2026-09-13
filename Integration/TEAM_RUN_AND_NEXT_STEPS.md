# GP1 Team Handoff — 如何執行、驗證與下一步

更新：2026-09-13  
已檢查的程式基準：GitHub `integration` branch，commit `3e28ce1`。

這份文件給 Tony / Zhiyuan、Tonny、Eric 使用。路徑以各自電腦的 repository root 為起點；不需要使用 Eric 的 D 槽路徑。

## 1. 現在完成到哪裡？

目前已可編譯並執行 **SystemJ coordinator + controller/plant simulation + workpiece tracker + read-only GUI**。

- 正常測試：B1 製作 8 瓶，完成後 B2 製作 2 瓶；兩種 recipes。
- 無效 batch request 會被拒絕。
- 544 項 coordinator model checks、72 項 archive checks 通過。
- 模擬 LID plant fault 後，coordinator 進入 HOLD，回報 FAULT。
- 已驗證 Eclipse JDT 編譯及 Eclipse bundled Java 21 執行；Eric 也已在 Eclipse 跑到正常 integration PASS。

**這代表目前 simulation baseline 通過，不代表整個 GP 已完成。** 新增的 `BatchManagerCD` 是 console-based interactive batch source，尚未接入真正 POS CD；active safety stop/reset、部分 sensor evidence 與 GUI 整理仍需完成。

## 2. 第一次使用：GitHub 與 Eclipse 匯入

### A. 取得正確版本

1. 在 GitHub Desktop 選擇 `704GP1` repository。
2. 先保留自己的未提交修改；不要用 discard changes 清掉工作。
3. 切到共享的 **`integration`** branch。
4. 按 **Fetch origin**；若出現 **Pull origin**，再按 Pull。
5. 在 History 確認包含 `05d9787 — fix(eclipse): align test packages and guard stale simulation ports`，或其後續版本。

Eric 本機分支名 `eric-integration` 是本機工作分支；組員不必建立同名分支。GitHub 共享目標是 `integration`，不是 `main`。

### B. 匯入兩個 Eclipse projects

建議使用獨立 Eclipse workspace，例如 `D:\EclipseWorkspaces\GP1`。Workspace 儲存 Eclipse 設定，不等於 Git repository。

1. 開啟 Eclipse，選擇 workspace，按 **Launch**。
2. **File → Import → General → Existing Projects into Workspace → Next**。
3. **Select root directory** 選擇 `<你的 repository>\Integration`。
4. 若有 **Search for nested projects**，勾選它。
5. 選擇下列兩個 projects；若未一起找到，可分別匯入兩個資料夾：

   | Repository 資料夾 | Eclipse project name | 用途 |
   | --- | --- | --- |
   | `Integration/Eric` | `Eric` | Finishing devices、tracker、GUI、Java helpers |
   | `Integration/Zhiyuan` | `CoordinatorSystemJ` | Coordinator、整合 XML、copied input/processing devices、build/test launchers |

6. **不要勾 Copy projects into workspace**，保留直接編輯 repository 的來源。
7. 按 **Finish**。不要同時匯入舊的 root-level `Eric` project，否則同名 project 會衝突。

目前測試的 XML 使用 `Integration/Zhiyuan/sysj` 裡的 Tonny controller/plant copies；不要額外把另一套同名 generated classes 加進這個 project。

## 3. 純 Eclipse：Build → Run

### A. 準備 Java

1. **Window → Preferences → Java → Installed JREs**，確認有完整 **JDK**。
2. Build launcher 需要 Java compiler API；只有 runtime 的 JRE 不足以 build。
3. 完整 build 已使用 JDK 26 驗證；Eclipse JDT 編譯的程式已使用 bundled Java 21 驗證執行。不要把 runtime 驗證誤當成所有 JDK build 組合皆已驗證。
4. 保留 project 的 Java 8 source/bytecode compatibility，這是舊 SystemJ compiler 的相容性要求；不需要因此把 Eclipse 換成 Java 8。

### B. 編譯 SystemJ

1. 在 Package Explorer 選取 `Eric` 和 `CoordinatorSystemJ`，按 **F5**。
2. 開啟 **Project → Build Automatically**，等 Java build 完成。
3. **Run → Run Configurations → Java Application → BuildAll**。
4. 確認設定：

   | 欄位 | 值 |
   | --- | --- |
   | Project | `CoordinatorSystemJ` |
   | Main class | `nz.ac.auckland.eabs.zhiyuan.tooling.EclipseSystemJBuild` |
   | Working directory（Arguments 分頁） | `${workspace_loc:/CoordinatorSystemJ}` |
   | VM arguments | `-Xmx768m` |
   | JRE 分頁 | 完整 JDK |

5. 按 **Run**，等幾分鐘。Console 出現 `Translate SystemJ to Java` 是正常編譯進度，不是當機。
6. 等到最後出現：

   ```text
   ECLIPSE SYSTEMJ BUILD PASSED
   ```

7. 再對兩個 projects 按 **F5**，等 Eclipse Java build 完成。
8. 打開 **Problems**，確認沒有紅色 errors；若有錯誤，不要選「仍然執行」。

若 launch entry 沒出現，先 refresh，或右鍵 `CoordinatorSystemJ/BuildAll.launch` 選對應的 Run As。也可以依上表建立 Java Application configuration。

**不要使用舊的 External Tools → Program → BuildAll**：那是 PowerShell 版本。現在不需要修改 PowerShell execution policy，也不需要手動執行 scripts。

為甚麼還需要 BuildAll？普通 Eclipse Java build 只會編譯 `.java`；BuildAll 先呼叫課程 SystemJ compiler 將 `.sysj` 翻譯為 Java，再驗證生成的程式。

### C. 跑正常整合與 GUI

1. **Run → Run Configurations → Java Application → RunCoordinator → Run**。
2. GUI 會顯示目前 batch、瓶子位置、operations 與 controller reports。
3. 現在是 **read-only**：不是實際 POS 操作畫面，也不直接控制 actuators。
4. Harness 自動執行 8 + 2 瓶，完成後 process 和 GUI 自動結束；這是正常行為。
5. Console 應出現下列訊息，後面沒有 exception：

   ```text
   COORDINATOR REAL DEVICE INTEGRATION PASSED: 8 + 2 bottles, 2 recipes, invalid batch rejected
   ```

不要只看到一行 PASS 就忽略後面的 exception。要同時確認執行正常結束、沒有 compilation/runtime error。

## 4. 測試按哪一個？

全部位於 **Run Configurations → Java Application**。

| Launch | 用途 | 成功標記 |
| --- | --- | --- |
| `BuildAll` | 完整 SystemJ / Java build，不跑 simulation | `ECLIPSE SYSTEMJ BUILD PASSED` |
| `CoordinatorTests` | 快速 coordinator model tests | `COORDINATOR MODEL TESTS PASSED (544 checks)` |
| `RunCoordinator` | Scripted 正常批次 + read-only GUI | `COORDINATOR REAL DEVICE INTEGRATION PASSED` |
| `RunCoordinatorReal` | Console 輸入訂單 + read-only GUI；持續等待下一單 | `DRAINED|<batchId>` 回到 BatchManager |
| `RunCoordinatorFault` | LID fault → batch FAULT/HOLD；預設沒有 GUI | `COORDINATOR REAL DEVICE FAULT HOLD PASSED` |
| `VerifyIntegration` | Fresh build + model + normal runtime + archive + fault tests | `ECLIPSE SYSTEMJ BUILD AND TEST PASSED` |

提交程式給組員前，請跑 **VerifyIntegration**。它使用與 BuildAll 相同的 Java main class，Program arguments 是 `--test`。

一次只跑一個 simulation。`RunCoordinator`、`RunCoordinatorFault` 與 VerifyIntegration 的 runtime 會用到相同 ports `30101–30103`。

### Logs 與 archive

- 完整驗證 logs：`Integration/Zhiyuan/build/eclipse-build-<id>/`。
- 可檢查 `model.log`、`normal.log`、`archive.log`、`fault.log`。
- 完整驗證 archive 在該 run folder 的 `workpieces.properties`。
- 普通 RunCoordinator 預設 archive：`Integration/Zhiyuan/build/coordinator-<UUID>.properties`。
- 每次 build/run 資料夾不同；這些是本機 outputs，不需要 commit。

## 5. 改哪裡？改完怎麼測？

| 想修改的部分 | 來源位置（repository-relative） |
| --- | --- |
| Coordinator SystemJ reactions / channels | `Integration/Zhiyuan/sysj/coordinator.sysj` |
| Coordinator 狀態、batch、dispatch、tracker 更新 | `Integration/Zhiyuan/src/nz/ac/auckland/eabs/zhiyuan/coordinator/IntegratedCoordinator.java` |
| 正常整合 wiring | `Integration/Zhiyuan/sysj/coordinator.xml` |
| Fault-test wiring | `Integration/Zhiyuan/sysj/coordinator_fault.xml` |
| Test-only batch driver | `Integration/Zhiyuan/sysj/coordinator_harness.sysj` |
| Coordinator Java tests | `Integration/Zhiyuan/tests/nz/ac/auckland/eabs/zhiyuan/coordinator/IntegratedCoordinatorTest.java` |
| Finishing SystemJ source | `Integration/Eric/systemj/` |
| Tracker / GUI / finishing helpers | `Integration/Eric/src/main/java/nz/ac/auckland/eabs/eric/` |
| Java build launcher | `Integration/Zhiyuan/src/nz/ac/auckland/eabs/zhiyuan/tooling/EclipseSystemJBuild.java` |

- 改 `.sysj`：**BuildAll → F5 → Eclipse Java build → run/test**。
- 只改 Java：**Eclipse Java build → run/test**；交付前仍跑 VerifyIntegration。
- 只改 XML：儲存後重新 run；所有 referenced CDs 必須先編譯好。
- **不要修改 `generated-src/*.java`**，下次 BuildAll 會重建。修改原本 `.sysj`。
- 不要 commit `bin/`、`build/`、generated classes、workspace `.metadata/`。

## 6. 接下來誰做甚麼？

以下是根據目前程式缺口的建議分工，請組員確認；不是宣稱已經全部完成或另行更改原本 work division。

### Priority 1 — Tony / Zhiyuan：把 interactive Batch Manager 接到真正 POS

目前 `RunCoordinatorReal` 已用 `BatchManagerCD` 取代 scripted harness，並以 console 輸入訂單。先保留 harness XML 作為 regression baseline；下一步是建立真正 POS CD 的 integration configuration，避免把 console entry 誤稱為 POS integration。

Batch activation 使用以下格式（不要再用舊的五欄位格式）：

```text
ACTIVATE|batchId|recipeId|productId|quantity|doseA|doseB|orderId
ACTIVATE|B1|R1|PRODUCT_X|8|20|80|ORDER-1
```

- ID：1–60 個字元，允許英數、`_`、`.`、`-`。
- Quantity：1–10000。
- Dose A/B：非負整數，合計 1–100；目前是 **simulation units，不是 mL**。
- Batch IDs 在 process 內不得重複；若跨次執行共用 archive，要確保不重用既有 IDs。
- 一次一個 active batch；收到 terminal result 才決定下一步。

Coordinator 的 `activateBatchIn` 接收 activation；`batchDrainedOut` 雖然名字保留 drained，實際可能傳回：

```text
DRAINED|batchId
REJECTED|batchId|INVALID_BATCH_OR_RECIPE
FAULT|batchId|reason
```

`DRAINED` 才代表該批完成；`REJECTED` 可修正 request 後再送；`FAULT` 代表 HOLD，不得當作成功直接送下一個 batch。

完成標準：從真正 POS 送出 order/recipe，完成兩個先後批次，batch 結果回到 POS，並測試 invalid request 與 fault 顯示。**現有 harness PASS 不能代替這項驗證。**

### Priority 1 — Tonny + 全組：Sensor 與 safety contract

- 先確認 loader/conveyor/rotary/filler 的 source of truth：目前 runnable copies 在 `Integration/Zhiyuan/sysj`；不要只改另一資料夾卻以為 runtime 已更新。
- Filler 要檢查 `dose1Done` / `dose2Done` 的 Boolean 值，補 false sensor cases。
- 目前 filler 回報的是 command targets，不能當成獨立量測的 actual fill。
- Rotary plant occupancy 現在固定 `000000`，並非真實 occupancy feedback；coordinator 使用自己的 logical occupancy，alignment 才是位置更新的確認。
- 全組先對齊 permit / stop / reset 的 ownership、訊息格式、controller enforcement 與 reset 條件，再實作及測試。

**目前 HOLD 只阻止新工作，不保證停止已發出的 machine cycle；它不是 emergency stop。** Acceptance XML 沒接真正 Safety Monitor。不能靠把 GUI 改成綠色來宣稱安全功能已完成。

### Priority 2 — Eric：GUI / Tracker integration 收尾

- 配合真正 POS/coordinator 接線，維持 tracker 更新只來自確認的完成／位置事件。
- 統一 GUI machine IDs，避免同一 station 出現 `NO DATA` 與另一個 READY card。
- 保留 unknown 與 zero 的區別；沒有 measured amounts 時顯示 unavailable，不能拿 requested dose 冒充。
- 區分 commanded/current/completed operations，核對 batch counters 與 archived records。
- 現有 GUI 是 read-only。若要加 operator controls，需透過已同意的 coordinator interface，不直接寫 plant actuators。
- Eric 本機尚有未提交的 GUI/bridge 修改，不在 `05d9787` 交付範圍；組員不要假設已經 pull 到它們。

### Priority 3 — 全組：共同驗收與 demo

1. 每個人先在自己的電腦跑通目前 baseline，記錄 commit ID。
2. 對齊 batch protocol、device source ownership、dose units、safety/reset contract。
3. 一次整合一項變更，保留舊的正常／fault tests。
4. 每次 handoff 提供：改了甚麼、改了哪些 files、測試結果、尚未完成的限制。
5. 真正 POS、safety 與 GUI 整合後，加上相應 end-to-end tests，不只重跑舊 harness。
6. Demo 準備：正常兩批、單瓶 tracking、fault/HOLD 與處理方式；同時保留 logs／截圖作為 backup。
7. 全組確認後才決定是否 merge 到 `main`；目前繼續使用 `integration`。

## 7. 常見問題

| 問題 | 處理方式 |
| --- | --- |
| `Project COMPSYS704-Eric does not exist` | 舊 launch 指向錯誤 project；本整合使用 `Eric` 與 `CoordinatorSystemJ`，重新選對應 shared launch。 |
| `IntegratedCoordinator cannot be resolved` / package mismatch | 確認包含 `05d9787`；兩個 projects F5 → Project → Clean。Test 必須在 package 對應目錄，不能放回 tests 根目錄。 |
| `Unresolved compilation problem`，即使前面有 PASS | Eclipse 有 problem classes；先處理所有紅色 errors，Clean/build，再 run。不要允許 launch despite errors。 |
| PowerShell script disabled | 選 Java Application 的 BuildAll，不是舊 External Tools Program。 |
| 找不到 Java compiler | 為 BuildAll / VerifyIntegration 選完整 JDK。 |
| Port unavailable / 上次 fault 後 run 卡住 | 在 Eclipse Console 選上次 simulation，按紅色 Terminate；不要同時跑多個 simulation。 |
| GUI 顯示 UNSAFE / NO DATA | 先查目前 profile 與訊息來源；現有測試沒有完整 safety monitor，NO DATA 也不等於 OFFLINE。不要為了 demo 偽造狀態。 |
| GUI 跑完自動關閉 | 目前 bounded test harness 完成後正常退出；不是 persistent POS service。 |
| 黃色 warnings | 不等於 test failure；生成碼常有 warnings，但必須檢查紅色 errors 與 runtime exceptions。 |

## 8. 開會可直接使用的進度說明

> Coordinator 已經可以在 Eclipse compile/run，controller/plant simulation、tracker 和 read-only GUI 已接通。正常 8 + 2 瓶、兩種 recipes、archive checks 和 LID fault/HOLD 都通過，程式在 integration branch。下一步不是重寫整套 coordinator，而是接真正 POS/batch manager、補 sensor/safety contract，然後完成 GUI 對接並做共同 end-to-end 驗收。

詳細技術背景：[Coordinator integration handoff](Zhiyuan/COORDINATOR_INTEGRATION.md)。  
簡短操作參考：[Coordinator README](Zhiyuan/README.md)。
