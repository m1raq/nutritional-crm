package ru.app.nutritionologycrm.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.entity.MedicalHistoryEntity;
import ru.app.nutritionologycrm.repository.MedicalHistoryRepository;
import ru.app.nutritionologycrm.service.GoogleFormsService;
import ru.app.nutritionologycrm.service.MedicalHistoryService;

import java.util.List;

@Slf4j
@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final GoogleFormsService googleFormsService;

    private final MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    public MedicalHistoryServiceImpl(GoogleFormsService googleFormsService, MedicalHistoryRepository medicalHistoryRepository) {
        this.googleFormsService = googleFormsService;
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    @Override
    public void saveMedicalHistory(MedicalHistoryEntity medicalHistory) {
        medicalHistoryRepository.save(medicalHistory);
    }

    @Override
    public void updateMedicalHistory(MedicalHistoryEntity medicalHistory) {
        medicalHistoryRepository.save(medicalHistory);
    }

    @Override
    public void createMedicalHistoryQuestionnaire() {
        googleFormsService.createQuestion(googleFormsService
                .createForm("asd", "Анамнез")
                , List.of("Антропометрия", "Режим жизни", "Жалобы", "Гипотезы"
                        , "Питание", "Питание", "Питьевой режим", "Физ.активность"
                        , ""));
    }
}
