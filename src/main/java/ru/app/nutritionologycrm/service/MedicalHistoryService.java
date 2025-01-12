package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryCreateRequestDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.MedicalHistoryEntity;

import java.util.List;

public interface MedicalHistoryService {

    void saveMedicalHistory(MedicalHistoryCreateRequestDTO medicalHistory, Long clientId);

    void updateMedicalHistory(MedicalHistoryUpdateRequestDTO medicalHistory);

    String createMedicalHistoryQuestionnaire();

    List<MedicalHistoryEntity> findAllMedicalHistoriesByCurrentUser();

    List<MedicalHistoryEntity> findAllByClientId(Long clientId);

    MedicalHistoryEntity findById(Long id);

}
