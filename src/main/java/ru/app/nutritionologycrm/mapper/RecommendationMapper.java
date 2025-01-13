package ru.app.nutritionologycrm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationDTO;
import ru.app.nutritionologycrm.entity.RecommendationEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RecommendationMapper {

    RecommendationDTO toDTO(RecommendationEntity entity);

    RecommendationEntity toEntity(RecommendationDTO dto);

}
