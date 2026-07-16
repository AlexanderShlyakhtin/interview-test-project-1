package dev.bitruby.testproject.application.port.out;

import dev.bitruby.testproject.domain.Operation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OperationPort {

  List<Operation> findOperations(UUID userId, LocalDateTime from);
}
