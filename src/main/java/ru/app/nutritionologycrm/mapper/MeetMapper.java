package ru.app.nutritionologycrm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.app.nutritionologycrm.dto.meet.MeetDTO;
import ru.app.nutritionologycrm.entity.MeetEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MeetMapper {

    MeetDTO toDTO(MeetEntity entity);

    MeetEntity toEntity(MeetDTO dto);

}
