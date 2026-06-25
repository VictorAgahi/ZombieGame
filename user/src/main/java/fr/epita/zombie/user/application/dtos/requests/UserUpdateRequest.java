package fr.epita.zombie.user.application.dtos.requests;

import fr.epita.zombie.user.domain.valueobjects.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
    @Email(message = "Email should be valid") String email,
    @Size(min = 8, message = "Password must be at least 8 characters long") String password,
    Role role) {}
