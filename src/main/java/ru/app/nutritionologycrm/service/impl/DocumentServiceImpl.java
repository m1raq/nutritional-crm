package ru.app.nutritionologycrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.app.nutritionologycrm.entity.DocumentEntity;
import ru.app.nutritionologycrm.exception.DocumentProcessingException;
import ru.app.nutritionologycrm.repository.DocumentRepository;
import ru.app.nutritionologycrm.service.ClientService;
import ru.app.nutritionologycrm.service.DocumentService;
import ru.app.nutritionologycrm.service.UserService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    private final Path fileStorageLocation;

    private final DocumentRepository documentRepository;

    private final ClientService clientService;

    private final UserService userService;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, ClientService clientService, UserService userService) {
        this.documentRepository = documentRepository;
        this.clientService = clientService;
        this.userService = userService;
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public void save(MultipartFile file, Long clientId) {
        log.info("Attempt to save file {}", file.getOriginalFilename());
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            DocumentEntity documentEntity = new DocumentEntity();
            documentEntity.setName(fileName);
            documentEntity.setClient(clientService.findById(clientId));
            documentEntity.setUser(userService.findByUsername(SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName()));

            documentRepository.save(documentEntity);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFile(String fileName) {
        log.info("Attempt to load file {}", fileName);
        try {
            if (documentRepository.existsByUserUsernameAndName(SecurityContextHolder.getContext()
                            .getAuthentication()
                            .getName()
                    , fileName)) {
                throw new DocumentProcessingException("File doesn't exist");
            }

            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }

    @Override
    public List<DocumentEntity> getAllFiles() {
        log.info("Attempt to get all files");
        return documentRepository.findAllByUserUsername(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());
    }

    @Override
    public DocumentEntity findByNameAndCurrentUser(String name) {
        return documentRepository.findByNameAndUserUsername(name, SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());
    }

    @Override
    public List<DocumentEntity> findByDateAndCurrentUser(Date date) {
        return documentRepository.findByDateAndUserUsername(date, SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());
    }

}
