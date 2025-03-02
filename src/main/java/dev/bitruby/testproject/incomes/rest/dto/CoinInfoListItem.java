package dev.bitruby.testproject.incomes.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@Setter
public class CoinInfoListItem {
  private String code;
  private BigDecimal rate;
  private BigDecimal minDepositAmount;
  private BigDecimal minWithdrawAmount;
  private BigDecimal minExchangeAmount;
  private BigDecimal maxExchangeAmount;
}
