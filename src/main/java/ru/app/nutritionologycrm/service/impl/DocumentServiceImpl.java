package ru.app.nutritionologycrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.DocumentDTO;
import ru.app.nutritionologycrm.entity.DocumentEntity;
import ru.app.nutritionologycrm.mapper.ClientMapper;
import ru.app.nutritionologycrm.mapper.DocumentMapper;
import ru.app.nutritionologycrm.repository.DocumentRepository;
import ru.app.nutritionologycrm.service.ClientService;
import ru.app.nutritionologycrm.service.DocumentService;
import ru.app.nutritionologycrm.service.UserService;

import java.util.List;


@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final ClientService clientService;

    private final UserService userService;

    private final ClientMapper clientMapper;

    private final DocumentMapper documentMapper;


    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, ClientService clientService
            , UserService userService, ClientMapper clientMapper, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.clientService = clientService;
        this.userService = userService;
        this.clientMapper = clientMapper;
        this.documentMapper = documentMapper;
    }

    @Override
    public void save(DocumentEntity file, Long clientId) {
        file.setClient(clientMapper.toEntity(clientService.findById(clientId)));
        file.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        documentRepository.save(file);
    }

    @Override
    public List<DocumentDTO> findAllByUserUsernameAndAndClientId(String username, Long clientId) {
        return documentRepository.findAllByUserUsernameAndClientId(username, clientId)
                .stream()
                .map(documentMapper::toDTO)
                .toList();
    }



}
