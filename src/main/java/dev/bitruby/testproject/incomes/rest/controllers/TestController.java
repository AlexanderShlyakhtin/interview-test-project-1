package dev.bitruby.testproject.incomes.rest.controllers;

import dev.bitruby.testproject.core.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/turnover")
@RequiredArgsConstructor
public class TestController {

  private final TestService testService;

  @GetMapping("/daily/{user-id}")
  public ResponseEntity<ResultDto> getDailyLimit(@PathVariable("user-id") UUID userId, @RequestParam("coin")String coin) {
    return new ResponseEntity<>(testService.getDailyLimit(userId, coin), HttpStatus.OK);
  }

  @GetMapping("/weekly/{user-id}")
  public ResponseEntity<ResultDto> getWeeklyLimit(@PathVariable("user-id") UUID userId, @RequestParam("coin")String coin) {
    return new ResponseEntity<>(testService.getWeeklyLimit(userId, coin), HttpStatus.OK);
  }

  @GetMapping("/{user-id}")
  public ResponseEntity<ResultDto> getLimit(@PathVariable("user-id") UUID userId, @RequestParam("coin")String coin) {
    return new ResponseEntity<>(testService.getLimit(userId, coin), HttpStatus.OK);
  }
}
