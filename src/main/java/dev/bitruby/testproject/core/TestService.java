package dev.bitruby.testproject.core;

import dev.bitruby.testproject.incomes.rest.controllers.ResultDto;
import dev.bitruby.testproject.outcomes.postgres.domain.CurrencyRateEntity;
import dev.bitruby.testproject.outcomes.postgres.domain.UserOperationTurnoverEntity;
import dev.bitruby.testproject.outcomes.postgres.repository.CurrencyRateRepository;
import dev.bitruby.testproject.outcomes.postgres.repository.UserOperationTurnoverRepository;
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
public class TestService {

  private final UserOperationTurnoverRepository turnoverRepository;
  private final CurrencyRateRepository currencyRateRepository;

  public ResultDto getDailyLimit(UUID userId, String coin) {
    List<CurrencyRateEntity> rateEntities = currencyRateRepository.findAll();
    Map<String, BigDecimal> collectedRates = rateEntities.stream().collect(
        Collectors.toMap(currencyRateEntity -> currencyRateEntity.getCurrency().getCode(),
            CurrencyRateEntity::getRate));
    CurrencyRateEntity targetCoinRateEntity =
        rateEntities.stream().filter(item -> item.getCurrency().getCode().equals(coin)).findAny()
            .orElseThrow(() -> new RuntimeException("Target currency not found"));
    BigDecimal result = turnoverRepository.findByUserIdAndExecutedAtGreaterThanEqual(userId,
            LocalDateTime.now().minusDays(1L)).stream().collect(
            Collectors.groupingBy(UserOperationTurnoverEntity::getCurrency,
                Collectors.reducing(BigDecimal.ZERO, UserOperationTurnoverEntity::getAmount,
                    BigDecimal::add))).entrySet().stream()
        .map(entry -> entry.getValue().multiply(collectedRates.get(entry.getKey())))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .divide(targetCoinRateEntity.getRate(), RoundingMode.CEILING)
        .setScale(10, RoundingMode.CEILING);
    return new ResultDto(result);
  }

  public ResultDto getWeeklyLimit(UUID userId, String coin) {
    List<CurrencyRateEntity> rateEntities = currencyRateRepository.findAll();
    Map<String, BigDecimal> collectedRates = rateEntities.stream().collect(
        Collectors.toMap(currencyRateEntity -> currencyRateEntity.getCurrency().getCode(),
            CurrencyRateEntity::getRate));
    CurrencyRateEntity targetCoinRateEntity =
        rateEntities.stream().filter(item -> item.getCurrency().getCode().equals(coin)).findAny()
            .orElseThrow(() -> new RuntimeException("Target currency not found"));
    BigDecimal result = turnoverRepository.findByUserIdAndExecutedAtGreaterThanEqual(userId,
            LocalDateTime.now().minusDays(7L)).stream().collect(
            Collectors.groupingBy(UserOperationTurnoverEntity::getCurrency,
                Collectors.reducing(BigDecimal.ZERO, UserOperationTurnoverEntity::getAmount,
                    BigDecimal::add))).entrySet().stream()
        .map(entry -> entry.getValue().multiply(collectedRates.get(entry.getKey())))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .divide(targetCoinRateEntity.getRate(), RoundingMode.CEILING)
        .setScale(10, RoundingMode.CEILING);
    return new ResultDto(result);
  }

  public ResultDto getLimit(UUID userId, String coin) {
    List<CurrencyRateEntity> rateEntities = currencyRateRepository.findAll();
    Map<String, BigDecimal> collectedRates = rateEntities.stream().collect(
        Collectors.toMap(currencyRateEntity -> currencyRateEntity.getCurrency().getCode(),
            CurrencyRateEntity::getRate));
    CurrencyRateEntity targetCoinRateEntity =
        rateEntities.stream().filter(item -> item.getCurrency().getCode().equals(coin)).findAny()
            .orElseThrow(() -> new RuntimeException("Target currency not found"));
    BigDecimal result = turnoverRepository.findByUserId(userId).stream().collect(
            Collectors.groupingBy(UserOperationTurnoverEntity::getCurrency,
                Collectors.reducing(BigDecimal.ZERO, UserOperationTurnoverEntity::getAmount,
                    BigDecimal::add))).entrySet().stream()
        .map(entry -> entry.getValue().multiply(collectedRates.get(entry.getKey())))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .divide(targetCoinRateEntity.getRate(), RoundingMode.CEILING)
        .setScale(10, RoundingMode.CEILING);
    return new ResultDto(result);
  }
}
