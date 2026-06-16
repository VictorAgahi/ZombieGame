package fr.epita.zombie.edition.infrastructure.repositories;

import fr.epita.zombie.edition.domain.entities.EditionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditionRepository extends JpaRepository<EditionEntity, Long> {
  // TODO: Define custom queries if necessary
}
