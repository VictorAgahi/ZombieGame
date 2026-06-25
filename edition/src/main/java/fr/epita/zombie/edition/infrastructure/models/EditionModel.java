package fr.epita.zombie.edition.infrastructure.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EditionModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // TODO: Define specific JPA fields (name, date, startTime, endTime, location, maxRunners,
  // maxZombies)
}
