while ($true) {
    git add .
    git commit -m "Auto update $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')" 2>$null
    git push
    Start-Sleep -Seconds 60
}
