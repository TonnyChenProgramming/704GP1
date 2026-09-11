$ErrorActionPreference = 'Stop'

$ericRoot = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$mainRoot = Join-Path $ericRoot 'src\main\java'
$classRoot = Join-Path $ericRoot 'build\classes'

New-Item -ItemType Directory -Path $classRoot -Force | Out-Null
$sources = @(
    Get-ChildItem -LiteralPath $mainRoot -Recurse -Filter '*.java' -File |
        Select-Object -ExpandProperty FullName
)

& javac --release 8 -encoding UTF-8 -d $classRoot $sources
if ($LASTEXITCODE -ne 0) {
    throw 'Eric GUI compilation failed.'
}

& java -cp $classRoot nz.ac.auckland.eabs.eric.gui.DashboardApplication
