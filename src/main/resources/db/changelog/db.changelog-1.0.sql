--liquibase formatted sql

--changeset test:1
CREATE TABLE user_operations_turnover
(
    operation_id UUID PRIMARY KEY,
    currency VARCHAR(10) NOT NULL,
    subject VARCHAR(10) NOT NULL,
    user_id UUID NOT NULL,
    amount DECIMAL(20,10) NOT NULL,
    executed_at TIMESTAMP

);
--changeset test:2
CREATE TABLE supported_currencies
(
    id UUID PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    type VARCHAR(10) NOT NULL
);
--changeset test:3
CREATE TABLE currencies_rate
(
    id UUID PRIMARY KEY,
    currency_id VARCHAR(10) NOT NULL UNIQUE REFERENCES supported_currencies(code),
    rate DECIMAL(20,10) NOT NULL
);
