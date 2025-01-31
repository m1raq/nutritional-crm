package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.dto.biomarker.BiomarkerCreateRequestDTO;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerDTO;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerUpdateRequestDTO;


import java.util.List;

public interface BiomarkerService {

    void saveBiomarker(BiomarkerCreateRequestDTO request, Long clientId);

    void updateBiomarker(BiomarkerUpdateRequestDTO updates);

    BiomarkerDTO findBiomarkerById(Long id);

    List<BiomarkerDTO> findAllBiomarkersByClientId(Long clientId);

}
