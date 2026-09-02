$ErrorActionPreference = 'Stop'

$ericRoot = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$mainRoot = Join-Path $ericRoot 'src\main\java'
$testRoot = Join-Path $ericRoot 'src\test\java'
$classRoot = Join-Path $ericRoot 'build\classes'

New-Item -ItemType Directory -Path $classRoot -Force | Out-Null

$sources = @(
    Get-ChildItem -LiteralPath $mainRoot,$testRoot -Recurse -Filter '*.java' -File |
        Select-Object -ExpandProperty FullName
)

if ($sources.Count -eq 0) {
    throw 'No Eric Java sources were found.'
}

& javac --release 8 -encoding UTF-8 -d $classRoot $sources
if ($LASTEXITCODE -ne 0) {
    throw 'Eric subsystem compilation failed.'
}

Push-Location $ericRoot
try {
    & java -ea '-Djava.awt.headless=true' -cp $classRoot nz.ac.auckland.eabs.eric.EricSubsystemTest
    if ($LASTEXITCODE -ne 0) {
        throw 'Eric subsystem tests failed.'
    }
} finally {
    Pop-Location
}

Write-Host 'ERIC GP SUBSYSTEM BUILD AND TESTS PASSED'
