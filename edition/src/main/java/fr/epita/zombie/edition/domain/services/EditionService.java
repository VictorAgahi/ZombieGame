package fr.epita.zombie.edition.domain.services;

import fr.epita.zombie.edition.domain.entities.EditionEntity;
import fr.epita.zombie.edition.domain.entities.EditionStatus;
import fr.epita.zombie.edition.domain.exceptions.EditionNotFoundException;
import fr.epita.zombie.edition.domain.exceptions.EditionOverlapException;
import fr.epita.zombie.edition.domain.exceptions.InvalidEditionDateException;
import fr.epita.zombie.edition.domain.exceptions.InvalidEditionTimeException;
import fr.epita.zombie.edition.domain.ports.IEditionRepository;
import fr.epita.zombie.edition.domain.ports.INotificationService;
import fr.epita.zombie.edition.domain.ports.IRegistrationPort;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditionService {

  private final IEditionRepository editionRepository;
  private final INotificationService notificationService;
  private final IRegistrationPort registrationPort;

  public EditionEntity create(EditionEntity edition) {
    validateEdition(edition);

    EditionEntity toSave = EditionEntity.builder()
        .name(edition.getName())
        .date(edition.getDate())
        .startTime(edition.getStartTime())
        .endTime(edition.getEndTime())
        .location(edition.getLocation())
        .maxCoureurs(edition.getMaxCoureurs())
        .maxZombies(edition.getMaxZombies())
        .status(EditionStatus.CREATED)
        .build();

    return editionRepository.save(toSave);
  }

  public EditionEntity findById(Long id) {
    return editionRepository
        .findById(id)
        .orElseThrow(() -> new EditionNotFoundException("Edition with id " + id + " not found"));
  }

  public List<EditionEntity> findAll() {
    return editionRepository.findAll();
  }

  public void delete(Long id) {
    EditionEntity edition = findById(id);

    if (registrationPort.hasRegistrations(id)) {
      edition.cancel();
      editionRepository.save(edition);
      notificationService.notifyCancellation(id);
    } else {
      editionRepository.delete(id);
    }
  }

  private void validateEdition(EditionEntity edition) {
    if (edition.getDate().isBefore(LocalDate.now())) {
      throw new InvalidEditionDateException("Edition date must be in the future");
    }

    if (edition.getDate().isEqual(LocalDate.now())
        && edition.getStartTime().isBefore(LocalTime.now())) {
      throw new InvalidEditionTimeException("Edition start time must be in the future");
    }

    if (!edition.getEndTime().isAfter(edition.getStartTime())) {
      throw new InvalidEditionTimeException("Edition end time must be strictly after start time");
    }

    if (editionRepository.existsOverlappingEdition(
        edition.getDate(), edition.getStartTime(), edition.getEndTime())) {
      throw new EditionOverlapException("This edition overlaps with an existing one");
    }
  }
}
