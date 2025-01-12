package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.dto.meet.MeetCreateRequestDTO;
import ru.app.nutritionologycrm.dto.meet.MeetUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.MeetEntity;

import java.util.List;

public interface MeetService {

    void save(MeetCreateRequestDTO request, Long clientId);

    void update(MeetUpdateRequestDTO updates);

    List<MeetEntity> findAllByClientId(Long id);

    void delete(Long id);

}
