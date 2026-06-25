package fr.epita.zombie.edition.application.controllers;

import fr.epita.zombie.edition.application.dtos.requests.EditionCreateRequest;
import fr.epita.zombie.edition.application.dtos.responses.EditionResponse;
import fr.epita.zombie.edition.application.dtos.responses.ErrorResponse;
import fr.epita.zombie.edition.application.mappers.EditionMapper;
import fr.epita.zombie.edition.domain.entities.EditionEntity;
import fr.epita.zombie.edition.domain.services.EditionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/editions")
@RequiredArgsConstructor
@Tag(name = "Edition Management", description = "Endpoints for creating and managing editions")
public class EditionController {

  private final EditionService editionService;
  private final EditionMapper editionMapper;

  @Operation(summary = "Create a new edition", description = "Creates a new edition of the event")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Edition successfully created",
            content = @Content(schema = @Schema(implementation = EditionResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Creation failed: invalid request or business rule violated",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PostMapping
  public ResponseEntity<?> createEdition(
      @Valid @RequestBody EditionCreateRequest request, HttpServletRequest servletRequest) {
    EditionEntity entity = editionMapper.toEntity(request);
    EditionEntity created = editionService.create(entity);
    EditionResponse response = editionMapper.toResponse(created);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();

    return ResponseEntity.created(location).body(response);
  }

  @Operation(summary = "Get all editions", description = "Returns a list of all editions")
  @ApiResponse(
      responseCode = "200",
      description = "List of editions",
      content = @Content(schema = @Schema(implementation = EditionResponse.class)))
  @GetMapping
  public ResponseEntity<List<EditionResponse>> getAllEditions() {
    List<EditionResponse> responses =
        editionService.findAll().stream()
            .map(editionMapper::toResponse)
            .collect(Collectors.toList());
    return ResponseEntity.ok(responses);
  }

  @Operation(
      summary = "Get an edition by ID",
      description = "Returns the details of a specific edition")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Edition found and returned",
            content = @Content(schema = @Schema(implementation = EditionResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Edition not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping("/{id}")
  public ResponseEntity<?> getEdition(@PathVariable Long id, HttpServletRequest servletRequest) {
    EditionEntity entity = editionService.findById(id);
    return ResponseEntity.ok(editionMapper.toResponse(entity));
  }

  @Operation(
      summary = "Delete or cancel an edition",
      description = "Deletes an edition if it has no registrations, otherwise cancels it")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Edition successfully deleted or canceled",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Edition not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteEdition(@PathVariable Long id, HttpServletRequest servletRequest) {
    editionService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
