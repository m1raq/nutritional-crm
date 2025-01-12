package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.dto.client.ClientCreateRequestDTO;
import ru.app.nutritionologycrm.dto.client.ClientUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.ClientEntity;

import java.util.List;

public interface ClientService {

    void saveClient(ClientCreateRequestDTO request);

    void updateClient(ClientUpdateRequestDTO update);

    void updateClientStatus(Long id, String status);

    List<ClientEntity> findAllByCurrentUser();

    ClientEntity findByContacts(String contacts);

    List<ClientEntity> findByAge(Integer age);

    List<ClientEntity> findByName(String name);

    ClientEntity findById(Long id);
}
