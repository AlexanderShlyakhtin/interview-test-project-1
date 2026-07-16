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
}
