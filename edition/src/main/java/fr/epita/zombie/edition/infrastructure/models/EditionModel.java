package fr.epita.zombie.edition.infrastructure.models;

import fr.epita.zombie.edition.domain.entities.EditionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "editions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditionModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private String location;
  private int maxCoureurs;
  private int maxZombies;

  @Enumerated(EnumType.STRING)
  private EditionStatus status;
}
