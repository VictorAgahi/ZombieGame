package fr.epita.zombie.edition.application.exceptions;

import fr.epita.zombie.edition.application.dtos.responses.ErrorResponse;
import fr.epita.zombie.edition.domain.exceptions.EditionNotFoundException;
import fr.epita.zombie.edition.domain.exceptions.EditionOverlapException;
import fr.epita.zombie.edition.domain.exceptions.InvalidEditionDateException;
import fr.epita.zombie.edition.domain.exceptions.InvalidEditionTimeException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({
    EditionOverlapException.class,
    InvalidEditionDateException.class,
    InvalidEditionTimeException.class
  })
  public ResponseEntity<ErrorResponse> handleInvalidEditionException(
      RuntimeException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage() != null ? ex.getMessage() : "Invalid edition data",
                request.getRequestURI()));
  }

  @ExceptionHandler(EditionNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEditionNotFoundException(
      EditionNotFoundException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage() != null ? ex.getMessage() : "Edition not found",
                request.getRequestURI()));
  }

  @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      org.springframework.web.bind.MethodArgumentNotValidException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed",
                request.getRequestURI()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(
      Exception ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Internal server error",
                request.getRequestURI()));
  }
}
