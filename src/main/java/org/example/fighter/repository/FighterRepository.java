package org.example.fighter.repository;

import org.example.fighter.entity.Fighter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для сущности Fighter.
 */
@Repository
public interface FighterRepository extends JpaRepository<Fighter, Long> {
}
