package fr.epita.zombie.user.application.controllers;

import fr.epita.zombie.user.application.dtos.requests.UserRequest;
import fr.epita.zombie.user.application.dtos.responses.UserResponse;
import fr.epita.zombie.user.application.mappers.UserMapper;
import fr.epita.zombie.user.domain.models.UserModel;
import fr.epita.zombie.user.domain.services.UserService;
import fr.epita.zombie.user.infrastructure.security.UserDetailsConnected;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Endpoints for user registration and profile management")
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  public UserController(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @Operation(summary = "User login", description = "Authenticates a user and returns their profile")
  @ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "User successfully authenticated",
        content = @Content(schema = @Schema(implementation = UserResponse.class))),
    @ApiResponse(responseCode = "401", description = "Invalid credentials"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping("/login")
  public ResponseEntity<UserResponse> login(@Valid @RequestBody UserRequest request) {
    return ResponseEntity.ok(userService.authenticate(request));
  }

  @Operation(summary = "Register a new user", description = "Creates a new user account in the system")
  @ApiResponses(value = {
    @ApiResponse(
        responseCode = "201",
        description = "User successfully registered",
        content = @Content(schema = @Schema(implementation = UserResponse.class))),
    @ApiResponse(responseCode = "400", description = "Registration failed: invalid request"),
    @ApiResponse(responseCode = "409", description = "Conflict - User already exists (internal)"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping("/register")
  public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
  }

  @Operation(summary = "Get current user profile", description = "Returns the profile information of the currently authenticated user")
  @ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "Profile found and returned",
        content = @Content(schema = @Schema(implementation = UserResponse.class))),
    @ApiResponse(responseCode = "404", description = "User not found")
  })
  @GetMapping("/me")
  public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal UserDetailsConnected currentUser) {
    return ResponseEntity.ok(userService.getById(currentUser.getId()));
  }

  @Operation(summary = "Update current user profile", description = "Updates the profile information of the currently authenticated user")
  @ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "Profile successfully updated",
        content = @Content(schema = @Schema(implementation = UserResponse.class))),
    @ApiResponse(responseCode = "400", description = "Invalid input data"),
    @ApiResponse(responseCode = "404", description = "User not found")
  })
  @PutMapping("/me")
  public ResponseEntity<UserResponse> updateMe(
      @AuthenticationPrincipal UserDetailsConnected currentUser,
      @Valid @RequestBody UserRequest request) {
    return ResponseEntity.ok(userService.update(currentUser.getId(), request));
  }

  @Operation(summary = "Delete current user account", description = "Deletes the account of the currently authenticated user")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "User successfully deleted"),
    @ApiResponse(responseCode = "404", description = "User not found")
  })
  @DeleteMapping("/me")
  public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal UserDetailsConnected currentUser) {
    userService.delete(currentUser.getId());
    return ResponseEntity.noContent().build();
  }
}
