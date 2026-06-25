package fr.epita.zombie.edition.application.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.epita.zombie.edition.application.dtos.requests.EditionCreateRequest;
import fr.epita.zombie.edition.application.mappers.EditionMapper;
import fr.epita.zombie.edition.domain.entities.EditionEntity;
import fr.epita.zombie.edition.domain.exceptions.EditionNotFoundException;
import fr.epita.zombie.edition.domain.exceptions.InvalidEditionDateException;
import fr.epita.zombie.edition.domain.services.EditionService;
import fr.epita.zombie.edition.factories.EditionRequestFactory;
import fr.epita.zombie.edition.factories.EditionTestFactory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EditionController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(EditionMapper.class)
class EditionControllerIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private EditionService editionService;

  @Test
  void should_return_201_when_create_edition_is_successful() throws Exception {
    // Arrange
    EditionCreateRequest request = EditionRequestFactory.validCreateRequest();
    EditionEntity createdEntity = EditionTestFactory.validEdition();
    when(editionService.create(any(EditionEntity.class))).thenReturn(createdEntity);

    // Act & Assert
    mockMvc
        .perform(
            post("/api/editions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(createdEntity.getId()))
        .andExpect(jsonPath("$.name").value(createdEntity.getName()));
  }

  @Test
  void should_return_400_when_create_edition_has_invalid_date() throws Exception {
    // Arrange
    EditionCreateRequest request = EditionRequestFactory.pastDateRequest();

    // Act & Assert
    mockMvc
        .perform(
            post("/api/editions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_return_400_when_business_rule_fails() throws Exception {
    // Arrange
    EditionCreateRequest request = EditionRequestFactory.validCreateRequest();
    when(editionService.create(any(EditionEntity.class)))
        .thenThrow(new InvalidEditionDateException("Date must be in the future"));

    // Act & Assert
    mockMvc
        .perform(
            post("/api/editions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Date must be in the future"));
  }

  @Test
  void should_return_200_and_list_editions() throws Exception {
    // Arrange
    EditionEntity edition = EditionTestFactory.validEdition();
    when(editionService.findAll()).thenReturn(List.of(edition));

    // Act & Assert
    mockMvc
        .perform(get("/api/editions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(edition.getId()));
  }

  @Test
  void should_return_200_when_get_edition_by_id_exists() throws Exception {
    // Arrange
    EditionEntity edition = EditionTestFactory.validEdition();
    when(editionService.findById(1L)).thenReturn(edition);

    // Act & Assert
    mockMvc
        .perform(get("/api/editions/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(edition.getId()));
  }

  @Test
  void should_return_404_when_get_edition_by_id_not_found() throws Exception {
    // Arrange
    when(editionService.findById(99L)).thenThrow(new EditionNotFoundException("Not found"));

    // Act & Assert
    mockMvc
        .perform(get("/api/editions/99"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Not found"));
  }

  @Test
  void should_return_204_when_delete_is_successful() throws Exception {
    // Act & Assert
    mockMvc.perform(delete("/api/editions/1")).andExpect(status().isNoContent());
    verify(editionService).delete(1L);
  }

  @Test
  void should_return_404_when_delete_not_found() throws Exception {
    // Arrange
    doThrow(new EditionNotFoundException("Not found")).when(editionService).delete(99L);

    // Act & Assert
    mockMvc
        .perform(delete("/api/editions/99"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Not found"));
  }
}
