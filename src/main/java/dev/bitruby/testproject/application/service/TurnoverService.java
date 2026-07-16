package dev.bitruby.testproject.application.service;

import dev.bitruby.testproject.application.exception.CurrencyNotFoundException;
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

  private static final int RESULT_SCALE = 10;

  private final OperationPort operationPort;
  private final CurrencyRatePort currencyRatePort;

  @Override
  public BigDecimal getDailyTurnover(UUID userId, String coin) {
    return getTurnover(userId, coin, 1);
  }

  @Override
  public BigDecimal getWeeklyTurnover(UUID userId, String coin) {
    return getTurnover(userId, coin, 7);
  }

  private BigDecimal getTurnover(UUID userId, String coin, int days) {
    List<CurrencyRate> rates = currencyRatePort.findAllRates();
    Map<String, BigDecimal> rateByCode = rates.stream()
        .collect(Collectors.toMap(CurrencyRate::currencyCode, CurrencyRate::rate));
    BigDecimal targetRate = rateByCode.get(coin);
    if (targetRate == null) {
      throw new CurrencyNotFoundException(coin);
    }
    return operationPort.findOperations(userId, LocalDateTime.now().minusDays(days)).stream()
        .collect(Collectors.groupingBy(Operation::currencyCode,
            Collectors.reducing(BigDecimal.ZERO, Operation::amount, BigDecimal::add)))
        .entrySet().stream()
        .map(entry -> entry.getValue().multiply(rateByCode.get(entry.getKey())))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .divide(targetRate, RoundingMode.CEILING)
        .setScale(RESULT_SCALE, RoundingMode.CEILING);
  }
}
