1. After Liquibase run this script to generate test data

```sql
CREATE OR REPLACE FUNCTION generate_user_operations_turnover_records()
    RETURNS void AS $$
DECLARE
    rec_currency RECORD;
    rec_type RECORD;
    currency_code TEXT;
    currency_type TEXT;
    random_amount NUMERIC;
    random_date TIMESTAMP;
    random_user_id UUID;
    i INTEGER;
BEGIN
    FOR i IN 1..100000 LOOP
            SELECT code, type INTO rec_currency
            FROM supported_currencies
            ORDER BY random()
            LIMIT 1;
            currency_code := rec_currency.code;
            currency_type := rec_currency.type;
            IF currency_type = 'FIAT' THEN
                random_amount := round(CAST(random() * 100000 AS numeric), 2);
            ELSE
                random_amount := round(CAST(random() * 100000 AS numeric), 8);
            END IF;
            random_date := NOW() - (random() * INTERVAL '8 days');
            random_user_id := CASE WHEN random() < 0.5
                                       THEN 'ee6c8623-02f5-438e-9c4d-24179da54307'::UUID
                                   ELSE '5c6569b5-57e8-45ab-a72f-ff7affbd874e'::UUID
                END;
            INSERT INTO user_operations_turnover (operation_id, currency, user_id, subject, amount, executed_at)
            VALUES (
                       gen_random_uuid(),           
                       currency_code,               
                       random_user_id,              
                       currency_type,               
                       random_amount,               
                       random_date                  
                   );
        END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT generate_user_operations_turnover_records();
```

2. Информация по БД:
![currencies_rate.png](currencies_rate.png)

Таблица supported_currencies хранит доступные валюты в системе

Таблица currencies_rate хранит курсы валют для с ссылкой на таблицу supported_currencies(code), где:
1) Курс валют type FIAT выражен в USD
2) Курс валют type CRYPTO выражен в USDT
3) По умолчанию, что USD = USDT

Таблица user_operations_turnover хранит историю операций пользователей, причем currency это supported_currencies(code).
В таблице существуют два пользователя Существует в БД для пользователя ee6c8623-02f5-438e-9c4d-24179da54307, 5c6569b5-57e8-45ab-a72f-ff7affbd874e.
Данные сгенерированы для интервала от сейчас до 8 дней минус сейчас. Всего 100 000 записей

3. Tasks:
 1) Сделать два контроллера для расчета дневного и недельного объема операций пользователя в валюте
    Требования:
        - Входит в группу /turnover  
        - Возвращает 200 статус код  
        - GET метод с энпоинтом /daily и /week
        - /daily оборот - это сейчас минус 24 часа, и /week - это оборот за сейчас минус 7*24 часа
        - Принимает обязательный path параметр (user-id) query параметр  
        - Принимает обязательный query параметр (coin) который определяет валюту в который необходимо рассчитать объем операций
        - Ответ необходимо отправить объект с одним полем result в качестве числа выраженного в валюте, которая была прислала (coin).


Например:

Запрос GET /turnover/daily/ee6c8623-02f5-438e-9c4d-24179da54307?coin=BTC 

Ответ: 

```json
{
  "result": 134543.23543
}
```

4. SQL чтобы проверить результаты:

```sql
WITH target_currency_rate AS (
    SELECT cr.currency_id, cr.rate
    FROM currencies_rate cr
    WHERE cr.currency_id = 'USD' -- Указать целевую валюту (например, BTC)
)
SELECT tcr.currency_id, agr.total/tcr.rate as turnover FROM (SELECT SUM(sum * cr.rate) as total FROM (SELECT currency, Sum(amount) as sum
FROM user_operations_turnover
WHERE user_id = 'ee6c8623-02f5-438e-9c4d-24179da54307'
  AND executed_at >= now() - INTERVAL '1 days'
GROUP BY currency) tmp
LEFT JOIN currencies_rate cr ON tmp.currency = cr.currency_id) agr
CROSS JOIN target_currency_rate tcr;

WITH target_currency_rate AS (
    SELECT cr.currency_id, cr.rate
    FROM currencies_rate cr
    WHERE cr.currency_id = 'USD' -- Указать целевую валюту (например, BTC)
)
SELECT tcr.currency_id, agr.total/tcr.rate as turnover FROM (SELECT SUM(sum * cr.rate) as total FROM (SELECT currency, Sum(amount) as sum
FROM user_operations_turnover
WHERE user_id = 'ee6c8623-02f5-438e-9c4d-24179da54307'
  AND executed_at >= now() - INTERVAL '7 days'
GROUP BY currency) tmp
LEFT JOIN currencies_rate cr ON tmp.currency = cr.currency_id) agr
CROSS JOIN target_currency_rate tcr;

WITH target_currency_rate AS (
    SELECT cr.currency_id, cr.rate
    FROM currencies_rate cr
    WHERE cr.currency_id = 'USD' -- Указать целевую валюту (например, BTC)
)
SELECT tcr.currency_id, agr.total/tcr.rate as turnover FROM (SELECT SUM(sum * cr.rate) as total FROM (SELECT currency, Sum(amount) as sum
FROM user_operations_turnover
WHERE user_id = 'ee6c8623-02f5-438e-9c4d-24179da54307'
GROUP BY currency) tmp
LEFT JOIN currencies_rate cr ON tmp.currency = cr.currency_id) agr
CROSS JOIN target_currency_rate tcr;
```
