package dev.bitruby.testproject.adapter.in.web;

import dev.bitruby.testproject.application.port.in.GetTurnoverUseCase;
import dev.bitruby.testproject.domain.PeakWindow;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/turnover")
@RequiredArgsConstructor
public class TurnoverController {

  private final GetTurnoverUseCase getTurnoverUseCase;

  @GetMapping("/daily/{user-id}")
  public ResponseEntity<ResultDto> getDailyTurnover(@PathVariable("user-id") UUID userId,
      @RequestParam("coin") String coin) {
    return new ResponseEntity<>(new ResultDto(getTurnoverUseCase.getDailyTurnover(userId, coin)),
        HttpStatus.OK);
  }

  @GetMapping("/week/{user-id}")
  public ResponseEntity<ResultDto> getWeeklyTurnover(@PathVariable("user-id") UUID userId,
      @RequestParam("coin") String coin) {
    return new ResponseEntity<>(new ResultDto(getTurnoverUseCase.getWeeklyTurnover(userId, coin)),
        HttpStatus.OK);
  }

  @GetMapping("/peak/{user-id}")
  public ResponseEntity<PeakResultDto> getPeakTurnoverWindow(
      @PathVariable("user-id") UUID userId, @RequestParam("coin") String coin) {
    PeakWindow peak = getTurnoverUseCase.getPeakTurnoverWindow(userId, coin);
    return new ResponseEntity<>(
        new PeakResultDto(peak.windowStart(), peak.windowEnd(), peak.result()), HttpStatus.OK);
  }
}
