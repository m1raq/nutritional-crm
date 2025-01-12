package ru.app.nutritionologycrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.meet.MeetCreateRequestDTO;
import ru.app.nutritionologycrm.dto.meet.MeetUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.MeetEntity;
import ru.app.nutritionologycrm.exception.EntityProcessingException;
import ru.app.nutritionologycrm.repository.MeetRepository;
import ru.app.nutritionologycrm.service.ClientService;
import ru.app.nutritionologycrm.service.MeetService;
import ru.app.nutritionologycrm.service.UserService;

import java.util.List;

@Slf4j
@Service
public class MeetServiceImpl implements MeetService {

    private final MeetRepository meetRepository;

    private final ClientService clientService;

    private final UserService userService;

    @Autowired
    public MeetServiceImpl(MeetRepository meetRepository, ClientService clientService, UserService userService) {
        this.meetRepository = meetRepository;
        this.clientService = clientService;
        this.userService = userService;
    }


    @Override
    public void save(MeetCreateRequestDTO request, Long clientId) {
        log.info("Attempt to create meet");

        if (meetRepository.existsByDate(request.getDate())) {
            throw new EntityProcessingException("Meet with this date already exists");
        }

        MeetEntity meet = new MeetEntity();
        meet.setDate(request.getDate());
        meet.setDuration(request.getDuration());
        meet.setPlace(request.getPlace());
        meet.setClient(clientService.findById(clientId));
        meet.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        meetRepository.save(meet);
    }

    @Override
    public void update(MeetUpdateRequestDTO updates) {
        log.info("Attempt to update meet {}", updates.getId());

        MeetEntity meet = meetRepository.findById(updates.getId())
                .orElseThrow(() -> new EntityProcessingException("Meet with this id does not exist"));

        if (meetRepository.existsByDate(updates.getDate())
                && !meetRepository.existsByDateAndId(updates.getDate(), updates.getId())
                && !meet.getDate().equals(updates.getDate())) {
            throw new EntityProcessingException("Meet with this date already exists");
        }

        meet.setDate(updates.getDate());
        meet.setDuration(updates.getDuration());
        meet.setPlace(updates.getPlace());
        meetRepository.save(meet);
    }

    @Override
    public List<MeetEntity> findAllByClientId(Long id) {
        log.info("Attempt to find all meet for client {}", id);
        return meetRepository.findAllByClientId(id);
    }

    @Override
    public void delete(Long id) {
        log.info("Attempt to delete meet {}", id);
        meetRepository.deleteById(id);
    }
}
