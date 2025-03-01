package dev.bitruby.testproject.outcomes.postgres.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "currencies_rate")
public class CurrencyRateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "currency_id", nullable = false)
  private String currency;

  @Column(name = "rate", nullable = false, scale = 18, precision = 38)
  private BigDecimal rate;

}
