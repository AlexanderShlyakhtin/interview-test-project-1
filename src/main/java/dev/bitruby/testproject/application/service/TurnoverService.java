package dev.bitruby.testproject.application.service;

import dev.bitruby.testproject.application.port.in.GetTurnoverUseCase;
import dev.bitruby.testproject.application.port.out.CurrencyRatePort;
import dev.bitruby.testproject.application.port.out.OperationPort;
import dev.bitruby.testproject.domain.CurrencyRate;
import dev.bitruby.testproject.domain.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurnoverService implements GetTurnoverUseCase {

  private final OperationPort operationPort;
  private final CurrencyRatePort currencyRatePort;

  @Override
  public BigDecimal getDailyTurnover(UUID userId, String coin) {
    List<CurrencyRate> rates = currencyRatePort.findAllRates();
    Map<String, BigDecimal> collectedRates = rates.stream()
        .collect(Collectors.toMap(CurrencyRate::currencyCode, CurrencyRate::rate));
    CurrencyRate targetCoinRate = rates.stream()
        .filter(rate -> rate.currencyCode().equals(coin)).findAny()
        .orElseThrow(() -> new RuntimeException("Target currency not found"));
    return operationPort.findOperations(userId, LocalDateTime.now().minusDays(1L)).stream()
        .collect(Collectors.groupingBy(Operation::currencyCode,
            Collectors.reducing(BigDecimal.ZERO, Operation::amount, BigDecimal::add)))
        .entrySet().stream()
        .map(entry -> entry.getValue().multiply(collectedRates.get(entry.getKey())))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .divide(targetCoinRate.rate(), RoundingMode.CEILING)
        .setScale(10, RoundingMode.CEILING);
  }
}
