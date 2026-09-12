#requires -Version 7.0
param([string]$SystemJLibPath = '', [switch]$Gui)
$ErrorActionPreference = 'Stop'
$root = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$eric = (Resolve-Path (Join-Path $root '..\Eric')).Path
if (-not $SystemJLibPath) { $SystemJLibPath = Join-Path $root 'lib' }
$compilerJar = Join-Path $SystemJLibPath 'sjc-2.2-13-g8ab684c-SNAPSHOT.jar'
if (-not (Test-Path -LiteralPath $compilerJar)) { throw "Pass -SystemJLibPath containing the course compiler: $compilerJar" }
# A fresh output directory on EVERY run prevents old Java/classes from passing.
$run = Join-Path $root ('build\verification-' + [Guid]::NewGuid().ToString('N'))
$classes = Join-Path $run 'classes'
$generated = Join-Path $run 'generated'
$libs = Join-Path $run 'lib'
New-Item -ItemType Directory -Path $classes, $generated, $libs -Force | Out-Null
Get-ChildItem -LiteralPath $SystemJLibPath -Filter '*.jar' -File |
    Where-Object { $_.Name -notlike 'kotlin-*' } |
    ForEach-Object { Copy-Item -LiteralPath $_.FullName -Destination $libs }
$cp = "$classes;$libs\*"

function Invoke-Checked([string]$Program, [string[]]$Arguments, [int]$TimeoutMs = 60000) {
    $info = [System.Diagnostics.ProcessStartInfo]::new()
    $info.FileName = (Get-Command $Program).Source
    $info.WorkingDirectory = $root
    $info.UseShellExecute = $false
    $info.CreateNoWindow = $true
    $info.RedirectStandardOutput = $true
    $info.RedirectStandardError = $true
    foreach ($argument in $Arguments) { $info.ArgumentList.Add($argument) }
    $child = [System.Diagnostics.Process]::new()
    $child.StartInfo = $info
    try {
        $null = $child.Start()
        $stdout = $child.StandardOutput.ReadToEndAsync()
        $stderr = $child.StandardError.ReadToEndAsync()
        if (-not $child.WaitForExit($TimeoutMs)) {
            $child.Kill($true); $child.WaitForExit()
            throw "$Program timed out: $($stdout.GetAwaiter().GetResult()) $($stderr.GetAwaiter().GetResult())"
        }
        $output = $stdout.GetAwaiter().GetResult() + $stderr.GetAwaiter().GetResult()
        # The SystemJ compiler sometimes reports errors but exits with code zero.
        if ($child.ExitCode -ne 0 -or $output -match '(?im)error:|Internal errors were detected|OutOfMemoryError|Exception in thread') {
            throw "$Program failed (exit $($child.ExitCode)):`n$output"
        }
        return $output
    } finally { $child.Dispose() }
}

$javaSources = @(Get-ChildItem (Join-Path $eric 'src\main\java') -Recurse -Filter '*.java' -File | Select-Object -ExpandProperty FullName)
$javaSources += Join-Path $root 'src\nz\ac\auckland\eabs\zhiyuan\coordinator\IntegratedCoordinator.java'
$javaSources += @(Get-ChildItem (Join-Path $root 'tests') -Filter '*.java' -File | Select-Object -ExpandProperty FullName)
Write-Host (Invoke-Checked 'javac' (@('--release', '8', '-encoding', 'UTF-8', '-d', $classes) + $javaSources))
$unit = Invoke-Checked 'java' @('-Djava.awt.headless=true', '-cp', $cp, 'nz.ac.auckland.eabs.zhiyuan.coordinator.IntegratedCoordinatorTest')
if (-not $unit.Contains('COORDINATOR MODEL TESTS PASSED')) { throw "Unit test marker missing: $unit" }
Write-Host $unit

$sources = @('coordinator.sysj', 'coordinator_harness.sysj', 'BottleLoaderController.sysj', 'BottleLoaderPlant.sysj',
    'ConveyorController.sysj', 'ConveyorPlant.sysj', 'RoteryTableController.sysj', 'RoteryTablePlant.sysj',
    'TwoLiquidFillerController.sysj', 'TwoLiquidFillerPlant.sysj') |
    ForEach-Object { Join-Path $root "sysj\$_" }
$sources += Join-Path $eric 'systemj\finishing_devices.sysj'
$sources += Join-Path $eric 'systemj\finishing_shims.sysj'
foreach ($source in $sources) {
    Write-Host "SystemJ compile: $([IO.Path]::GetFileName($source))"
    Write-Host (Invoke-Checked 'java' @('-Xmx384m', '-cp', $cp, 'nz.ac.auckland.eabs.eric.tooling.SystemJCompilerLauncher',
        '-d', $generated, '--nojavac', '--silence', '--', $source))
}
$generatedFiles = @(Get-ChildItem -LiteralPath $generated -Filter '*.java' -File | Select-Object -ExpandProperty FullName)
# 1 coordinator + 2 alternative batch harnesses + 8 Tonny CDs + 12 Eric CDs.
if ($generatedFiles.Count -ne 23) { throw "Expected 23 freshly generated CDs, found $($generatedFiles.Count)" }
Write-Host (Invoke-Checked 'javac' (@('-encoding', 'UTF-8', '-cp', $cp, '-d', $classes) + $generatedFiles))
foreach ($source in $generatedFiles) {
    $class = Join-Path $classes ([IO.Path]::GetFileNameWithoutExtension($source) + '.class')
    if (-not (Test-Path -LiteralPath $class)) { throw "Missing fresh class: $class" }
}
$runtimeArgs = @('-Xmx256m', '-Deric.finishing.flatSimulation=true', "-Dcoordinator.archive=$run\workpieces.properties")
if ($Gui) { $runtimeArgs += '-Dcoordinator.gui=true' } else { $runtimeArgs += '-Djava.awt.headless=true' }
$runtimeArgs += @('-cp', $cp, 'com.systemj.SystemJRunner', 'sysj/coordinator.xml')
$output = Invoke-Checked 'java' $runtimeArgs 120000
Write-Host $output
if (-not $output.Contains('COORDINATOR REAL DEVICE INTEGRATION PASSED')) { throw 'Integration did not reach verified completion' }
# Verify persisted records using a separate JVM, not the in-process tracker cache.
$archiveCheck = Invoke-Checked 'java' @('-Djava.awt.headless=true', '-cp', $cp,
    'nz.ac.auckland.eabs.zhiyuan.coordinator.IntegratedCoordinatorTest', "$run\workpieces.properties")
Write-Host $archiveCheck
if (-not $archiveCheck.Contains('COORDINATOR ARCHIVE CHECK PASSED')) { throw 'Archive verification marker missing' }
$faultOutput = Invoke-Checked 'java' @('-Xmx256m', '-Djava.awt.headless=true', '-Deric.finishing.flatSimulation=true',
    '-Deric.finishing.testMode=true', '-Dcoordinator.testLidFault=true', "-Dcoordinator.archive=$run\fault-workpieces.properties",
    '-cp', $cp, 'com.systemj.SystemJRunner', 'sysj/coordinator_fault.xml') 45000
Write-Host $faultOutput
if (-not $faultOutput.Contains('COORDINATOR REAL DEVICE FAULT HOLD PASSED')) { throw 'Real LID fault did not hold the batch' }
if (Test-Path -LiteralPath (Join-Path $run 'fault-workpieces.properties')) { throw 'Faulted bottle was incorrectly archived as complete' }
[IO.File]::WriteAllText((Join-Path $run 'acceptance.log'), $unit + $output + $archiveCheck + $faultOutput)
# Publish only VERIFIED generated Java for Eclipse's normal incremental builder.
# No existing authored source is modified; this dedicated directory is ignored.
$eclipseGenerated = Join-Path $root 'build\eclipse-generated'
New-Item -ItemType Directory -Path $eclipseGenerated -Force | Out-Null
foreach ($source in $generatedFiles) { Copy-Item -LiteralPath $source -Destination $eclipseGenerated -Force }
Write-Host "COORDINATOR BUILD AND TEST PASSED. Evidence: $run"
