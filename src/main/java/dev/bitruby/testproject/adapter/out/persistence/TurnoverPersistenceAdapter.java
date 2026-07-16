package dev.bitruby.testproject.adapter.out.persistence;

import dev.bitruby.testproject.adapter.out.persistence.repository.CurrencyRateRepository;
import dev.bitruby.testproject.adapter.out.persistence.repository.CurrencyTurnoverView;
import dev.bitruby.testproject.adapter.out.persistence.repository.UserOperationTurnoverRepository;
import dev.bitruby.testproject.application.port.out.CurrencyRatePort;
import dev.bitruby.testproject.application.port.out.OperationPort;
import dev.bitruby.testproject.domain.CurrencyRate;
import dev.bitruby.testproject.domain.OperationAmount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TurnoverPersistenceAdapter implements OperationPort, CurrencyRatePort {

  private final UserOperationTurnoverRepository turnoverRepository;
  private final CurrencyRateRepository currencyRateRepository;

  @Override
  public Map<String, BigDecimal> sumTurnoverByCurrency(UUID userId, LocalDateTime from) {
    return turnoverRepository.sumTurnoverByCurrency(userId, from).stream()
        .collect(Collectors.toMap(CurrencyTurnoverView::getCurrencyCode,
            CurrencyTurnoverView::getTotal));
  }

  @Override
  public List<OperationAmount> findOperationAmountsInUsd(UUID userId, LocalDateTime from) {
    return turnoverRepository.findOperationAmountsInUsd(userId, from).stream()
        .map(view -> new OperationAmount(view.getExecutedAt().toLocalDateTime(),
            view.getAmountUsd()))
        .toList();
  }

  @Override
  public List<CurrencyRate> findAllRates() {
    return currencyRateRepository.findAllWithCurrency().stream()
        .map(entity -> new CurrencyRate(entity.getCurrency().getCode(), entity.getRate()))
        .toList();
  }
}
