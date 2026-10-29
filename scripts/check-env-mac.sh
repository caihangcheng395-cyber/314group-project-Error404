#!/usr/bin/env bash
set -euo pipefail
for cmd in java mvn node npm; do
  if command -v "$cmd" >/dev/null 2>&1; then
    echo "[OK] $cmd: $(command -v "$cmd")"
  else
    echo "[MISSING] $cmd (required for non-Docker local development)"
  fi
done
if command -v docker >/dev/null 2>&1; then
  echo "[OK] docker is installed (ensure Docker Desktop is running)"
else
  echo "[INFO] docker not found; install Docker Desktop for one-command startup"
fi
