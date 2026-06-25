package fr.epita.zombie.edition.factories;

import fr.epita.zombie.edition.domain.entities.EditionEntity;
import fr.epita.zombie.edition.domain.entities.EditionStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public class EditionTestFactory {

  public static EditionEntity validEdition() {
    return EditionEntity.builder()
        .id(1L)
        .name("Edition Printemps")
        .date(LocalDate.now().plusDays(10))
        .startTime(LocalTime.of(10, 0))
        .endTime(LocalTime.of(18, 0))
        .location("Parc des Princes")
        .maxCoureurs(100)
        .maxZombies(50)
        .status(EditionStatus.CREATED)
        .build();
  }

  public static EditionEntity pastEdition() {
    return EditionEntity.builder()
        .id(2L)
        .name("Edition Passée")
        .date(LocalDate.now().minusDays(1))
        .startTime(LocalTime.of(10, 0))
        .endTime(LocalTime.of(18, 0))
        .location("Parc des Princes")
        .maxCoureurs(100)
        .maxZombies(50)
        .status(EditionStatus.CREATED)
        .build();
  }
}
