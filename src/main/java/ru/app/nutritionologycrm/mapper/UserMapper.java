package ru.app.nutritionologycrm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.app.nutritionologycrm.dto.UserDTO;
import ru.app.nutritionologycrm.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "clients", ignore = true)
    @Mapping(target = "biomarkers", ignore = true)
    @Mapping(target = "documents", ignore = true)
    @Mapping(target = "meets", ignore = true)
    @Mapping(target = "recommendations", ignore = true)
    UserDTO toDTO(UserEntity entity);

    UserEntity toEntity(UserDTO dto);

}
