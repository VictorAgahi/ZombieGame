package fr.epita.zombie.user.application.dtos.requests;

import fr.epita.zombie.user.infrastructure.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
    @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email,
    @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,
    @NotNull(message = "Role is required") Role role) {}
