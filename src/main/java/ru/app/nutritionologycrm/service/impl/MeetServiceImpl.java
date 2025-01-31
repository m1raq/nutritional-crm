package ru.app.nutritionologycrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.meet.MeetCreateRequestDTO;
import ru.app.nutritionologycrm.dto.meet.MeetDTO;
import ru.app.nutritionologycrm.dto.meet.MeetUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.MeetEntity;
import ru.app.nutritionologycrm.exception.EntityProcessingException;
import ru.app.nutritionologycrm.mapper.ClientMapper;
import ru.app.nutritionologycrm.mapper.MeetMapper;
import ru.app.nutritionologycrm.repository.MeetRepository;
import ru.app.nutritionologycrm.service.ClientService;
import ru.app.nutritionologycrm.service.MeetService;
import ru.app.nutritionologycrm.service.UserService;

import java.util.List;

@Slf4j
@Service
public class MeetServiceImpl implements MeetService {

    private final MeetRepository meetRepository;

    private final MeetMapper meetMapper;

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    private final UserService userService;


    @Autowired
    public MeetServiceImpl(MeetRepository meetRepository, MeetMapper meetMapper, ClientService clientService
            , ClientMapper clientMapper, UserService userService) {
        this.meetRepository = meetRepository;
        this.meetMapper = meetMapper;
        this.clientService = clientService;
        this.clientMapper = clientMapper;
        this.userService = userService;
    }


    @Override
    public void save(MeetCreateRequestDTO request, Long clientId, String userUsername) throws Exception {
        log.info("Attempt to create meet");

        if (meetRepository.existsByStartAndEndAndUserUsername(request.getStart(), request.getEnd(), userUsername)) {
            throw new Exception("Meet with this date already exists");
        }

        MeetEntity meet = new MeetEntity();
        meet.setStart(request.getStart());
        meet.setEnd(request.getEnd());
        meet.setDuration(request.getDuration());
        meet.setPlace(request.getPlace());
        meet.setClient(clientMapper.toEntity(clientService.findById(clientId)));
        meet.setUser(userService.findByUsername(userUsername));

        meetRepository.save(meet);
    }

    @Override
    public void update(MeetUpdateRequestDTO updates, String userUsername) {
        log.info("Attempt to update meet {}", updates.getId());

        MeetEntity meet = meetRepository.findById(updates.getId())
                .orElseThrow(() -> new EntityProcessingException("Meet with this id does not exist"));

        meet.setStart(updates.getStart());
        meet.setEnd(updates.getEnd());
        meet.setDuration(updates.getDuration());
        meet.setPlace(updates.getPlace());
        meetRepository.save(meet);
    }

    @Override
    public List<MeetDTO> findAllByClientId(Long id) {
        log.info("Attempt to find all meet for client {}", id);
        return meetRepository.findAllByClientId(id)
                .stream()
                .map(meetMapper::toDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        log.info("Attempt to delete meet {}", id);
        meetRepository.deleteById(id);
    }
}
