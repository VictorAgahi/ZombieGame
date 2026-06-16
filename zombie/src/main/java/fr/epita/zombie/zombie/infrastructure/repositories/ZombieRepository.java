package fr.epita.zombie.zombie.infrastructure.repositories;

import fr.epita.zombie.zombie.domain.entities.ZombieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZombieRepository extends JpaRepository<ZombieEntity, Long> {
  // TODO: Define custom queries if necessary
}
