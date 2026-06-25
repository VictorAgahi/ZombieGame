package fr.epita.zombie.edition.domain.ports;

public interface IRegistrationPort {
  boolean hasRegistrations(Long editionId);
}
