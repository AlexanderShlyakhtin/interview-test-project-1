package dev.bitruby.testproject.application.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface GetTurnoverUseCase {

  BigDecimal getDailyTurnover(UUID userId, String coin);

  BigDecimal getWeeklyTurnover(UUID userId, String coin);
}
