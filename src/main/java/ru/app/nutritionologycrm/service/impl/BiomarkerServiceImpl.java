package ru.app.nutritionologycrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerCreateRequestDTO;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.BiomarkerEntity;
import ru.app.nutritionologycrm.exception.EntityProcessingException;
import ru.app.nutritionologycrm.repository.BiomarkerRepository;
import ru.app.nutritionologycrm.service.BiomarkerService;

import java.util.List;

@Slf4j
@Service
public class BiomarkerServiceImpl implements BiomarkerService {

    private final BiomarkerRepository biomarkerRepository;

    @Autowired
    public BiomarkerServiceImpl(BiomarkerRepository biomarkerRepository) {
        this.biomarkerRepository = biomarkerRepository;
    }

    @Override
    public void saveBiomarker(BiomarkerCreateRequestDTO request) {
        log.info("Attempt to save biomarker");
        BiomarkerEntity biomarker = new BiomarkerEntity();

        biomarker.setName(request.getName());
        biomarker.setValue(request.getValue());
        biomarker.setNormalValue(request.getNormalValue());
        biomarker.setClinicalReferences(request.getClinicalReferences());
        biomarker.setNutritionist(request.getNutritionist());
        biomarker.setUnit(request.getUnit());

        biomarkerRepository.save(biomarker);
    }

    @Override
    public void updateBiomarker(BiomarkerUpdateRequestDTO updates) {
        log.info("Attempt to update biomarker {}", updates.getId());

        BiomarkerEntity biomarker =  biomarkerRepository.findById(updates.getId())
                .orElseThrow(() -> new EntityProcessingException("Biomarker with id " + updates.getId()
                        + " doesn't exists"));
        biomarker.setName(updates.getName());
        biomarker.setValue(updates.getValue());
        biomarker.setNutritionist(updates.getNutritionist());
        biomarker.setClinicalReferences(updates.getClinicalReferences());
        biomarker.setUnit(updates.getUnit());
        biomarkerRepository.save(biomarker);
    }

    //TODO: Доработать сортировку по дате
    @Override
    public List<BiomarkerEntity> findAllBiomarkersByCurrentUser() {
        return biomarkerRepository.findAllByUserUsername(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());
    }

    @Override
    public List<BiomarkerEntity> findAllBiomarkersByClientId(Long clientId) {
        return biomarkerRepository.findAllByClientId(clientId);
    }

}
