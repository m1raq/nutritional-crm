package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.dto.client.ClientCreateRequestDTO;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.dto.client.ClientUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.ClientEntity;


import java.util.List;

public interface ClientService {

    void saveClient(ClientCreateRequestDTO request);

    void updateClient(ClientUpdateRequestDTO update);

    void updateClient(ClientEntity update);

    void updateClientStatus(Long id, Boolean status);

    List<ClientDTO> findAllByCurrentUser();

    ClientDTO findByContacts(String contacts);

    List<ClientDTO> findByAge(Integer age);

    List<ClientDTO> findByName(String name);

    ClientDTO findById(Long id);
}
