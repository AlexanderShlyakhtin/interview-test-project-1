--liquibase formatted sql

--changeset test:9
CREATE INDEX idx_user_operations_turnover_user_executed_at
    ON user_operations_turnover (user_id, executed_at);
