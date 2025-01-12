package ru.app.nutritionologycrm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.entity.MeetEntity;
import ru.app.nutritionologycrm.repository.MeetRepository;
import ru.app.nutritionologycrm.service.MeetService;

import java.util.List;

@Service
public class MeetServiceImpl implements MeetService {

    private final MeetRepository meetRepository;

    @Autowired
    public MeetServiceImpl(MeetRepository meetRepository) {
        this.meetRepository = meetRepository;
    }

    @Override
    public void save(MeetEntity meet) {
        meetRepository.save(meet);
    }

    @Override
    public List<MeetEntity> findAllByClientContacts(String contacts) {
        return meetRepository.findAllByClientContacts(contacts);
    }

    @Override
    public void delete(Long id) {
        meetRepository.deleteById(id);
    }
}
