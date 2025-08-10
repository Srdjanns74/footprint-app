param(
  [string]$Branch = "main"
)

# 1) Uzmi sadržaj sa clipboarda i snimi kao temp .patch
$clip = Get-Clipboard
if ([string]::IsNullOrWhiteSpace($clip)) {
  Write-Host "Clipboard je prazan. Kopiraj patch iz chata pa ponovo pokreni."
  exit 1
}
$temp = New-Item -ItemType File -Path ([System.IO.Path]::GetTempFileName() + ".patch")
Set-Content -Path $temp -Value $clip -Encoding UTF8

# 2) Proveri da li smo u git repo-u
if (!(Test-Path ".git")) {
  Write-Host "Nisi u root folderu git repozitorijuma. Pokreni skriptu u folderu footprint-app."
  exit 1
}

# 3) Povuci najnovije promene i prebaci se na granu
git fetch origin | Out-Null
git checkout $Branch
git pull --rebase origin $Branch

# 4) Proba suvo (dry-run) pa stvarno primeni patch
git apply --check $temp
if ($LASTEXITCODE -ne 0) {
  Write-Host "Patch se ne može primeniti (conflict). Proveri da li si kopirao ceo patch."
  exit 1
}

git apply $temp

# 5) Commit + push
git add -A
$timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
git commit -m "Apply patch from AI ($timestamp)"
git push origin $Branch

Write-Host "✅ Patch primenjen i pushovan na $Branch."
