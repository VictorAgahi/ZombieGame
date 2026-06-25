package fr.epita.zombie.coureur.infrastructure.repositories;

import fr.epita.zombie.coureur.infrastructure.models.CoureurModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoureurRepository extends JpaRepository<CoureurModel, Long> {
  // TODO: Define custom queries if necessary
}
