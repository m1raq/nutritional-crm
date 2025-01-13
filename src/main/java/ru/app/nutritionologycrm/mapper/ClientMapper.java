package ru.app.nutritionologycrm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.entity.ClientEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

    ClientDTO toDTO(ClientEntity entity);

    ClientEntity toEntity(ClientDTO dto);

}
