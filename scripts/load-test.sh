#!/usr/bin/env bash
set -euo pipefail

# Parallel load test for the turnover endpoints: prints latency percentiles.
#
# Usage:
#   ./scripts/load-test.sh                          # daily endpoint only
#   ENDPOINTS="daily week" ./scripts/load-test.sh   # after task 1 is done
#   REQUESTS=200 CONCURRENCY=20 ./scripts/load-test.sh

BASE_URL="${BASE_URL:-http://localhost:8080}"
REQUESTS="${REQUESTS:-100}"
CONCURRENCY="${CONCURRENCY:-10}"
ENDPOINTS="${ENDPOINTS:-daily}"

USERS="ee6c8623-02f5-438e-9c4d-24179da54307 5c6569b5-57e8-45ab-a72f-ff7affbd874e"
COINS="USD BTC RUB ETH KZT SOL"

urls_file=$(mktemp)
out_file=$(mktemp)
trap 'rm -f "$urls_file" "$out_file"' EXIT

eps=($ENDPOINTS); users=($USERS); coins=($COINS)
for ((i = 0; i < REQUESTS; i++)); do
  ep=${eps[$((i % ${#eps[@]}))]}
  user=${users[$((i % ${#users[@]}))]}
  coin=${coins[$((i % ${#coins[@]}))]}
  echo "$BASE_URL/turnover/$ep/$user?coin=$coin"
done > "$urls_file"

echo "Load test: $REQUESTS requests, concurrency=$CONCURRENCY, endpoints: $ENDPOINTS"
start=$SECONDS
xargs -P "$CONCURRENCY" -n 1 curl -s -o /dev/null -w '%{http_code} %{time_total}\n' \
  < "$urls_file" > "$out_file"
elapsed=$((SECONDS - start))

errors=$(awk '$1 != 200' "$out_file" | wc -l | tr -d ' ')

awk '$1 == 200 { print $2 }' "$out_file" | sort -n | awk \
  -v elapsed="$elapsed" -v errors="$errors" -v sent="$REQUESTS" '
  { a[NR] = $1; sum += $1 }
  function pct(p,   i) { i = int(NR * p + 0.5); if (i < 1) i = 1; if (i > NR) i = NR; return a[i] * 1000 }
  END {
    if (NR == 0) { print "No successful (200) responses — is the app running?"; exit 1 }
    printf "\nOK: %d  errors(non-200): %d  wall time: %ds  throughput: ~%.1f req/s\n\n", NR, errors, elapsed, (elapsed > 0 ? sent / elapsed : sent)
    printf "  %-6s %8.0f ms\n", "min", a[1] * 1000
    printf "  %-6s %8.0f ms\n", "avg", sum / NR * 1000
    printf "  %-6s %8.0f ms\n", "p50", pct(0.50)
    printf "  %-6s %8.0f ms\n", "p90", pct(0.90)
    printf "  %-6s %8.0f ms\n", "p95", pct(0.95)
    printf "  %-6s %8.0f ms\n", "p99", pct(0.99)
    printf "  %-6s %8.0f ms\n", "max", a[NR] * 1000
  }'
