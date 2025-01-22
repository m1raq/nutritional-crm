package ru.app.nutritionologycrm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryDTO;
import ru.app.nutritionologycrm.entity.MedicalHistoryEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicalHistoryMapper {

    @Mapping(target = "client", ignore = true)
    MedicalHistoryDTO toDTO(MedicalHistoryEntity entity);

    MedicalHistoryEntity toEntity(MedicalHistoryDTO dto);

}
