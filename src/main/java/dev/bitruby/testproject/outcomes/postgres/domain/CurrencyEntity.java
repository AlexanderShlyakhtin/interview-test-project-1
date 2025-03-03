package dev.bitruby.testproject.outcomes.postgres.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "supported_currencies")
public class CurrencyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID id;

  @Column(name = "code", unique = true, nullable = false)
  private String code;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "min_deposit_amount", nullable = false, scale = 18, precision = 38)
  private BigDecimal minDepositAmount;

  @Column(name = "min_withdraw_amount", nullable = false, scale = 18, precision = 38)
  private BigDecimal minWithdrawAmount;

  @Column(name = "min_exchange_amount", nullable = false, scale = 18, precision = 38)
  private BigDecimal maxExchangeAmount;

}
