package ru.app.nutritionologycrm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.app.nutritionologycrm.dto.DocumentDTO;
import ru.app.nutritionologycrm.entity.DocumentEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentMapper {

    DocumentDTO toDTO(DocumentEntity entity);

    DocumentEntity toEntity(DocumentDTO dto);

}
