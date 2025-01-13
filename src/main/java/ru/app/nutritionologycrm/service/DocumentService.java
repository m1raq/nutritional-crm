package ru.app.nutritionologycrm.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.app.nutritionologycrm.dto.DocumentDTO;


import java.util.Date;
import java.util.List;

public interface DocumentService {

    void save(MultipartFile file, Long clientId);

    Resource loadFile(String fileName);

    List<DocumentDTO> getAllFiles();

    DocumentDTO findByNameAndCurrentUser(String name);

    List<DocumentDTO> findByDateAndCurrentUser(Date date);


}
