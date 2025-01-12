package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.entity.BiomarkerEntity;

import java.util.List;

public interface BiomarkerService {

    void saveBiomarker(BiomarkerEntity biomarker);

    List<BiomarkerEntity> findAllBiomarkers();

    List<BiomarkerEntity> findBiomarkersByClientContacts(String contacts);

}
