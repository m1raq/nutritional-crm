package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryUpdateRequestDTO;


import java.util.List;

public interface MedicalHistoryService {

    void updateMedicalHistory(MedicalHistoryUpdateRequestDTO medicalHistory);

    List<MedicalHistoryDTO> findAllByClientId(Long clientId);

    MedicalHistoryDTO findByClientId(Long clientId);

    MedicalHistoryDTO findById(Long id);

}
