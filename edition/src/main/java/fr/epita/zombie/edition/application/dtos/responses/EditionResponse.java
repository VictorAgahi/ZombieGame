package fr.epita.zombie.edition.application.dtos.responses;

import fr.epita.zombie.edition.domain.entities.EditionStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public record EditionResponse(
    Long id,
    String name,
    LocalDate date,
    LocalTime startTime,
    LocalTime endTime,
    String location,
    int maxCoureurs,
    int maxZombies,
    EditionStatus status) {}
