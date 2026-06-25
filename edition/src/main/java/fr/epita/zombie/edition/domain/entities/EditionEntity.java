package fr.epita.zombie.edition.domain.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditionEntity {
  private Long id;
  private String name;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private String location;
  private int maxCoureurs;
  private int maxZombies;
  private EditionStatus status;

  public void cancel() {
    this.status = EditionStatus.CANCELED;
  }
}
