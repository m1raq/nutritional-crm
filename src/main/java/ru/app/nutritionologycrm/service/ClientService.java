package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.dto.client.ClientCreateRequestDTO;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.dto.client.ClientUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.ClientEntity;


import java.util.List;

public interface ClientService {

    void saveClient(ClientCreateRequestDTO request);

    void updateClient(ClientUpdateRequestDTO update);

    void updateClient(ClientDTO update);

    ClientDTO findByTgBotChatId(String tgBotChatId);

    Boolean existsByTgBotChatId(String tgBotChatId);

    void updateClient(ClientEntity update);

    List<ClientDTO> findAllByCurrentUser();

    ClientDTO findById(Long id);
}
