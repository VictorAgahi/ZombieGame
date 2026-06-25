package fr.epita.zombie.edition.factories;

import fr.epita.zombie.edition.application.dtos.requests.EditionCreateRequest;
import java.time.LocalDate;
import java.time.LocalTime;

public class EditionRequestFactory {

  public static EditionCreateRequest validCreateRequest() {
    return new EditionCreateRequest(
        "Edition Hiver",
        LocalDate.now().plusDays(30),
        LocalTime.of(9, 0),
        LocalTime.of(17, 0),
        "Forêt de Fontainebleau",
        200,
        100);
  }

  public static EditionCreateRequest pastDateRequest() {
    return new EditionCreateRequest(
        "Edition Hiver",
        LocalDate.now().minusDays(5),
        LocalTime.of(9, 0),
        LocalTime.of(17, 0),
        "Forêt de Fontainebleau",
        200,
        100);
  }
}
