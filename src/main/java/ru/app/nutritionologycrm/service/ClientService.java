package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.dto.ClientUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.ClientEntity;

import java.util.List;

public interface ClientService {

    void saveClient(ClientEntity clientEntity);

    void updateClient(Long id, ClientUpdateRequestDTO updates);

    ClientEntity findByContacts(String contacts);

    List<ClientEntity> findByAge(Integer age);
}
