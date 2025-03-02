package dev.bitruby.testproject.incomes.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class CoinInfoRequest {

  private List<CoinInfoListItem> coinList;

}
