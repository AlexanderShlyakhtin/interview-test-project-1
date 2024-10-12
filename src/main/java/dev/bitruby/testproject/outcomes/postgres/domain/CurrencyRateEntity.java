package dev.bitruby.testproject.outcomes.postgres.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "currencies_rate")
public class CurrencyRateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne
  @JoinColumn(name = "currency_id", nullable = false, referencedColumnName = "code")
  private CurrencyEntity currency;

  @Column(name = "rate", nullable = false, scale = 10, precision = 4)
  private BigDecimal rate;

}
