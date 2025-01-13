package ru.app.nutritionologycrm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.app.nutritionologycrm.dto.UserDTO;
import ru.app.nutritionologycrm.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDTO toDTO(UserEntity entity);

    UserEntity toEntity(UserDTO dto);

}
