package dev.bitruby.testproject.adapter.out.persistence.repository;

import dev.bitruby.testproject.adapter.out.persistence.entity.UserOperationTurnoverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserOperationTurnoverRepository
    extends JpaRepository<UserOperationTurnoverEntity, UUID> {

  @Query("select t.currency.code as currencyCode, sum(t.amount) as total "
      + "from UserOperationTurnoverEntity t "
      + "where t.userId = :userId and t.executedAt >= :from "
      + "group by t.currency.code")
  List<CurrencyTurnoverView> sumTurnoverByCurrency(@Param("userId") UUID userId,
      @Param("from") LocalDateTime from);

  @Query(value = "SELECT t.executed_at AS \"executedAt\", t.amount * cr.rate AS \"amountUsd\" "
      + "FROM user_operations_turnover t "
      + "JOIN currencies_rate cr ON cr.currency_id = t.currency "
      + "WHERE t.user_id = :userId AND t.executed_at >= :from "
      + "ORDER BY t.executed_at", nativeQuery = true)
  List<OperationAmountView> findOperationAmountsInUsd(@Param("userId") UUID userId,
      @Param("from") LocalDateTime from);
}
