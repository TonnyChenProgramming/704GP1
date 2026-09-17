# Windows PowerShell 5 entry point for Eclipse External Tools; tests require pwsh 7.
$ErrorActionPreference = 'Stop'
$pwshCommand = Get-Command pwsh -ErrorAction SilentlyContinue
if ($pwshCommand) { $coordinatorPwsh = $pwshCommand.Source }
else {
    $coordinatorPwsh = Join-Path $env:USERPROFILE '.cache\codex-runtimes\codex-primary-runtime\dependencies\native\powershell\pwsh.exe'
    if (-not (Test-Path -LiteralPath $coordinatorPwsh)) { throw 'PowerShell 7 not found. Install/configure pwsh on PATH, or run test-coordinator.ps1 using your PowerShell 7 executable.' }
}
& $coordinatorPwsh -NoProfile -File (Join-Path $PSScriptRoot 'test-coordinator.ps1')
exit $LASTEXITCODE
