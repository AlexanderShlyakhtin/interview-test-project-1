package dev.bitruby.testproject.outcomes.postgres.repository;

import dev.bitruby.testproject.outcomes.postgres.domain.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, UUID> {
}