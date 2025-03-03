package dev.bitruby.testproject.outcomes.postgres.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_operations_turnover")
public class UserOperationTurnoverEntity {

  @Id
  @Column(name = "operation_id")
  private UUID operationId;
  
  @Column(name = "currency", nullable = false)
  private String currency; // For example, USD,EUR,BTC

  @Column(name = "subject", nullable = false)
  private String subject; // For example, FIAT min accuracy - 2, for CRYPTO - 8

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "amount", nullable = false, scale = 18, precision = 38)
  private BigDecimal amount;

  @Column(name = "executed_at", nullable = false)
  private LocalDateTime executedAt;
}

