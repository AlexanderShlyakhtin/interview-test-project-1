package dev.bitruby.testproject.core;

import dev.bitruby.testproject.incomes.rest.dto.CoinInfoListItem;
import dev.bitruby.testproject.incomes.rest.dto.CoinInfoRequest;
import dev.bitruby.testproject.outcomes.postgres.domain.CurrencyEntity;
import dev.bitruby.testproject.outcomes.postgres.domain.CurrencyRateEntity;
import dev.bitruby.testproject.outcomes.postgres.repository.CurrencyRateRepository;
import dev.bitruby.testproject.outcomes.postgres.repository.CurrencyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InfoCurrencyService {

  private final CurrencyRepository currencyRepository;
  private final CurrencyRateRepository currencyRateRepository;

  public void updateInfoCurrencies(CoinInfoRequest request) {
    for (CoinInfoListItem currencyListItem : request.getCoinList()) {
      Optional<CurrencyEntity> byCode = currencyRepository.findByCode(currencyListItem.getCode());
      Optional<CurrencyRateEntity> byCurrency =
          currencyRateRepository.findByCurrency(currencyListItem.getCode());
      if (byCode.isPresent()) {
        CurrencyEntity currencyEntity = byCode.get();
        currencyEntity.setMinWithdrawAmount(currencyListItem.getMinWithdrawAmount());
        currencyEntity.setMinDepositAmount(currencyListItem.getMinDepositAmount());
        currencyEntity.setMaxExchangeAmount(currencyListItem.getMinExchangeAmount());
        currencyRepository.save(currencyEntity);
      }
      if(byCurrency.isPresent()) {
        CurrencyRateEntity currencyRateEntity = byCurrency.get();
        currencyRateEntity.setRate(currencyListItem.getRate());
        currencyRateRepository.save(currencyRateEntity);
      }
    }
  }
}
