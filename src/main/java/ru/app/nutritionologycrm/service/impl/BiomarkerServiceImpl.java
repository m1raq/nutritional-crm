package ru.app.nutritionologycrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerCreateRequestDTO;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerDTO;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.BiomarkerEntity;
import ru.app.nutritionologycrm.exception.EntityProcessingException;
import ru.app.nutritionologycrm.mapper.BiomarkerMapper;
import ru.app.nutritionologycrm.mapper.ClientMapper;
import ru.app.nutritionologycrm.repository.BiomarkerRepository;
import ru.app.nutritionologycrm.service.BiomarkerService;
import ru.app.nutritionologycrm.service.ClientService;
import ru.app.nutritionologycrm.service.UserService;

import java.util.List;


@Slf4j
@Service
public class BiomarkerServiceImpl implements BiomarkerService {

    private final BiomarkerRepository biomarkerRepository;

    private final BiomarkerMapper biomarkerMapper;

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    private final UserService userService;

    @Autowired
    public BiomarkerServiceImpl(BiomarkerRepository biomarkerRepository, BiomarkerMapper biomarkerMapper
            , ClientService clientService, ClientMapper clientMapper, UserService userService) {
        this.biomarkerRepository = biomarkerRepository;
        this.biomarkerMapper = biomarkerMapper;
        this.clientService = clientService;
        this.clientMapper = clientMapper;
        this.userService = userService;
    }

    @Override
    public void saveBiomarker(BiomarkerCreateRequestDTO request, Long clientId) {
        log.info("Attempt to save biomarker");
        BiomarkerEntity biomarker = new BiomarkerEntity();

        biomarker.setName(request.getName());
        biomarker.setValue(request.getValue());
        biomarker.setNormalValue(request.getNormalValue());
        biomarker.setClinicalReferences(request.getClinicalReferences());
        biomarker.setNutritionist(request.getNutritionistReferences());
        biomarker.setUnit(request.getUnit());
        biomarker.setDate(request.getDate());
        biomarker.setClient(clientMapper.toEntity(clientService.findById(clientId)));
        biomarker.setUser(userService.findByUsername(SecurityContextHolder.getContext().
                getAuthentication()
                .getName()));

        biomarkerRepository.save(biomarker);
    }

    @Override
    public void updateBiomarker(BiomarkerUpdateRequestDTO updates) {
        log.info("Attempt to update biomarker {}", updates.getId());

        BiomarkerEntity biomarker =  biomarkerRepository.findById(updates.getId())
                .orElseThrow(() -> new EntityProcessingException("Biomarker with id " + updates.getId()
                        + " doesn't exists"));
        biomarker.setName(updates.getName());
        biomarker.setValue(updates.getValue());
        biomarker.setNutritionist(updates.getNutritionist());
        biomarker.setClinicalReferences(updates.getClinicalReferences());
        biomarker.setUnit(updates.getUnit());
        biomarkerRepository.save(biomarker);
    }

    @Override
    public BiomarkerDTO findBiomarkerById(Long id) {
        return biomarkerMapper.toDTO(biomarkerRepository.findById(id)
                .orElseThrow(()
                        -> new EntityProcessingException("Biomarker with id " + id + " doesn't exists")));
    }


    @Override
    public List<BiomarkerDTO> findAllBiomarkersByClientId(Long clientId) {
        return biomarkerRepository.findAllByClientId(clientId).stream()
                .map(biomarkerMapper::toDTO)
                .toList();
    }

}
