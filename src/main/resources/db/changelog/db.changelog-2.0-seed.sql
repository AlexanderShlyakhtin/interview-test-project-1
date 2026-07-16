--liquibase formatted sql

--changeset test:7
INSERT INTO user_operations_turnover (operation_id, currency, subject, user_id, amount, executed_at)
SELECT gen_random_uuid(),
       c.code,
       c.type,
       CASE WHEN random() < 0.5
                THEN 'ee6c8623-02f5-438e-9c4d-24179da54307'::uuid
            ELSE '5c6569b5-57e8-45ab-a72f-ff7affbd874e'::uuid END,
       CASE WHEN c.type = 'FIAT'
                THEN round((random() * 100000)::numeric, 2)
            ELSE round((random() * 100000)::numeric, 8) END,
       now() - (random() * INTERVAL '8 days')
FROM (SELECT g,
             1 + floor(random() * (SELECT count(*) FROM supported_currencies))::int AS pick
      FROM generate_series(1, 1000000) g) gs
JOIN (SELECT code, type, row_number() OVER (ORDER BY code) AS rn
      FROM supported_currencies) c ON c.rn = gs.pick;

--changeset test:8
INSERT INTO user_operation_details (id, operation_id, fee, tx_hash, created_at)
SELECT gen_random_uuid(),
       o.operation_id,
       round((o.amount * 0.001)::numeric, 8),
       md5(o.operation_id::text),
       o.executed_at
FROM user_operations_turnover o;
