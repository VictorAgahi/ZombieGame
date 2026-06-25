package fr.epita.zombie.edition.domain.ports;

import fr.epita.zombie.edition.domain.entities.EditionEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface IEditionRepository {
  EditionEntity save(EditionEntity edition);

  Optional<EditionEntity> findById(Long id);

  List<EditionEntity> findAll();

  void delete(Long id);

  boolean existsOverlappingEdition(LocalDate date, LocalTime startTime, LocalTime endTime);
}
