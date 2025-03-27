package ru.aston.fighterservice.repository;

import ru.aston.fighterservice.entity.Fighter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для сущности Fighter.
 */
@Repository
public interface FighterRepository extends JpaRepository<Fighter, UUID> {
}
