package org.example.fighter.repository;

import org.example.fighter.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для сущности Info.
 */
@Repository
public interface InfoRepository extends JpaRepository<Info, UUID> {
}
