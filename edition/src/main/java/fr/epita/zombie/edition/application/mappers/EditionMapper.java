package fr.epita.zombie.edition.application.mappers;

import fr.epita.zombie.edition.application.dtos.requests.EditionCreateRequest;
import fr.epita.zombie.edition.application.dtos.responses.EditionResponse;
import fr.epita.zombie.edition.domain.entities.EditionEntity;
import org.springframework.stereotype.Component;

@Component
public class EditionMapper {

  public EditionEntity toEntity(EditionCreateRequest request) {
    return EditionEntity.builder()
        .name(request.name())
        .date(request.date())
        .startTime(request.startTime())
        .endTime(request.endTime())
        .location(request.location())
        .maxCoureurs(request.maxCoureurs())
        .maxZombies(request.maxZombies())
        .build();
  }

  public EditionResponse toResponse(EditionEntity entity) {
    return new EditionResponse(
        entity.getId(),
        entity.getName(),
        entity.getDate(),
        entity.getStartTime(),
        entity.getEndTime(),
        entity.getLocation(),
        entity.getMaxCoureurs(),
        entity.getMaxZombies(),
        entity.getStatus());
  }
}
