package dev.bitruby.testproject.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PeakWindow(LocalDateTime windowStart, LocalDateTime windowEnd, BigDecimal result) {
}
