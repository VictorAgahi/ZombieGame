package fr.epita.zombie.edition.infrastructure.mappers;

import fr.epita.zombie.edition.domain.entities.EditionEntity;
import fr.epita.zombie.edition.infrastructure.models.EditionModel;
import org.springframework.stereotype.Component;

@Component
public class EditionModelMapper {

  public EditionModel toModel(EditionEntity entity) {
    return EditionModel.builder()
        .id(entity.getId())
        .name(entity.getName())
        .date(entity.getDate())
        .startTime(entity.getStartTime())
        .endTime(entity.getEndTime())
        .location(entity.getLocation())
        .maxCoureurs(entity.getMaxCoureurs())
        .maxZombies(entity.getMaxZombies())
        .status(entity.getStatus())
        .build();
  }

  public EditionEntity toEntity(EditionModel model) {
    return EditionEntity.builder()
        .id(model.getId())
        .name(model.getName())
        .date(model.getDate())
        .startTime(model.getStartTime())
        .endTime(model.getEndTime())
        .location(model.getLocation())
        .maxCoureurs(model.getMaxCoureurs())
        .maxZombies(model.getMaxZombies())
        .status(model.getStatus())
        .build();
  }
}
