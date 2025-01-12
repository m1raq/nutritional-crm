package ru.app.nutritionologycrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.ClientCreateRequestDTO;
import ru.app.nutritionologycrm.entity.ClientEntity;
import ru.app.nutritionologycrm.exception.ClientProcessingException;
import ru.app.nutritionologycrm.repository.ClientRepository;
import ru.app.nutritionologycrm.service.ClientService;
import ru.app.nutritionologycrm.service.UserService;

import java.util.List;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final UserService userService;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, UserService userService) {
        this.clientRepository = clientRepository;
        this.userService = userService;
    }

    @Override
    public void saveClient(ClientCreateRequestDTO request) {
        log.info("Attempt to save client");
        if (clientRepository.existsByContacts(request.getContacts())) {
            throw new ClientProcessingException("Client with contacts "
                    + request.getContacts() + "already exists");
        }

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(request.getName());
        clientEntity.setContacts(request.getContacts());
        clientEntity.setStatus(request.getStatus());
        clientEntity.setSex(request.getSex());
        clientEntity.setTgUrl(request.getTgUrl());
        clientEntity.setAge(request.getAge());
        clientEntity.setUser(userService.findByUsername(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName()));

        clientRepository.save(clientEntity);
    }

    @Override
    public void updateClient(ClientEntity clientEntity) {
        log.info("Attempt to update client {}", clientEntity.getId());
        if (!clientRepository.existsById(clientEntity.getId())) {
            throw new ClientProcessingException("Client with id " + clientEntity.getId() + " doesn't exist");
        }
        clientRepository.save(clientEntity);
    }

    @Override
    public void updateClientStatus(Long id, String status) {
        log.info("Attempt to update client {} status", id);
        if (!clientRepository.existsById(id)) {
            throw new ClientProcessingException("Client with id " + id + " doesn't exist");
        }
        clientRepository.updateStatusById(id, status);
    }

    @Override
    public List<ClientEntity> findAllByCurrentUser() {
        return clientRepository.findAllByUserUsername(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());
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

    @Override
    public List<ClientEntity> findByName(String name) {
        return clientRepository.findByName(name);
    }
}
