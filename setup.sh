#!/usr/bin/env bash
#
# setup.sh — Bootstraps and runs the URL-Shortener & Analytics project end-to-end.
# Tested on Debian/Ubuntu. Requires sudo for tool installation.
set -euo pipefail

#—— Helpers ————————————————————————————————————————————————

log()   { echo -e "\e[1;32m[INFO]\e[0m $*"; }
warn()  { echo -e "\e[1;33m[WARN]\e[0m $*"; }
error() { echo -e "\e[1;31m[ERROR]\e[0m $*" >&2; exit 1; }

# Install Debian/Ubuntu packages if missing
install_pkg() {
  sudo apt-get update
  sudo apt-get install -y "$@"
}

# Check if a command exists
require_cmd() {
  if ! command -v "$1" &>/dev/null; then
    return 1
  fi
  return 0
}

#—— 1. Check / Install Prerequisites ————————————————————————

log "Checking prerequisites..."

# 1a. Java 21
if ! require_cmd java || ! java -version 2>&1 | grep -q '"21\.'; then
  warn "Java 21 not found. Installing default-jdk..."
  install_pkg default-jdk
fi

# 1b. Docker & (Compose V2 or V1)
if ! require_cmd docker; then
  warn "Docker not found. Installing Docker CE..."
  install_pkg \
    apt-transport-https ca-certificates curl gnupg lsb-release \
    software-properties-common
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg \
    | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
  echo \
    "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] \
    https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" \
    | sudo tee /etc/apt/sources.list.d/docker.list >/dev/null
  sudo apt-get update
  install_pkg docker-ce docker-ce-cli containerd.io docker-compose-plugin
fi

# Determine Compose command
if docker compose version &>/dev/null; then
  COMPOSE_CMD="docker compose"
elif require_cmd docker-compose; then
  COMPOSE_CMD="docker-compose"
else
  warn "docker-compose plugin missing. Installing via pip..."
  install_pkg python3-pip
  python3 -m pip install --user docker-compose
  export PATH="$HOME/.local/bin:$PATH"
  COMPOSE_CMD="docker-compose"
fi

# 1c. Gradle wrapper
if [ ! -f ./gradlew ]; then
  warn "Gradle wrapper not found. Generating..."
  if ! require_cmd gradle; then
    install_pkg gradle
  fi
  gradle wrapper --gradle-version=8.13
  chmod +x gradlew
fi

# 1d. Node.js & pnpm
if ! require_cmd node || ! node --version 2>&1 | grep -q '^v2[0-9]\.'; then
  warn "Node.js 20+ not found. Installing Node.js via NodeSource..."
  curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
  install_pkg nodejs
fi

if ! require_cmd pnpm; then
  warn "pnpm not found. Bootstrapping via Corepack..."
  corepack enable
  corepack prepare pnpm@latest --activate

  if ! require_cmd pnpm; then
    warn "Corepack failed—installing pnpm via sudo npm..."
    sudo npm install -g pnpm
  fi
fi

log "All prerequisites installed/verified."

#—— 2. Build Java Services ——————————————————————————————

log "Building Java services with Gradle..."
./gradlew clean build --parallel

#—— 3. Start Infrastructure & Services ——————————————————————

log "Starting infra & microservices via Docker Compose..."
pushd infra >/dev/null
$COMPOSE_CMD up --build -d
popd >/dev/null

#—— 4. Start Analytics UI ——————————————————————————————

log "Starting UI (React/Vite)…"
pushd ui-analytics >/dev/null
pnpm install
pnpm run dev > ../ui-analytics.log 2>&1 &
UI_PID=$!
popd >/dev/null

#—— 5. Final Status —————————————————————————————————————

log "🎉 All components launched!"
echo
log "Service endpoints:"
echo "  • URL-Shortener API:    http://localhost:8080"
echo "  • Click-Collector API:  http://localhost:8081"
echo "  • Analytics UI:         http://localhost:4173"
echo
log "To view UI logs:     tail -f ui-analytics.log"
log "To stop Docker:      cd infra && $COMPOSE_CMD down"
log "To stop UI server:  kill $UI_PID"
