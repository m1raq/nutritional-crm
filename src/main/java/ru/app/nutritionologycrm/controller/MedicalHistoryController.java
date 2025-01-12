package ru.app.nutritionologycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.app.nutritionologycrm.dto.ResponseMessage;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryCreateRequestDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.MedicalHistoryEntity;
import ru.app.nutritionologycrm.service.MedicalHistoryService;

import java.util.List;

@RequestMapping("/api/v1/medical-history")
@RestController
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;

    @Autowired
    public MedicalHistoryController(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createMedicalHistory(@RequestBody MedicalHistoryCreateRequestDTO request
            , @RequestParam Long clientId ) {
        medicalHistoryService.saveMedicalHistory(request, clientId);
        return new ResponseEntity<>(ResponseMessage.builder()
                .message("Medical history is created")
                .success(true)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> updateMedicalHistory(@RequestBody MedicalHistoryUpdateRequestDTO request) {
        medicalHistoryService.updateMedicalHistory(request);
        return new ResponseEntity<>(ResponseMessage.builder()
                .message("Medical history is updated")
                .success(true)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<MedicalHistoryEntity>> getAllMedicalHistory() {
        return new ResponseEntity<>(medicalHistoryService.findAllMedicalHistoriesByCurrentUser(), HttpStatus.OK);
    }

    @GetMapping("/get-by-client-id")
    public ResponseEntity<List<MedicalHistoryEntity>> getMedicalHistory(@RequestParam Long clientId) {
        return new ResponseEntity<>(medicalHistoryService.findAllByClientId(clientId), HttpStatus.OK);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<MedicalHistoryEntity> getMedicalHistoryById(@RequestParam Long id) {
        return new ResponseEntity<>(medicalHistoryService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create-google-form")
    public ResponseEntity<ResponseMessage> createGoogleForm() {
        return new ResponseEntity<>(ResponseMessage.builder()
                .message("Google form is created")
                .features(medicalHistoryService.createMedicalHistoryQuestionnaire())
                .success(true)
                .build(), HttpStatus.CREATED);
    }

}
