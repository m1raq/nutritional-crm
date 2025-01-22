package ru.app.nutritionologycrm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.entity.ClientEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "medicalHistory", ignore = true)
    @Mapping(target = "meets", ignore = true)
    @Mapping(target = "biomarkers", ignore = true)
    @Mapping(target = "recommendations", ignore = true)
    @Mapping(target = "documents", ignore = true)
    ClientDTO toDTO(ClientEntity entity);

    ClientEntity toEntity(ClientDTO dto);

}
