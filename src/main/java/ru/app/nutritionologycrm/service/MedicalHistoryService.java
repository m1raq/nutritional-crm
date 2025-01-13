package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryCreateRequestDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryUpdateRequestDTO;


import java.util.List;

public interface MedicalHistoryService {

    void saveMedicalHistory(MedicalHistoryCreateRequestDTO medicalHistory, Long clientId);

    void updateMedicalHistory(MedicalHistoryUpdateRequestDTO medicalHistory);

    String createMedicalHistoryQuestionnaire();

    List<MedicalHistoryDTO> findAllMedicalHistoriesByCurrentUser();

    List<MedicalHistoryDTO> findAllByClientId(Long clientId);

    MedicalHistoryDTO findById(Long id);

}
