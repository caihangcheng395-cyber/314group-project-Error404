Write-Host "CSIT314 environment check (Windows PowerShell)" -ForegroundColor Cyan
foreach ($cmd in @('java', 'mvn', 'node', 'npm', 'docker')) {
    if (Get-Command $cmd -ErrorAction SilentlyContinue) {
        Write-Host "[OK] $cmd"
    } else {
        Write-Host "[MISSING] $cmd"
    }
}
Write-Host "Use 'docker compose up --build' from the project root, or run Maven and npm in separate terminals."
