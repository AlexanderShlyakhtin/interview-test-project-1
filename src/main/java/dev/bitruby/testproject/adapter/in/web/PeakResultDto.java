package dev.bitruby.testproject.adapter.in.web;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PeakResultDto(LocalDateTime windowStart, LocalDateTime windowEnd,
                            BigDecimal result) {
}
