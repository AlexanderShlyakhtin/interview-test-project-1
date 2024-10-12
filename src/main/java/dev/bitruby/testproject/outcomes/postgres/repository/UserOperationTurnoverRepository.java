package dev.bitruby.testproject.outcomes.postgres.repository;

import dev.bitruby.testproject.outcomes.postgres.domain.UserOperationTurnoverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserOperationTurnoverRepository
    extends JpaRepository<UserOperationTurnoverEntity, UUID> {
}
