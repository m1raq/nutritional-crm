package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.dto.biomarker.BiomarkerCreateRequestDTO;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.BiomarkerEntity;

import java.util.List;

public interface BiomarkerService {

    void saveBiomarker(BiomarkerCreateRequestDTO request);

    void updateBiomarker(BiomarkerUpdateRequestDTO updates);

    List<BiomarkerEntity> findAllBiomarkersByCurrentUser();

    List<BiomarkerEntity> findAllBiomarkersByClientId(Long clientId);

}
