package dev.bitruby.testproject.incomes.rest.controllers;

import dev.bitruby.testproject.core.InfoCurrencyService;
import dev.bitruby.testproject.incomes.rest.dto.CoinInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class InfoCurrencyController {

  private final InfoCurrencyService infoCurrencyService;

  @PostMapping("/api/info")
  public ResponseEntity<String> updateInfoCurrencies(@RequestBody Object request) {
    infoCurrencyService.updateInfoCurrencies( (CoinInfoRequest) request);
    return ResponseEntity.ok("OK");
  }
}
