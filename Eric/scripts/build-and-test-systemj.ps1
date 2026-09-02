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
$systemJSource = Join-Path $ericRoot 'systemj\finishing_contract.sysj'

& (Join-Path $PSScriptRoot 'build-and-test.ps1')

$sourceCompilerJar = Join-Path $SystemJLibPath 'sjc-2.2-13-g8ab684c-SNAPSHOT.jar'
if (-not (Test-Path -LiteralPath $sourceCompilerJar)) {
    throw "SystemJ compiler not found: $sourceCompilerJar"
}

New-Item -ItemType Directory -Path $generatedRoot -Force | Out-Null
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
& java -cp $classpath nz.ac.auckland.eabs.eric.tooling.SystemJCompilerLauncher -d $generatedRoot --nojavac --silence -- $systemJSource
if ($LASTEXITCODE -ne 0) {
    throw 'Eric SystemJ source generation failed.'
}

$generatedSources = @(
    Get-ChildItem -LiteralPath $generatedRoot -Filter '*.java' -File |
        Select-Object -ExpandProperty FullName
)
if ($generatedSources.Count -eq 0) {
    throw 'The SystemJ compiler produced no Java sources.'
}

# JDK 26 may emit a ZipFileSystem AccessDenied stack trace while closing these
# legacy lab jars even though compilation succeeded and returned exit code 0.
# Capture that post-compile diagnostic, but surface every genuine non-zero
# compiler result and verify that all expected generated classes exist below.
$javacInfo = [System.Diagnostics.ProcessStartInfo]::new()
$javacInfo.FileName = (Get-Command javac).Source
$javacInfo.UseShellExecute = $false
$javacInfo.CreateNoWindow = $true
$javacInfo.RedirectStandardOutput = $true
$javacInfo.RedirectStandardError = $true
foreach ($argument in @('-encoding', 'UTF-8', '-cp', $classpath, '-d', $classRoot)) {
    $javacInfo.ArgumentList.Add($argument)
}
foreach ($source in $generatedSources) {
    $javacInfo.ArgumentList.Add($source)
}
$javacProcess = [System.Diagnostics.Process]::new()
$javacProcess.StartInfo = $javacInfo
$null = $javacProcess.Start()
$javacOutput = $javacProcess.StandardOutput.ReadToEnd()
$javacErrors = $javacProcess.StandardError.ReadToEnd()
$javacProcess.WaitForExit()
if ($javacProcess.ExitCode -ne 0) {
    Write-Host $javacOutput
    Write-Error $javacErrors
    throw 'Generated Eric SystemJ Java compilation failed.'
}
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

Push-Location $ericRoot
try {
    $startInfo = [System.Diagnostics.ProcessStartInfo]::new()
    $startInfo.FileName = (Get-Command java).Source
    $startInfo.WorkingDirectory = $ericRoot
    $startInfo.UseShellExecute = $false
    $startInfo.CreateNoWindow = $true
    $startInfo.RedirectStandardOutput = $true
    $startInfo.RedirectStandardError = $true
    $startInfo.ArgumentList.Add('-cp')
    $startInfo.ArgumentList.Add($classpath)
    $startInfo.ArgumentList.Add('com.systemj.SystemJRunner')
    $startInfo.ArgumentList.Add('config/finishing-contract.xml')

    $process = [System.Diagnostics.Process]::new()
    $process.StartInfo = $startInfo
    $null = $process.Start()
    $stdoutTask = $process.StandardOutput.ReadToEndAsync()
    $stderrTask = $process.StandardError.ReadToEndAsync()

    if (-not $process.WaitForExit(10000)) {
        $process.Kill($true)
        $process.WaitForExit()
    }

    $output = $stdoutTask.GetAwaiter().GetResult()
    $errorOutput = $stderrTask.GetAwaiter().GetResult()
    Write-Host $output
    if ($errorOutput) {
        Write-Warning $errorOutput
    }
    $expected = @(
        'ERIC SYSTEMJ LID CONTRACT PASSED',
        'ERIC SYSTEMJ CAPPER CONTRACT PASSED',
        'ERIC SYSTEMJ LABELER CONTRACT PASSED',
        'ERIC SYSTEMJ UNLOADER CONTRACT PASSED'
    )
    foreach ($marker in $expected) {
        if (-not $output.Contains($marker)) {
            throw "Eric SystemJ contract marker was missing: $marker"
        }
    }
} finally {
    Pop-Location
}

Write-Host 'ERIC SYSTEMJ BUILD AND CONTRACT TEST PASSED'
