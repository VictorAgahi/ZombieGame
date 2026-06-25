package fr.epita.zombie.edition.domain.ports;

public interface INotificationService {
  void notifyCancellation(Long editionId);
}
