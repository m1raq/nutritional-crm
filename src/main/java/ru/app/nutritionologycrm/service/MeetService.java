package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.dto.meet.MeetCreateRequestDTO;
import ru.app.nutritionologycrm.dto.meet.MeetDTO;
import ru.app.nutritionologycrm.dto.meet.MeetUpdateRequestDTO;


import java.util.List;

public interface MeetService {

    void save(MeetCreateRequestDTO request, Long clientId, String userUsername) throws Exception;

    void update(MeetUpdateRequestDTO updates, String userUsername);

    List<MeetDTO> findAllByClientId(Long id);

    void delete(Long id);

}
