package dev.bitruby.testproject.application.exception;

public class CurrencyNotFoundException extends RuntimeException {

  public CurrencyNotFoundException(String coin) {
    super("Currency not supported: " + coin);
  }
}
