package fr.epita.zombie.edition.infrastructure.repositories;

import fr.epita.zombie.edition.infrastructure.models.EditionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditionRepository extends JpaRepository<EditionModel, Long> {
  // TODO: Define custom queries if necessary
}
