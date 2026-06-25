package fr.epita.zombie.edition.infrastructure.services;

import fr.epita.zombie.edition.domain.ports.IRegistrationPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegistrationAdapter implements IRegistrationPort {

  @Override
  public boolean hasRegistrations(Long editionId) {
    log.warn(
        "L'intégration avec le module d'inscriptions n'est pas encore implémentée pour l'édition {}",
        editionId);
    // TODO CLEMENT: Implémenter la communication avec le module coureur/zombie
    return false;
  }
}
