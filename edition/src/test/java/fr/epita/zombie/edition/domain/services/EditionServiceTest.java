package fr.epita.zombie.edition.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fr.epita.zombie.edition.domain.entities.EditionEntity;
import fr.epita.zombie.edition.domain.entities.EditionStatus;
import fr.epita.zombie.edition.domain.exceptions.EditionOverlapException;
import fr.epita.zombie.edition.domain.exceptions.InvalidEditionDateException;
import fr.epita.zombie.edition.domain.exceptions.InvalidEditionTimeException;
import fr.epita.zombie.edition.domain.ports.IEditionRepository;
import fr.epita.zombie.edition.domain.ports.INotificationService;
import fr.epita.zombie.edition.domain.ports.IRegistrationPort;
import fr.epita.zombie.edition.factories.EditionTestFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EditionServiceTest {

  @Mock private IEditionRepository editionRepository;
  @Mock private INotificationService notificationService;
  @Mock private IRegistrationPort registrationPort;

  @InjectMocks private EditionService editionService;

  @Test
  void should_create_edition_when_valid() {
    // Arrange
    EditionEntity edition = EditionTestFactory.validEdition();
    when(editionRepository.existsOverlappingEdition(
            edition.getDate(), edition.getStartTime(), edition.getEndTime()))
        .thenReturn(false);
    when(editionRepository.save(any(EditionEntity.class))).thenReturn(edition);

    // Act
    EditionEntity result = editionService.create(edition);

    // Assert
    assertThat(result).isNotNull();
    verify(editionRepository).save(any(EditionEntity.class));
  }

  @Test
  void should_throw_exception_when_date_is_in_the_past() {
    // Arrange
    EditionEntity edition = EditionTestFactory.pastEdition();

    // Act & Assert
    assertThrows(InvalidEditionDateException.class, () -> editionService.create(edition));
    verify(editionRepository, never()).save(any());
  }

  @Test
  void should_throw_exception_when_end_time_is_before_start_time() {
    // Arrange
    EditionEntity edition =
        EditionEntity.builder()
            .date(LocalDate.now().plusDays(1))
            .startTime(LocalTime.of(18, 0))
            .endTime(LocalTime.of(10, 0))
            .build();

    // Act & Assert
    assertThrows(InvalidEditionTimeException.class, () -> editionService.create(edition));
  }

  @Test
  void should_throw_exception_when_edition_overlaps() {
    // Arrange
    EditionEntity edition = EditionTestFactory.validEdition();
    when(editionRepository.existsOverlappingEdition(
            edition.getDate(), edition.getStartTime(), edition.getEndTime()))
        .thenReturn(true);

    // Act & Assert
    assertThrows(EditionOverlapException.class, () -> editionService.create(edition));
  }

  @Test
  void should_delete_edition_when_no_registrations() {
    // Arrange
    EditionEntity edition = EditionTestFactory.validEdition();
    when(editionRepository.findById(1L)).thenReturn(Optional.of(edition));
    when(registrationPort.hasRegistrations(1L)).thenReturn(false);

    // Act
    editionService.delete(1L);

    // Assert
    verify(editionRepository).delete(1L);
    verify(editionRepository, never()).save(any());
    verify(notificationService, never()).notifyCancellation(any());
  }

  @Test
  void should_cancel_and_notify_when_edition_has_registrations() {
    // Arrange
    EditionEntity edition = EditionTestFactory.validEdition();
    when(editionRepository.findById(1L)).thenReturn(Optional.of(edition));
    when(registrationPort.hasRegistrations(1L)).thenReturn(true);

    // Act
    editionService.delete(1L);

    // Assert
    assertThat(edition.getStatus()).isEqualTo(EditionStatus.CANCELED);
    verify(editionRepository).save(edition);
    verify(notificationService).notifyCancellation(1L);
    verify(editionRepository, never()).delete(1L);
  }
}
