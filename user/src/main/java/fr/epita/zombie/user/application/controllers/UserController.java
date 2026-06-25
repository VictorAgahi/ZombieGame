package fr.epita.zombie.user.application.controllers;

import fr.epita.zombie.user.application.dtos.requests.UserRegisterRequest;
import fr.epita.zombie.user.application.dtos.responses.ErrorResponse;
import fr.epita.zombie.user.application.dtos.responses.UserResponse;
import fr.epita.zombie.user.application.mappers.UserMapper;
import fr.epita.zombie.user.domain.entities.UserEntity;
import fr.epita.zombie.user.domain.exceptions.UserAlreadyExistsException;
import fr.epita.zombie.user.domain.exceptions.UserNotFoundException;
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
import java.time.LocalDateTime;
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
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content)
      })
  @PostMapping("/register")
  public ResponseEntity<?> register(
      @Valid @RequestBody UserRegisterRequest request, HttpServletRequest servletRequest) {
    try {
      UserEntity domainUser = userMapper.toEntity(request);
      UserEntity registeredUser = userService.register(domainUser);
      return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(registeredUser));
    } catch (UserAlreadyExistsException ex) {
      ErrorResponse error =
          new ErrorResponse(
              LocalDateTime.now(),
              HttpStatus.BAD_REQUEST.value(),
              HttpStatus.BAD_REQUEST.getReasonPhrase(),
              "Registration failed: invalid request",
              servletRequest.getRequestURI());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
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
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
      })
  @GetMapping("/me")
  public ResponseEntity<?> getMe(
      @AuthenticationPrincipal UserDetailsConnected currentUser,
      HttpServletRequest servletRequest) {
    try {
      UserEntity user = userService.getById(currentUser.getId());
      return ResponseEntity.ok(userMapper.toResponse(user));
    } catch (UserNotFoundException ex) {
      ErrorResponse error =
          new ErrorResponse(
              LocalDateTime.now(),
              HttpStatus.NOT_FOUND.value(),
              HttpStatus.NOT_FOUND.getReasonPhrase(),
              ex.getMessage(),
              servletRequest.getRequestURI());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
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
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
      })
  @PutMapping("/me")
  public ResponseEntity<?> updateMe(
      @AuthenticationPrincipal UserDetailsConnected currentUser,
      @Valid @RequestBody UserRegisterRequest request,
      HttpServletRequest servletRequest) {
    try {
      UserEntity domainUser = userMapper.toEntity(request);
      UserEntity updatedUser = userService.update(currentUser.getId(), domainUser);
      return ResponseEntity.ok(userMapper.toResponse(updatedUser));
    } catch (UserNotFoundException ex) {
      ErrorResponse error =
          new ErrorResponse(
              LocalDateTime.now(),
              HttpStatus.NOT_FOUND.value(),
              HttpStatus.NOT_FOUND.getReasonPhrase(),
              ex.getMessage(),
              servletRequest.getRequestURI());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
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
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
      })
  @DeleteMapping("/me")
  public ResponseEntity<?> deleteMe(
      @AuthenticationPrincipal UserDetailsConnected currentUser,
      HttpServletRequest servletRequest) {
    try {
      userService.delete(currentUser.getId());
      return ResponseEntity.noContent().build();
    } catch (UserNotFoundException ex) {
      ErrorResponse error =
          new ErrorResponse(
              LocalDateTime.now(),
              HttpStatus.NOT_FOUND.value(),
              HttpStatus.NOT_FOUND.getReasonPhrase(),
              ex.getMessage(),
              servletRequest.getRequestURI());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
  }
}
