package ru.app.nutritionologycrm.service.impl;

import com.google.api.services.forms.v1.Forms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.entity.MedicalHistoryEntity;
import ru.app.nutritionologycrm.repository.MedicalHistoryRepository;
import ru.app.nutritionologycrm.service.MedicalHistoryService;

@Slf4j
@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    public MedicalHistoryServiceImpl(MedicalHistoryRepository medicalHistoryRepository, Forms formsService) {
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.formsService = formsService;
    }

    @Override
    public void saveMedicalHistory(MedicalHistoryEntity medicalHistory) {
        medicalHistoryRepository.save(medicalHistory);
    }

    @Override
    public void updateMedicalHistory(MedicalHistoryEntity medicalHistory) {

    }

    @Override
    public void createMedicalHistoryQuestionnaire() {

    }
}
