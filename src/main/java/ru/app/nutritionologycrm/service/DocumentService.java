package ru.app.nutritionologycrm.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.app.nutritionologycrm.entity.DocumentEntity;

import java.util.Date;
import java.util.List;

public interface DocumentService {

    void save(MultipartFile file, Long clientId);

    Resource loadFile(String fileName);

    List<DocumentEntity> getAllFiles();

    DocumentEntity findByNameAndCurrentUser(String name);

    List<DocumentEntity> findByDateAndCurrentUser(Date date);


}
