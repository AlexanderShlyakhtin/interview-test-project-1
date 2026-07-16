# Тестовый проект: обороты пользователей (crypto/fiat)

Мини-сервис на Spring Boot 3 / Java 17 / PostgreSQL / Liquibase.
Считает обороты операций пользователей с конвертацией по курсам валют.

## Быстрый старт

1. Поднять БД (данные создадутся и засидятся автоматически при старте приложения):

```bash
docker compose up -d
```

2. Запустить приложение (Liquibase накатит схему и сгенерирует 1 000 000 операций с деталями за последние 8 дней; первый старт занимает ~20 секунд):

```bash
./mvnw spring-boot:run
```

3. Проверить, что всё работает — существующий эндпоинт дневного оборота:

```bash
curl "http://localhost:8080/turnover/daily/ee6c8623-02f5-438e-9c4d-24179da54307?coin=USD"
```

Готовые запросы лежат в [.api/test-requests.http](.api/test-requests.http).

Подключение к БД: `jdbc:postgresql://localhost:5430/bitruby`, `user` / `password`.
Консоль БД одной командой (для контрольных SQL из заданий):

```bash
docker exec -it bitruby-test-postgres psql -U user -d bitruby
```

## Модель данных

```sql
-- доступные валюты системы
CREATE TABLE supported_currencies
(
    id   UUID PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,        -- 'USD', 'BTC', ...
    type VARCHAR(10) NOT NULL                -- 'FIAT' | 'CRYPTO'
);

-- курсы валют: FIAT выражен в USD, CRYPTO — в USDT
CREATE TABLE currencies_rate
(
    id          UUID PRIMARY KEY,
    currency_id VARCHAR(10) NOT NULL UNIQUE REFERENCES supported_currencies (code),
    rate        DECIMAL(38, 18) NOT NULL
);

-- история операций пользователей;
-- amount — сумма сделки в натуральном выражении (сделка в XRP — количество XRP);
-- currency всегда присутствует в supported_currencies(code)
CREATE TABLE user_operations_turnover
(
    operation_id UUID PRIMARY KEY,
    currency     VARCHAR(10) NOT NULL,
    subject      VARCHAR(10) NOT NULL,
    user_id      UUID NOT NULL,
    amount       DECIMAL(38, 18) NOT NULL,
    executed_at  TIMESTAMP
);

-- детали операции, 1:1 к user_operations_turnover
CREATE TABLE user_operation_details
(
    id           UUID PRIMARY KEY,
    operation_id UUID NOT NULL UNIQUE REFERENCES user_operations_turnover (operation_id),
    fee          DECIMAL(38, 18) NOT NULL,
    tx_hash      VARCHAR(64) NOT NULL,
    created_at   TIMESTAMP NOT NULL
);
```

Про курсы:

1. Курс валют типа `FIAT` выражен в USD (например, 1 USD = 90 RUB).
2. Курс валют типа `CRYPTO` выражен в USDT (например, 1 BTC = 62869.94 USDT).
3. Принимаем, что USD = USDT. Оба присутствуют в таблице курсов с rate = 1 для удобства.

Про данные:

1. В таблице операций два пользователя:
    - `5c6569b5-57e8-45ab-a72f-ff7affbd874e`
    - `ee6c8623-02f5-438e-9c4d-24179da54307`
2. Данные сгенерированы на интервале «сейчас минус 8 дней … сейчас», всего 1 000 000 записей.

## Структура проекта

Сервис устроен по гексагональной архитектуре (ports & adapters):

```text
dev.bitruby.testproject
├── domain/                        — доменные модели (Operation, CurrencyRate)
├── application/
│   ├── port/in/                   — входные порты (GetTurnoverUseCase)
│   ├── port/out/                  — выходные порты (OperationPort, CurrencyRatePort)
│   └── service/                   — реализация use case (TurnoverService)
└── adapter/
    ├── in/web/                    — REST-контроллер и DTO
    └── out/persistence/           — JPA-сущности, репозитории, адаптер портов
```

---

## Задания


### Задание 1. Недельный оборот (разминка)

В сервисе уже есть расчёт дневного оборота (`GET /turnover/daily/{user-id}?coin=`).
Нужно добавить расчёт недельного оборота.

Требования:

- Группа `/turnover`, метод `GET`, эндпоинт `/week/{user-id}`.
- Неделя = «сейчас минус 7 * 24 часа».
- Обязательный path-параметр `user-id`, обязательный query-параметр `coin` — валюта,
  в которой нужно выразить оборот (если прислали `RUB` — сумма всех операций в рублях).
- Ответ `200` и объект с одним полем `result` — число в валюте `coin`:

```json
{
  "result": 134543.23543
}
```

- Если `coin` не существует в системе — ответ `400` (не `500`) с понятным телом ошибки.
- Если операций за период нет — `200` и `result: 0`.

**Проверка.** HTTP-запросы:

```bash
# основной сценарий
curl "http://localhost:8080/turnover/week/ee6c8623-02f5-438e-9c4d-24179da54307?coin=BTC"
```

```bash
# неизвестная валюта — ожидается 400
curl -i "http://localhost:8080/turnover/week/ee6c8623-02f5-438e-9c4d-24179da54307?coin=XXX"
```

Контрольный SQL — число должно совпасть с полем `result` из ответа:

```sql
WITH target AS (
    SELECT rate FROM currencies_rate WHERE currency_id = 'BTC' -- целевая валюта (coin)
)
SELECT SUM(t.amount * cr.rate) / target.rate AS turnover
FROM user_operations_turnover t
JOIN currencies_rate cr ON cr.currency_id = t.currency
CROSS JOIN target
WHERE t.user_id = 'ee6c8623-02f5-438e-9c4d-24179da54307'
  AND t.executed_at >= now() - INTERVAL '7 days'
GROUP BY target.rate;
```

Нюанс сверки: данные распределены по времени непрерывно, а окно привязано к «сейчас» —
выполняйте HTTP-запрос и SQL сразу друг за другом, иначе граница окна успеет сместиться
и последние значащие цифры разойдутся.

### Задание 2. Прод-инцидент: деградация производительности (основное)

Вводная: в проде таблица `user_operations_turnover` выросла до 10+ млн строк (локально — 1 млн).
Мониторинг показывает: p99 у `/turnover/daily` — секунды, память сервиса растёт с каждым запросом,
инстансы периодически падают по OOM.

Нужно найти и устранить **минимум три** причины деградации, не меняя контракт API.

Правила:

- Данные в БД менять нельзя; добавлять новые объекты БД (например, индексы) — можно,
  миграциями Liquibase.
- Результат расчёта должен остаться корректным (сверяйтесь с контрольным SQL).

Подсказка: включите `spring.jpa.show-sql: true` и посмотрите, какие SQL-запросы реально
уходят в базу и сколько строк они возвращают.

**Проверка.** HTTP-запрос (до и после оптимизации — число не должно измениться):

```bash
curl "http://localhost:8080/turnover/daily/ee6c8623-02f5-438e-9c4d-24179da54307?coin=USD"
```

Нагрузочная проверка — [scripts/load-test.sh](scripts/load-test.sh) параллельно шлёт запросы
разными пользователями и валютами и печатает перцентили времени ответа:

```bash
./scripts/load-test.sh                                # 100 запросов, 10 параллельно
REQUESTS=200 CONCURRENCY=20 ./scripts/load-test.sh    # пожёстче
ENDPOINTS="daily week" ./scripts/load-test.sh         # после задания 1 — оба эндпоинта
```

Пример вывода:

```text
Load test: 200 requests, concurrency=20, endpoints: daily

OK: 200  errors(non-200): 0  wall time: 6s  throughput: ~33.3 req/s

  min         333 ms
  avg         557 ms
  p50         454 ms
  p90         863 ms
  p95        1178 ms
  p99        1242 ms
  max        1417 ms
```

Ориентир: это перцентили исходной реализации на референсной машине. После правильного
набора исправлений p50/p99 должны упасть примерно на порядок — прогоните скрипт до и
после и сравните.

Контрольный SQL — тот же, что в задании 1, только с `INTERVAL '1 days'` и валютой `USD`:

```sql
WITH target AS (
    SELECT rate FROM currencies_rate WHERE currency_id = 'USD' -- целевая валюта (coin)
)
SELECT SUM(t.amount * cr.rate) / target.rate AS turnover
FROM user_operations_turnover t
JOIN currencies_rate cr ON cr.currency_id = t.currency
CROSS JOIN target
WHERE t.user_id = 'ee6c8623-02f5-438e-9c4d-24179da54307'
  AND t.executed_at >= now() - INTERVAL '1 days'
GROUP BY target.rate;
```

### Задание 3*. Пиковое 24-часовое окно (со звёздочкой)

Комплаенс просит находить всплески активности: для пользователя нужно найти **скользящее
24-часовое окно** (не календарные сутки) с максимальным суммарным оборотом за последние 7 дней.

Требования:

- `GET /turnover/peak/{user-id}?coin=` — оборот в валюте `coin`, как в остальных эндпоинтах.
- Ответ `200`:

```json
{
  "windowStart": "2026-07-14T10:23:11",
  "windowEnd": "2026-07-15T10:23:11",
  "result": 1234567.89
}
```

- `windowStart` — время операции, с которой начинается лучшее окно; `windowEnd` = `windowStart` + 24 часа.
- Ожидаемая сложность решения — лучше, чем перебор всех пар операций (O(n²)).

**Проверка.** HTTP-запрос:

```bash
curl "http://localhost:8080/turnover/peak/ee6c8623-02f5-438e-9c4d-24179da54307?coin=USD"
```

Нагрузочная проверка — тем же скриптом:

```bash
ENDPOINTS="peak" ./scripts/load-test.sh                # только peak
ENDPOINTS="daily week peak" REQUESTS=200 CONCURRENCY=20 ./scripts/load-test.sh   # все эндпоинты разом
```

Ориентир: peak работает с 7-дневным окном (сотни тысяч операций на пользователя), поэтому
будет медленнее daily — это нормально. Важно, что время ответа стабильно: решение сложности
O(n log n) отвечает за секунды даже под нагрузкой, а квадратичный перебор пар на таком объёме
не ответит за разумное время вовсе.

Контрольный SQL — `window_start` и `turnover_usd` должны совпасть с `windowStart` и `result`:

```sql
WITH ops AS (
    SELECT t.executed_at, t.amount * cr.rate AS amount_usd
    FROM user_operations_turnover t
    JOIN currencies_rate cr ON cr.currency_id = t.currency
    WHERE t.user_id = 'ee6c8623-02f5-438e-9c4d-24179da54307'
      AND t.executed_at >= now() - INTERVAL '7 days'
)
SELECT executed_at AS window_start,
       SUM(amount_usd) OVER (
           ORDER BY executed_at
           RANGE BETWEEN CURRENT ROW AND INTERVAL '24 hours' FOLLOWING
       ) AS turnover_usd
FROM ops
ORDER BY turnover_usd DESC
LIMIT 1;
```

---

## Что мы смотрим

- Работоспособность: код собирается, запускается, эндпоинты возвращают корректные числа.
- Владение Spring Boot / JPA / Stream API и понимание того, что происходит под капотом.
- Аккуратность с деньгами: `BigDecimal`, округления, точность.
- Умение рассуждать вслух: почему проблема именно здесь и почему выбранное решение — лучшее из простых.
