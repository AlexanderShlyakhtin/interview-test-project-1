package dev.bitruby.testproject.application.port.out;

import dev.bitruby.testproject.domain.OperationAmount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OperationPort {

  Map<String, BigDecimal> sumTurnoverByCurrency(UUID userId, LocalDateTime from);

  /** Operations of the user since {@code from}, converted to USD, ordered by execution time. */
  List<OperationAmount> findOperationAmountsInUsd(UUID userId, LocalDateTime from);
}
