--liquibase formatted sql

--changeset test:1
CREATE TABLE user_operations_turnover
(
    operation_id UUID PRIMARY KEY,
    currency VARCHAR(10) NOT NULL,
    subject VARCHAR(10) NOT NULL,
    user_id UUID NOT NULL,
    amount DECIMAL(38,18) NOT NULL,
    executed_at TIMESTAMP
);
--changeset test:2
CREATE TABLE supported_currencies
(
    id UUID PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL DEFAULT false,
    type VARCHAR(10) NOT NULL,
    min_deposit_amount DECIMAL(38,18) NOT NULL DEFAULT 0,
    min_withdraw_amount DECIMAL(38,18) NOT NULL DEFAULT 0,
    min_exchange_amount DECIMAL(38,18) NOT NULL DEFAULT 0
);
--changeset test:3
CREATE TABLE currencies_rate
(
    id UUID PRIMARY KEY,
    currency_id VARCHAR(10) NOT NULL UNIQUE REFERENCES supported_currencies(code),
    rate DECIMAL(38,18) NOT NULL
);

--changeset test:4
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233151', 'KGS', true, 'FIAT');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233152', 'KZT', true, 'FIAT');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233153', 'RUB', true, 'FIAT');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233154', 'EUR', true, 'FIAT');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233155', 'USD', true, 'FIAT');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233156', 'AED', true, 'FIAT');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d14', 'NEAR', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d2b', 'XRP', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d9b', 'TON', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d09', 'APEX', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d7b', 'ATOM', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233deb', 'SOL', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233ded', 'ETH', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233dea', 'TRX', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d1b', 'MATIC', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d6b', 'SHIB', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d15', 'SUI', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233def', 'BTC', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d12', 'DOGE', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d13', 'NOT', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d3b', 'MNT', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d8b', 'AVAX', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d17', 'USDE', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d11', 'STETH', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d16', 'LINK', true, 'CRYPTO');
INSERT INTO supported_currencies (id, code, is_active, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d22', 'USDT', true, 'CRYPTO');

--changeset test:5
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233151', 'KGS', 87.45);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233152', 'KZT', 498.21);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233153', 'RUB', 90);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233154', 'EUR', 0.96);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233156', 'AED', 3.67);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d14', 'NEAR', 4.7804);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d2b', 'XRP', 0.5399);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d9b', 'TON', 5.275);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d09', 'APEX', 1.493);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d7b', 'ATOM', 4.35);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233deb', 'SOL', 145.83);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233ded', 'ETH', 2445.85);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233dea', 'TRX', 0.16383);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d1b', 'MATIC', 2.67);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d6b', 'SHIB', 0.0000179);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d15', 'SUI', 2.0585);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233def', 'BTC', 62869.94);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d12', 'DOGE', 0.11129);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d13', 'NOT', 0.007957);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d3b', 'MNT', 0.6049);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d8b', 'AVAX', 28.414);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d17', 'USDE', 0.9993);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d11', 'STETH', 2444.51);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d16', 'LINK', 11.0383);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d22', 'USDT', 1);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233155', 'USD', 1);

