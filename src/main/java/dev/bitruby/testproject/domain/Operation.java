package dev.bitruby.testproject.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Operation(String currencyCode, BigDecimal amount, LocalDateTime executedAt) {
}
