#requires -Version 7.0
param(
    [string]$SystemJLibPath = ''
)

$ErrorActionPreference = 'Stop'
$candidateLibraryPaths = @(
    $SystemJLibPath,
    'D:\CS704\Labs\COMPSYS704_Lab_3\lib',
    (Join-Path $env:USERPROFILE 'Downloads\SystemJ labs (COMPSYS 704)\SystemJ labs (COMPSYS 704)\Lab-2\COMPSYS704_Lab_2 (1)\lib')
)
$SystemJLibPath = $candidateLibraryPaths |
    Where-Object { $_ -and (Test-Path -LiteralPath $_) } |
    Select-Object -First 1
if (-not $SystemJLibPath) {
    throw 'No SystemJ library directory was found. Pass -SystemJLibPath.'
}

$ericRoot = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$classRoot = Join-Path $ericRoot 'build\classes'
$generatedRoot = Join-Path $ericRoot 'build\generated-systemj'
$stagedLibraryRoot = Join-Path $ericRoot 'build\systemj-lib'
$systemJSources = @(
    (Join-Path $ericRoot 'systemj\finishing_devices.sysj'),
    (Join-Path $ericRoot 'systemj\finishing_contract.sysj'),
    (Join-Path $ericRoot 'systemj\finishing_shims.sysj'),
    (Join-Path $ericRoot 'systemj\finishing_shim_contract.sysj')
)

& (Join-Path $PSScriptRoot 'build-and-test.ps1')

$sourceCompilerJar = Join-Path $SystemJLibPath 'sjc-2.2-13-g8ab684c-SNAPSHOT.jar'
if (-not (Test-Path -LiteralPath $sourceCompilerJar)) {
    throw "SystemJ compiler not found: $sourceCompilerJar"
}

New-Item -ItemType Directory -Path $generatedRoot -Force | Out-Null
$resolvedGenerated = (Resolve-Path -LiteralPath $generatedRoot).Path
if (-not $resolvedGenerated.StartsWith($ericRoot + '\build\', [StringComparison]::OrdinalIgnoreCase)) {
    throw "Generated output escaped Eric/build: $resolvedGenerated"
}
Get-ChildItem -LiteralPath $generatedRoot -Filter '*.java' -File |
    Remove-Item -Force

New-Item -ItemType Directory -Path $stagedLibraryRoot -Force | Out-Null
$sourceLibraryJars = Get-ChildItem -LiteralPath $SystemJLibPath -Filter '*.jar' -File |
    # SystemJ does not use these Kotlin support jars. On the laboratory bundle
    # some of them are unreadable to javac on current Windows/JDK releases.
    Where-Object { $_.Name -notlike 'kotlin-*.jar' }
foreach ($jar in $sourceLibraryJars) {
    Copy-Item -LiteralPath $jar.FullName -Destination $stagedLibraryRoot -Force
}
$libraryJars = Get-ChildItem -LiteralPath $stagedLibraryRoot -Filter '*.jar' -File |
    Select-Object -ExpandProperty FullName
$classpath = (@($libraryJars) + $classRoot) -join ';'

# Every child is bounded, hidden, checked for exit failure, and killed only by
# its owned Process handle. A stuck compiler/runtime must not pass on stale classes.
function Invoke-FinishingProcess([string]$Program, [string[]]$Arguments, [int]$TimeoutMs = 60000) {
    $info = [System.Diagnostics.ProcessStartInfo]::new()
    $info.FileName = (Get-Command $Program).Source
    $info.WorkingDirectory = $ericRoot
    $info.UseShellExecute = $false
    $info.CreateNoWindow = $true
    $info.RedirectStandardOutput = $true
    $info.RedirectStandardError = $true
    foreach ($argument in $Arguments) { $info.ArgumentList.Add($argument) }
    $child = [System.Diagnostics.Process]::new()
    $child.StartInfo = $info
    $started = $false
    try {
        $null = $child.Start()
        $started = $true
        $stdout = $child.StandardOutput.ReadToEndAsync()
        $stderr = $child.StandardError.ReadToEndAsync()
        if (-not $child.WaitForExit($TimeoutMs)) {
            $child.Kill($true); $child.WaitForExit()
            $partialOutput = $stdout.GetAwaiter().GetResult()
            $partialErrors = $stderr.GetAwaiter().GetResult()
            throw "$Program timed out after $TimeoutMs ms. No successful verification is recorded.`n$partialOutput`n$partialErrors"
        }
        $output = $stdout.GetAwaiter().GetResult()
        $errors = $stderr.GetAwaiter().GetResult()
        if ($errors) { Write-Host $errors }
        if ($child.ExitCode -ne 0) { throw "$Program exited with code $($child.ExitCode): $output" }
        return $output
    } finally {
        if ($started -and -not $child.HasExited) { $child.Kill($true); $child.WaitForExit() }
        $child.Dispose()
    }
}
foreach ($systemJSource in $systemJSources) {
    Write-Host "Generating SystemJ: $systemJSource"
    $generationOutput = Invoke-FinishingProcess 'java' @('-Xmx384m', '-cp', $classpath,
        'nz.ac.auckland.eabs.eric.tooling.SystemJCompilerLauncher', '-d', $generatedRoot,
        '--nojavac', '--silence', '--', $systemJSource)
    if ($generationOutput) { Write-Host $generationOutput }
}

$generatedSources = @(
    Get-ChildItem -LiteralPath $generatedRoot -Filter '*.java' -File |
        Select-Object -ExpandProperty FullName
)
if ($generatedSources.Count -eq 0) {
    throw 'The SystemJ compiler produced no Java sources.'
}

$javacOutput = Invoke-FinishingProcess 'javac' (@('-encoding', 'UTF-8', '-cp', $classpath, '-d', $classRoot) + $generatedSources)
if ($javacOutput) { Write-Host $javacOutput }
$expectedClasses = @(
    'LidLoaderControllerCD.class', 'LidLoaderPlantCD.class',
    'CapperControllerCD.class', 'CapperPlantCD.class',
    'LabelerControllerCD.class', 'LabelerPlantCD.class',
    'UnloaderControllerCD.class', 'UnloaderPlantCD.class'
)
foreach ($className in $expectedClasses) {
    if (-not (Test-Path -LiteralPath (Join-Path $classRoot $className))) {
        throw "Generated SystemJ class was missing: $className"
    }
}

$output = Invoke-FinishingProcess 'java' @('-Xmx256m', '-Djava.awt.headless=true',
    '-Deric.finishing.testMode=true', '-cp', $classpath, 'com.systemj.SystemJRunner',
    'config/finishing-contract.xml') 30000
Write-Host $output
$expected = @(
    'ERIC SYSTEMJ LID CONTRACT PASSED', 'ERIC SYSTEMJ CAPPER CONTRACT PASSED',
    'ERIC SYSTEMJ LABELER CONTRACT PASSED', 'ERIC SYSTEMJ UNLOADER CONTRACT PASSED',
    'ERIC SYSTEMJ DYNAMIC FINISHING ACCEPTANCE PASSED'
)
foreach ($marker in $expected) {
    if (-not $output.Contains($marker)) { throw "Eric SystemJ contract marker was missing: $marker" }
}
[System.IO.File]::WriteAllText((Join-Path $ericRoot 'build\systemj-dynamic-last.log'), $output)

Write-Host 'ERIC SYSTEMJ BUILD AND CONTRACT TEST PASSED'

# Separate process: legacy rich tests above retain their fail-closed startup.
# Flat simulation opts into Tony's fixed-precondition / automatic-repair profile.
$shimOutput = Invoke-FinishingProcess 'java' @('-Xmx256m', '-Djava.awt.headless=true',
    '-Deric.finishing.flatSimulation=true', '-Deric.finishing.testMode=true',
    '-cp', $classpath, 'com.systemj.SystemJRunner', 'config/finishing-shim-contract.xml') 30000
Write-Host $shimOutput
foreach ($station in @('LID', 'CAPPER', 'LABELLER', 'UNLOADER')) {
    if (-not $shimOutput.Contains("ERIC FLAT SHIM $station PASSED")) {
        throw "Flat shim marker missing: $station"
    }
}
if (-not $shimOutput.Contains('ERIC SYSTEMJ FLAT SHIM ACCEPTANCE PASSED')) {
    throw 'Flat shim run did not finish all four real controller/plant pairs.'
}
[System.IO.File]::WriteAllText((Join-Path $ericRoot 'build\systemj-flat-shim-last.log'), $shimOutput)
Write-Host 'ERIC SYSTEMJ RICH AND FLAT CONTRACT TESTS PASSED'
