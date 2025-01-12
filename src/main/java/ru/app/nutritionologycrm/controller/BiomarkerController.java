package ru.app.nutritionologycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.app.nutritionologycrm.dto.ResponseMessage;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerCreateRequestDTO;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.BiomarkerEntity;
import ru.app.nutritionologycrm.service.BiomarkerService;

import java.util.List;

@RequestMapping("/biomarker")
@RestController
public class BiomarkerController {

    private final BiomarkerService biomarkerService;

    @Autowired
    public BiomarkerController(BiomarkerService biomarkerService) {
        this.biomarkerService = biomarkerService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> create(@RequestBody BiomarkerCreateRequestDTO request) {
        biomarkerService.saveBiomarker(request);
        return new ResponseEntity<>(ResponseMessage.builder()
                .success(true)
                .message("Biomarker is created")
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> update(@RequestBody BiomarkerUpdateRequestDTO request) {
        biomarkerService.updateBiomarker(request);
        return new ResponseEntity<>(ResponseMessage.builder()
                .success(true)
                .message("Biomarker is updated")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<BiomarkerEntity>> getAll() {
        return new ResponseEntity<>(biomarkerService.findAllBiomarkersByCurrentUser(), HttpStatus.OK);
    }

    @GetMapping("/get-by-client-id")
    public ResponseEntity<List<BiomarkerEntity>> getByClientId(@RequestParam Long clientId) {
        return new ResponseEntity<>(biomarkerService.findAllBiomarkersByClientId(clientId), HttpStatus.OK);
    }

}
