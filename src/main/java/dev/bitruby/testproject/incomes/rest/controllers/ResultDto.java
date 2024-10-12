package dev.bitruby.testproject.incomes.rest.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ResultDto implements Serializable {

  private BigDecimal result;
}
