package fr.epita.zombie.edition.infrastructure.config;

import fr.epita.zombie.edition.domain.ports.IEditionRepository;
import fr.epita.zombie.edition.domain.ports.INotificationService;
import fr.epita.zombie.edition.domain.ports.IRegistrationPort;
import fr.epita.zombie.edition.domain.services.EditionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EditionDomainConfig {

  @Bean
  public EditionService editionService(
      IEditionRepository editionRepository,
      INotificationService notificationService,
      IRegistrationPort registrationPort) {
    return new EditionService(editionRepository, notificationService, registrationPort);
  }
}
