package dev.bitruby.testproject.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ResultDto implements Serializable {

  private BigDecimal result;
}
