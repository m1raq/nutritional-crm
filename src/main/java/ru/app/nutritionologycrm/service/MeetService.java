package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.entity.MeetEntity;

import java.util.List;

public interface MeetService {

    void save(MeetEntity meet);

    List<MeetEntity> findAllByClientContacts(String contacts);

    void delete(Long id);

}
