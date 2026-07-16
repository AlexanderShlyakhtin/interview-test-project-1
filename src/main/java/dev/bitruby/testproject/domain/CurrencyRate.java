package dev.bitruby.testproject.domain;

import java.math.BigDecimal;

public record CurrencyRate(String currencyCode, BigDecimal rate) {
}
