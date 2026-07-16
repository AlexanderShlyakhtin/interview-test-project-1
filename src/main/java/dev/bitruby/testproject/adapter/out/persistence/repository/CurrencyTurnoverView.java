package dev.bitruby.testproject.adapter.out.persistence.repository;

import java.math.BigDecimal;

public interface CurrencyTurnoverView {

  String getCurrencyCode();

  BigDecimal getTotal();
}
