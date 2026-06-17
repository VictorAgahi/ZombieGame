package fr.epita.zombie.user.application.dtos.responses;

import java.time.LocalDateTime;

public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path) {}
