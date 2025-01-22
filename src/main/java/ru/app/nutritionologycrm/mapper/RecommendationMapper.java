package ru.app.nutritionologycrm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationDTO;
import ru.app.nutritionologycrm.entity.RecommendationEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RecommendationMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "client", ignore = true)
    RecommendationDTO toDTO(RecommendationEntity entity);

    RecommendationEntity toEntity(RecommendationDTO dto);

}
