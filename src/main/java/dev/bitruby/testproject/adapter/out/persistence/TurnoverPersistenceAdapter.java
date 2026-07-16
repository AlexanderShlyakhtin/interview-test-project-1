package dev.bitruby.testproject.adapter.out.persistence;

import dev.bitruby.testproject.adapter.out.persistence.repository.CurrencyRateRepository;
import dev.bitruby.testproject.adapter.out.persistence.repository.UserOperationTurnoverRepository;
import dev.bitruby.testproject.application.port.out.CurrencyRatePort;
import dev.bitruby.testproject.application.port.out.OperationPort;
import dev.bitruby.testproject.domain.CurrencyRate;
import dev.bitruby.testproject.domain.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TurnoverPersistenceAdapter implements OperationPort, CurrencyRatePort {

  private final UserOperationTurnoverRepository turnoverRepository;
  private final CurrencyRateRepository currencyRateRepository;

  @Override
  public List<Operation> findOperations(UUID userId, LocalDateTime from) {
    return turnoverRepository.findByUserIdAndExecutedAtGreaterThanEqual(userId, from).stream()
        .map(entity -> new Operation(entity.getCurrency().getCode(), entity.getAmount(),
            entity.getExecutedAt()))
        .toList();
  }

  @Override
  public List<CurrencyRate> findAllRates() {
    return currencyRateRepository.findAll().stream()
        .map(entity -> new CurrencyRate(entity.getCurrency().getCode(), entity.getRate()))
        .toList();
  }
}
