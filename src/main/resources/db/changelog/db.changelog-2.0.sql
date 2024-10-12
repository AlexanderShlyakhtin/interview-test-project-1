--liquibase formatted sql

--changeset test:1
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233151', 'KGS', 'FIAT');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233152', 'KZT', 'FIAT');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233153', 'RUB', 'FIAT');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233154', 'EUR', 'FIAT');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233155', 'USD', 'FIAT');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233156', 'AED', 'FIAT');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d14', 'NEAR', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d2b', 'XRP', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d9b', 'TON', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d09', 'APEX', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d7b', 'ATOM', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233deb', 'SOL', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233ded', 'ETH', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233dea', 'TRX', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d1b', 'MATIC', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d6b', 'SHIB', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d15', 'SUI', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233def', 'BTC', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d12', 'DOGE', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d13', 'NOT', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d3b', 'MNT', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d8b', 'AVAX', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d17', 'USDE', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d11', 'STETH', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d16', 'LINK', 'CRYPTO');
INSERT INTO supported_currencies (id, code, type) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233d22', 'USDT', 'CRYPTO');

--changeset test:2
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233151', 'KGS', 85.5500);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233152', 'KZT', 100.0585);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233153', 'RUB', 3.8536);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233154', 'EUR', 0.918864);
INSERT INTO currencies_rate (id, currency_id, rate) VALUES ('d3c79efe-1a3d-4b56-819b-b67d09233156', 'AED', 495.2500);
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

