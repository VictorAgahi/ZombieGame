package fr.epita.zombie.edition.application.dtos.requests;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record EditionCreateRequest(
    @NotBlank(message = "Name is required") String name,
    @NotNull(message = "Date is required") @Future(message = "Date must be in the future")
        LocalDate date,
    @NotNull(message = "Start time is required") LocalTime startTime,
    @NotNull(message = "End time is required") LocalTime endTime,
    @NotBlank(message = "Location is required") String location,
    @Min(value = 1, message = "Max coureurs must be at least 1") int maxCoureurs,
    @Min(value = 1, message = "Max zombies must be at least 1") int maxZombies) {}
