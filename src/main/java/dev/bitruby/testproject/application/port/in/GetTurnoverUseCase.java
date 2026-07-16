package dev.bitruby.testproject.application.port.in;

import dev.bitruby.testproject.domain.PeakWindow;

import java.math.BigDecimal;
import java.util.UUID;

public interface GetTurnoverUseCase {

  BigDecimal getDailyTurnover(UUID userId, String coin);

  BigDecimal getWeeklyTurnover(UUID userId, String coin);

  PeakWindow getPeakTurnoverWindow(UUID userId, String coin);
}
