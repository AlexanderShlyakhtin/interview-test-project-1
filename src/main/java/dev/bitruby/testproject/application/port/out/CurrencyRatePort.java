package dev.bitruby.testproject.application.port.out;

import dev.bitruby.testproject.domain.CurrencyRate;

import java.util.List;

public interface CurrencyRatePort {

  List<CurrencyRate> findAllRates();
}
