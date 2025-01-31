package ru.app.nutritionologycrm.service;


import ru.app.nutritionologycrm.dto.DocumentDTO;
import ru.app.nutritionologycrm.entity.DocumentEntity;

import java.util.List;

public interface DocumentService {

    void save(DocumentEntity file, Long clientId);

    List<DocumentDTO> findAllByUserUsernameAndAndClientId(String username, Long clientId);


}
