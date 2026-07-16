package dev.bitruby.testproject.application.port.out;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public interface OperationPort {

  Map<String, BigDecimal> sumTurnoverByCurrency(UUID userId, LocalDateTime from);
}
