package dev.bitruby.testproject.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OperationAmount(LocalDateTime executedAt, BigDecimal amountUsd) {
}
