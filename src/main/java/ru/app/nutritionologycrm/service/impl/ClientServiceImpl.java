package ru.app.nutritionologycrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.client.ClientCreateRequestDTO;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.dto.client.ClientUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.ClientEntity;
import ru.app.nutritionologycrm.exception.EntityProcessingException;
import ru.app.nutritionologycrm.mapper.ClientMapper;
import ru.app.nutritionologycrm.repository.ClientRepository;
import ru.app.nutritionologycrm.service.ClientService;
import ru.app.nutritionologycrm.service.UserService;

import java.util.List;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final UserService userService;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper
            , UserService userService) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.userService = userService;
    }

    @Override
    public void saveClient(ClientCreateRequestDTO request) {
        log.info("Attempt to save client");
        if (clientRepository.existsByContacts(request.getContacts())) {
            throw new EntityProcessingException("Client with contacts "
                    + request.getContacts() + "already exists");
        }

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(request.getName());
        clientEntity.setContacts(request.getContacts());
        clientEntity.setStatus(request.getStatus());
        clientEntity.setSex(request.getSex());
        clientEntity.setTgUrl(request.getTgUrl());
        clientEntity.setAge(request.getAge());
        clientEntity.setUser(userService
                .findByUsername(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName()));

        clientRepository.save(clientEntity);

    }
    @Override
    public void updateClient(ClientUpdateRequestDTO update) {
        log.info("Attempt to update client {}", update.getId());
        if (!clientRepository.existsById(update.getId())) {
            throw new EntityProcessingException("Client with id " + update.getId() + " doesn't exist");
        }

        ClientEntity clientToUpdate = clientMapper.toEntity(findById(update.getId()));
        clientToUpdate.setName(update.getName());
        clientToUpdate.setAge(update.getAge());
        clientToUpdate.setSex(update.getSex());
        clientToUpdate.setStatus(update.getStatus());
        clientToUpdate.setContacts(update.getContacts());
        clientToUpdate.setTgUrl(update.getTgUrl());
        clientToUpdate.setUser(userService.findByUsername(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName()));

        clientRepository.save(clientToUpdate);
    }

    @Override
    public void updateClient(ClientEntity update) {
        log.info("Attempt to update client {}", update.getId());
        clientRepository.save(update);
    }

    @Override
    public void updateClientStatus(Long id, Boolean status) {
        log.info("Attempt to update client {} status", id);
        if (!clientRepository.existsById(id)) {
            throw new EntityProcessingException("Client with id " + id + " doesn't exist");
        }
        clientRepository.updateStatusById(id, status);
    }

    @Override
    public List<ClientDTO> findAllByCurrentUser() {
        log.info("Attempt to find all clients by user {}", SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());
        return clientRepository.findAllByUserUsername(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName())
                .stream()
                .map(clientMapper::toDTO)
                .toList();
    }

    @Override
    public ClientDTO findByContacts(String contacts) {
        log.info("Attempt to find client by contacts {}", contacts);
        return clientMapper.toDTO(clientRepository.findByContacts(contacts));
    }

    @Override
    public List<ClientDTO> findByAge(Integer age) {
        log.info("Attempt to find clients by age {}", age);
        return clientRepository.findAllByAge(age)
                .stream()
                .map(clientMapper::toDTO)
                .toList();
    }

    @Override
    public List<ClientDTO> findByName(String name) {
        log.info("Attempt to find client by name {}", name);
        return clientRepository.findAllByNameAndUserUsername(name, SecurityContextHolder.getContext()
                .getAuthentication()
                .getName())
                .stream()
                .map(clientMapper::toDTO)
                .toList();
    }

    @Override
    public ClientDTO findById(Long id) {
        log.info("Attempt to find client by id {}", id);
        return clientMapper.toDTO(clientRepository.findById(id)
                .orElseThrow(()-> new EntityProcessingException("Client with id " + id + " doesn't exist")));
    }
}
