package fr.epita.zombie.zombie.infrastructure.repositories;

import fr.epita.zombie.zombie.infrastructure.models.ZombieModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZombieRepository extends JpaRepository<ZombieModel, Long> {
  // TODO: Define custom queries if necessary
}
