package ru.app.nutritionologycrm.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryCreateRequestDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.MedicalHistoryEntity;
import ru.app.nutritionologycrm.exception.EntityProcessingException;
import ru.app.nutritionologycrm.mapper.ClientMapper;
import ru.app.nutritionologycrm.mapper.MedicalHistoryMapper;
import ru.app.nutritionologycrm.repository.MedicalHistoryRepository;
import ru.app.nutritionologycrm.service.ClientService;
import ru.app.nutritionologycrm.service.GoogleFormsService;
import ru.app.nutritionologycrm.service.MedicalHistoryService;

import java.util.List;

@Slf4j
@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final GoogleFormsService googleFormsService;

    private final MedicalHistoryRepository medicalHistoryRepository;

    private final MedicalHistoryMapper medicalHistoryMapper;

    private final ClientMapper clientMapper;

    private final ClientService clientService;

    @Autowired
    public MedicalHistoryServiceImpl(GoogleFormsService googleFormsService
            , MedicalHistoryRepository medicalHistoryRepository, MedicalHistoryMapper medicalHistoryMapper
            , ClientMapper clientMapper, ClientService clientService) {
        this.googleFormsService = googleFormsService;
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.medicalHistoryMapper = medicalHistoryMapper;
        this.clientMapper = clientMapper;
        this.clientService = clientService;
    }

    @Override
    public void saveMedicalHistory(MedicalHistoryCreateRequestDTO request, Long clientId) {
        log.info("Attempting to save medical history");

        MedicalHistoryEntity medicalHistoryEntity = new MedicalHistoryEntity();
        medicalHistoryEntity.setClient(clientMapper.toEntity(clientService.findById(clientId)));
        medicalHistoryEntity.setAnthropometry(request.getAnthropometry());
        medicalHistoryEntity.setGoals(request.getGoals());
        medicalHistoryEntity.setHypotheses(request.getGoals());
        medicalHistoryEntity.setLifeMode(request.getLifeMode());
        medicalHistoryEntity.setComplaints(request.getComplaints());
        medicalHistoryEntity.setNutrition(request.getNutrition());
        medicalHistoryEntity.setDrinkingMode(request.getDrinkingMode());
        medicalHistoryEntity.setPhysicalActivity(request.getPhysicalActivity());
        medicalHistoryEntity.setSpecialConditions(request.getSpecialConditions());


        medicalHistoryRepository.save(medicalHistoryEntity);
    }

    @Override
    public void updateMedicalHistory(MedicalHistoryUpdateRequestDTO updates) {
        MedicalHistoryEntity medicalHistoryToUpdate
                = medicalHistoryRepository.findById(updates.getId())
                .orElseThrow(() -> new EntityProcessingException("Medical history with id "
                        + updates.getId() + " not found"));
        medicalHistoryToUpdate.setAnthropometry(updates.getAnthropometry());
        medicalHistoryToUpdate.setGoals(updates.getGoals());
        medicalHistoryToUpdate.setHypotheses(updates.getGoals());
        medicalHistoryToUpdate.setLifeMode(updates.getLifeMode());
        medicalHistoryToUpdate.setComplaints(updates.getComplaints());
        medicalHistoryToUpdate.setNutrition(updates.getNutrition());
        medicalHistoryToUpdate.setDrinkingMode(updates.getDrinkingMode());
        medicalHistoryToUpdate.setPhysicalActivity(updates.getPhysicalActivity());
        medicalHistoryToUpdate.setSpecialConditions(updates.getSpecialConditions());

        medicalHistoryRepository.save(medicalHistoryToUpdate);
    }

    @Override
    public String createMedicalHistoryQuestionnaire() {
        return googleFormsService.createQuestion(googleFormsService
                .createForm("asd", "Анамнез")
                , List.of("Антропометрия", "Режим жизни", "Жалобы", "Гипотезы"
                        , "Питание", "Питание", "Питьевой режим", "Физ.активность"
                        , ""));
    }

    @Override
    public List<MedicalHistoryDTO> findAllMedicalHistoriesByCurrentUser() {
        log.info("Attempting to find all medical histories");
        return medicalHistoryRepository.findAllByClientUserUsername(SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName())
                .stream()
                .map(medicalHistoryMapper::toDTO)
                .toList();
    }

    @Override
    public List<MedicalHistoryDTO> findAllByClientId(Long clientId) {
        return medicalHistoryRepository.findAllByClientId(clientId)
                .stream()
                .map(medicalHistoryMapper::toDTO)
                .toList();
    }

    @Override
    public MedicalHistoryDTO findById(Long id) {
        return medicalHistoryMapper.toDTO(medicalHistoryRepository.findById(id)
                .orElseThrow(() -> new EntityProcessingException("Medical history with id "
                        + id + " not found")));
    }
}
