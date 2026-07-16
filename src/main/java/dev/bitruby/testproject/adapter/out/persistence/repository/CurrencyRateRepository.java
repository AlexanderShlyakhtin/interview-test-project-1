package dev.bitruby.testproject.adapter.out.persistence.repository;

import dev.bitruby.testproject.adapter.out.persistence.entity.CurrencyRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRateEntity, UUID> {

  @Query("select r from CurrencyRateEntity r join fetch r.currency")
  List<CurrencyRateEntity> findAllWithCurrency();
}
