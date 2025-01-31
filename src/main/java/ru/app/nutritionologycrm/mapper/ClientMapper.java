package ru.app.nutritionologycrm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.entity.ClientEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        MedicalHistoryMapper.class
        , BiomarkerMapper.class
        , DocumentMapper.class
        , UserMapper.class
        , RecommendationMapper.class})
public interface ClientMapper {

    @Mapping(target = "meets", ignore = true)
    ClientDTO toDTO(ClientEntity entity);

    ClientEntity toEntity(ClientDTO dto);

}
