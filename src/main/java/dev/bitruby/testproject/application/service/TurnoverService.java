package dev.bitruby.testproject.application.service;

import dev.bitruby.testproject.application.exception.CurrencyNotFoundException;
import dev.bitruby.testproject.application.port.in.GetTurnoverUseCase;
import dev.bitruby.testproject.application.port.out.CurrencyRatePort;
import dev.bitruby.testproject.application.port.out.OperationPort;
import dev.bitruby.testproject.domain.CurrencyRate;
import dev.bitruby.testproject.domain.OperationAmount;
import dev.bitruby.testproject.domain.PeakWindow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurnoverService implements GetTurnoverUseCase {

  private static final int RESULT_SCALE = 10;
  private static final Duration PEAK_WINDOW = Duration.ofHours(24);
  private static final int PEAK_PERIOD_DAYS = 7;

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

  @Override
  public PeakWindow getPeakTurnoverWindow(UUID userId, String coin) {
    BigDecimal targetRate = requireRate(coin);
    List<OperationAmount> operations =
        operationPort.findOperationAmountsInUsd(userId, LocalDateTime.now().minusDays(PEAK_PERIOD_DAYS));
    if (operations.isEmpty()) {
      return new PeakWindow(null, null, BigDecimal.ZERO.setScale(RESULT_SCALE, RoundingMode.UNNECESSARY));
    }

    // Sliding window over operations sorted by executedAt: O(n), each operation
    // enters and leaves the window exactly once.
    BigDecimal windowSum = BigDecimal.ZERO;
    BigDecimal bestSum = null;
    LocalDateTime bestStart = null;
    int right = 0;
    for (int left = 0; left < operations.size(); left++) {
      if (left > 0) {
        windowSum = windowSum.subtract(operations.get(left - 1).amountUsd());
      }
      LocalDateTime windowEnd = operations.get(left).executedAt().plus(PEAK_WINDOW);
      while (right < operations.size()
          && !operations.get(right).executedAt().isAfter(windowEnd)) {
        windowSum = windowSum.add(operations.get(right).amountUsd());
        right++;
      }
      if (bestSum == null || windowSum.compareTo(bestSum) > 0) {
        bestSum = windowSum;
        bestStart = operations.get(left).executedAt();
      }
    }
    BigDecimal result = bestSum.divide(targetRate, RoundingMode.CEILING)
        .setScale(RESULT_SCALE, RoundingMode.CEILING);
    return new PeakWindow(bestStart, bestStart.plus(PEAK_WINDOW), result);
  }

  private BigDecimal getTurnover(UUID userId, String coin, int days) {
    Map<String, BigDecimal> rateByCode = loadRates();
    BigDecimal targetRate = rateByCode.get(coin);
    if (targetRate == null) {
      throw new CurrencyNotFoundException(coin);
    }
    return operationPort.sumTurnoverByCurrency(userId, LocalDateTime.now().minusDays(days))
        .entrySet().stream()
        .map(entry -> entry.getValue().multiply(rateByCode.get(entry.getKey())))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .divide(targetRate, RoundingMode.CEILING)
        .setScale(RESULT_SCALE, RoundingMode.CEILING);
  }

  private Map<String, BigDecimal> loadRates() {
    return currencyRatePort.findAllRates().stream()
        .collect(Collectors.toMap(CurrencyRate::currencyCode, CurrencyRate::rate));
  }

  private BigDecimal requireRate(String coin) {
    BigDecimal rate = loadRates().get(coin);
    if (rate == null) {
      throw new CurrencyNotFoundException(coin);
    }
    return rate;
  }
}
