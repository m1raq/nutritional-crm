package ru.app.nutritionologycrm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerDTO;
import ru.app.nutritionologycrm.entity.BiomarkerEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BiomarkerMapper {

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "user", ignore = true)
    BiomarkerDTO toDTO(BiomarkerEntity entity);

    BiomarkerEntity toEntity(BiomarkerDTO dto);

}
