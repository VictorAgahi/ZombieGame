package fr.epita.zombie.user.application.controllers.exceptions;

import fr.epita.zombie.user.application.dtos.responses.ErrorResponse;
import fr.epita.zombie.user.domain.exceptions.BadCredentialsException;
import fr.epita.zombie.user.domain.exceptions.UserAlreadyExistsException;
import fr.epita.zombie.user.domain.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentials(
      BadCredentialsException exception, HttpServletRequest request) {

    ErrorResponse error =
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.UNAUTHORIZED.value(),
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            exception.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExists(
      UserAlreadyExistsException exception, HttpServletRequest request) {

    // Security: Returning 400 Bad Request instead of 409 Conflict to prevent email enumeration.
    // This way, an attacker cannot easily check if an email is already registered.
    ErrorResponse error =
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "Registration failed: invalid request",
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFound(
      UserNotFoundException exception, HttpServletRequest request) {

    ErrorResponse error =
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            exception.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(
      Exception exception, HttpServletRequest request) {

    ErrorResponse error =
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            "Une erreur interne est survenue. Les développeurs ont été prévenus.",
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
