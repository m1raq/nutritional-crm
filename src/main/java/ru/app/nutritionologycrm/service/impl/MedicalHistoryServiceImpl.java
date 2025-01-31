package ru.app.nutritionologycrm.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.MedicalHistoryEntity;
import ru.app.nutritionologycrm.exception.EntityProcessingException;
import ru.app.nutritionologycrm.mapper.MedicalHistoryMapper;
import ru.app.nutritionologycrm.repository.MedicalHistoryRepository;
import ru.app.nutritionologycrm.service.MedicalHistoryService;

import java.util.List;

@Slf4j
@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;

    private final MedicalHistoryMapper medicalHistoryMapper;


    @Autowired
    public MedicalHistoryServiceImpl(MedicalHistoryRepository medicalHistoryRepository
            , MedicalHistoryMapper medicalHistoryMapper) {
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.medicalHistoryMapper = medicalHistoryMapper;
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
    public List<MedicalHistoryDTO> findAllByClientId(Long clientId) {
        return medicalHistoryRepository.findAllByClientId(clientId)
                .stream()
                .map(medicalHistoryMapper::toDTO)
                .toList();
    }

    @Override
    public MedicalHistoryDTO findByClientId(Long clientId) {
        return medicalHistoryMapper.toDTO(medicalHistoryRepository.findByClientId(clientId));
    }

    @Override
    public MedicalHistoryDTO findById(Long id) {
        return medicalHistoryMapper.toDTO(medicalHistoryRepository.findById(id)
                .orElseThrow(() -> new EntityProcessingException("Medical history with id "
                        + id + " not found")));
    }
}
