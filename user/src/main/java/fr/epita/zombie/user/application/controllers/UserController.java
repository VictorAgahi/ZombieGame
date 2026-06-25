package fr.epita.zombie.user.application.controllers;

import fr.epita.zombie.user.application.dtos.requests.UserRegisterRequest;
import fr.epita.zombie.user.application.dtos.requests.UserUpdateRequest;
import fr.epita.zombie.user.application.dtos.responses.ErrorResponse;
import fr.epita.zombie.user.application.dtos.responses.UserResponse;
import fr.epita.zombie.user.application.mappers.UserMapper;
import fr.epita.zombie.user.domain.entities.UserEntity;
import fr.epita.zombie.user.domain.services.UserService;
import fr.epita.zombie.user.infrastructure.security.UserDetailsConnected;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(
    name = "User Management",
    description = "Endpoints for user registration and profile management")
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  public UserController(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @Operation(
      summary = "Register a new user",
      description = "Creates a new user account in the system")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "User successfully registered",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Registration failed: invalid request",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PostMapping("/register")
  public ResponseEntity<?> register(
      @Valid @RequestBody UserRegisterRequest request, HttpServletRequest servletRequest) {
    UserEntity domainUser = userMapper.toEntity(request);
    UserEntity registeredUser = userService.register(domainUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(registeredUser));
  }

  @Operation(
      summary = "Get current user profile",
      description = "Returns the profile information of the currently authenticated user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Profile found and returned",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping("/me")
  public ResponseEntity<?> getMe(
      @AuthenticationPrincipal UserDetailsConnected currentUser,
      HttpServletRequest servletRequest) {
    UserEntity user = userService.getById(currentUser.getId());
    return ResponseEntity.ok(userMapper.toResponse(user));
  }

  @Operation(
      summary = "Update current user profile",
      description = "Updates the profile information of the currently authenticated user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Profile successfully updated",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PutMapping("/me")
  public ResponseEntity<?> updateMe(
      @AuthenticationPrincipal UserDetailsConnected currentUser,
      @Valid @RequestBody UserUpdateRequest request,
      HttpServletRequest servletRequest) {
    UserEntity domainUser = userMapper.toEntity(request);
    UserEntity updatedUser = userService.update(currentUser.getId(), domainUser);
    return ResponseEntity.ok(userMapper.toResponse(updatedUser));
  }

  @Operation(
      summary = "Delete current user account",
      description = "Deletes the account of the currently authenticated user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "User successfully deleted",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @DeleteMapping("/me")
  public ResponseEntity<?> deleteMe(
      @AuthenticationPrincipal UserDetailsConnected currentUser,
      HttpServletRequest servletRequest) {
    userService.delete(currentUser.getId());
    return ResponseEntity.noContent().build();
  }
}
