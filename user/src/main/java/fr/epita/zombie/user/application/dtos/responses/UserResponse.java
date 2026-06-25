package fr.epita.zombie.user.application.dtos.responses;

import fr.epita.zombie.user.infrastructure.models.Role;

public record UserResponse(Long id, String email, Role role) {}
