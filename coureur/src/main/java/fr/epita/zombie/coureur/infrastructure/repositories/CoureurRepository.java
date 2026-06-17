package fr.epita.zombie.coureur.infrastructure.repositories;

import fr.epita.zombie.coureur.infrastructure.entities.CoureurEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoureurRepository extends JpaRepository<CoureurEntity, Long> {
  // TODO: Define custom queries if necessary
}
