package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.entity.MedicalHistoryEntity;

public interface MedicalHistoryService {

    void saveMedicalHistory(MedicalHistoryEntity medicalHistory);

    void updateMedicalHistory(MedicalHistoryEntity medicalHistory);

    void createMedicalHistoryQuestionnaire();

}
