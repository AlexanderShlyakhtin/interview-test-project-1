package dev.bitruby.testproject.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_operation_details")
public class OperationDetailEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "operation_id", nullable = false, unique = true)
  private UserOperationTurnoverEntity operation;

  @Column(name = "fee", nullable = false, scale = 18, precision = 38)
  private BigDecimal fee;

  @Column(name = "tx_hash", nullable = false)
  private String txHash;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
}
