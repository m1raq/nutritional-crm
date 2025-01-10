package ru.app.nutritionologycrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.ClientUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.ClientEntity;
import ru.app.nutritionologycrm.repository.ClientRepository;
import ru.app.nutritionologycrm.service.ClientService;

import java.util.List;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void saveClient(ClientEntity clientEntity) {
        log.info("Attempt to save client");
        clientRepository.save(clientEntity);
    }

    @Override
    public void updateClient(Long id, ClientUpdateRequestDTO updates) {
        //TODO: РЕАЛИЗОВАТЬ
    }

    @Override
    public ClientEntity findByContacts(String contacts) {
        log.info("Attempt to find client by contacts {}", contacts);
        return clientRepository.findByContacts(contacts);
    }

    @Override
    public List<ClientEntity> findByAge(Integer age) {
        log.info("Attempt to find clients by age {}", age);
        return clientRepository.findAllByAge(age);
    }
}
