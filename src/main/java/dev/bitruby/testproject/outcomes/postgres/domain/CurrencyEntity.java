package dev.bitruby.testproject.outcomes.postgres.domain;

import dev.bitruby.testproject.outcomes.postgres.domain.constants.CurrencyTypeEnum;
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

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private CurrencyTypeEnum type;

}
