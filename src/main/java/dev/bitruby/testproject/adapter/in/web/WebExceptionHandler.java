package dev.bitruby.testproject.adapter.in.web;

import dev.bitruby.testproject.application.exception.CurrencyNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebExceptionHandler {

  @ExceptionHandler(CurrencyNotFoundException.class)
  public ResponseEntity<ErrorDto> handleCurrencyNotFound(CurrencyNotFoundException exception) {
    return ResponseEntity.badRequest().body(new ErrorDto(exception.getMessage()));
  }

  public record ErrorDto(String error) {
  }
}
