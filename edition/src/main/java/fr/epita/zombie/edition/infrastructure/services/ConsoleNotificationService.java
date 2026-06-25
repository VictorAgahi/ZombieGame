package fr.epita.zombie.edition.infrastructure.services;

import fr.epita.zombie.edition.domain.ports.INotificationService;
import org.springframework.stereotype.Service;

@Service
public class ConsoleNotificationService implements INotificationService {

  @Override
  public void notifyCancellation(Long editionId) {
    System.out.println("Envoi d'un mail d'annulation aux inscrits de l'édition " + editionId);
  }
}
