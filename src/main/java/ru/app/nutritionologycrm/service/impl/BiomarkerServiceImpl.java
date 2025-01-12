package ru.app.nutritionologycrm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.entity.BiomarkerEntity;
import ru.app.nutritionologycrm.repository.BiomarkerRepository;
import ru.app.nutritionologycrm.service.BiomarkerService;

import java.util.List;

@Service
public class BiomarkerServiceImpl implements BiomarkerService {

    private final BiomarkerRepository biomarkerRepository;

    @Autowired
    public BiomarkerServiceImpl(BiomarkerRepository biomarkerRepository) {
        this.biomarkerRepository = biomarkerRepository;
    }


    @Override
    public void saveBiomarker(BiomarkerEntity biomarker) {
        biomarkerRepository.save(biomarker);
    }

    @Override
    public List<BiomarkerEntity> findAllBiomarkers() {
        return biomarkerRepository.findAll();
    }

    @Override
    public List<BiomarkerEntity> findBiomarkersByClientContacts(String contacts) {
        return biomarkerRepository.findAllByClientContacts(contacts);
    }
}
