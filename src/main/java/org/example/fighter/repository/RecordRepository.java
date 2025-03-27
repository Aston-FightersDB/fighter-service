package org.example.fighter.repository;

import org.example.fighter.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для сущности Record.
 */
@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {
}
