package fr.epita.zombie.edition.infrastructure.repositories;

import fr.epita.zombie.edition.domain.entities.EditionEntity;
import fr.epita.zombie.edition.domain.ports.IEditionRepository;
import fr.epita.zombie.edition.infrastructure.mappers.EditionModelMapper;
import fr.epita.zombie.edition.infrastructure.models.EditionModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EditionRepositoryAdapter implements IEditionRepository {

  private final IJpaEditionRepository jpaRepository;
  private final EditionModelMapper editionModelMapper;

  @Override
  public EditionEntity save(EditionEntity edition) {
    EditionModel model = editionModelMapper.toModel(edition);
    EditionModel saved = jpaRepository.save(model);
    return editionModelMapper.toEntity(saved);
  }

  @Override
  public Optional<EditionEntity> findById(Long id) {
    return jpaRepository.findById(id).map(editionModelMapper::toEntity);
  }

  @Override
  public List<EditionEntity> findAll() {
    return jpaRepository.findAll().stream()
        .map(editionModelMapper::toEntity)
        .collect(Collectors.toList());
  }

  @Override
  public void delete(Long id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public boolean existsOverlappingEdition(LocalDate date, LocalTime startTime, LocalTime endTime) {
    return jpaRepository
        .existsByDateAndStatusNotAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            date,
            fr.epita.zombie.edition.domain.entities.EditionStatus.CANCELED,
            endTime,
            startTime);
  }
}
