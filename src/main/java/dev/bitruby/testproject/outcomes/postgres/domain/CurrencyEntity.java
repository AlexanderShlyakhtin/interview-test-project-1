package dev.bitruby.testproject.outcomes.postgres.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "supported_currencies")
public class CurrencyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID id;

  @Column(name = "CODE", unique = true, nullable = false)
  private String code;

  @Column(name = "IS_ACTIVE", nullable = false)
  private Boolean isActive;

}
