package ru.app.nutritionologycrm.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryCreateRequestDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.MedicalHistoryEntity;
import ru.app.nutritionologycrm.exception.MedicalHistoryProcessingException;
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

    private final ClientService clientService;

    @Autowired
    public MedicalHistoryServiceImpl(GoogleFormsService googleFormsService
            , MedicalHistoryRepository medicalHistoryRepository, ClientService clientService) {
        this.googleFormsService = googleFormsService;
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.clientService = clientService;
    }

    @Override
    public void saveMedicalHistory(MedicalHistoryCreateRequestDTO request, Long clientId) {
        log.info("Attempting to save medical history");

        MedicalHistoryEntity medicalHistoryEntity = new MedicalHistoryEntity();
        medicalHistoryEntity.setClient(clientService.findById(clientId));
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
                .orElseThrow(() -> new MedicalHistoryProcessingException("Medical history with id "
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
    public List<MedicalHistoryEntity> findAllMedicalHistoriesByCurrentUser() {
        log.info("Attempting to find all medical histories");
        return medicalHistoryRepository.findAllByClientUserUsername(SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName());
    }

    @Override
    public List<MedicalHistoryEntity> findAllByClientId(Long clientId) {
        return medicalHistoryRepository.findAllByClientId(clientId);
    }

    @Override
    public MedicalHistoryEntity findById(Long id) {
        return medicalHistoryRepository.findById(id)
                .orElseThrow(() -> new MedicalHistoryProcessingException("Medical history with id "
                        + id + " not found"));
    }
}
