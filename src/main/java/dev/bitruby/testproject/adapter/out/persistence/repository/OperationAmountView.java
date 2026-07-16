package dev.bitruby.testproject.adapter.out.persistence.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface OperationAmountView {

  Timestamp getExecutedAt();

  BigDecimal getAmountUsd();
}
