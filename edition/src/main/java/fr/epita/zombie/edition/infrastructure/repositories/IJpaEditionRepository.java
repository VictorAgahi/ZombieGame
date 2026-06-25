package fr.epita.zombie.edition.infrastructure.repositories;

import fr.epita.zombie.edition.domain.entities.EditionStatus;
import fr.epita.zombie.edition.infrastructure.models.EditionModel;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJpaEditionRepository extends JpaRepository<EditionModel, Long> {

  boolean existsByDateAndStatusNotAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
      LocalDate date, EditionStatus status, LocalTime endTime, LocalTime startTime);
}
