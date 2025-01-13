package ru.app.nutritionologycrm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.app.nutritionologycrm.dto.ResponseMessageDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryCreateRequestDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryUpdateRequestDTO;
import ru.app.nutritionologycrm.service.MedicalHistoryService;

import java.util.List;

@Tag(name = "Анамнез")
@SecurityRequirement(name = "JWT")
@RequestMapping("/api/v1/medical-history")
@RestController
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;

    @Autowired
    public MedicalHistoryController(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    @Operation(summary = "Создание анамнеза")
    @PostMapping("/create")
    public ResponseEntity<ResponseMessageDTO> createMedicalHistory(@RequestBody MedicalHistoryCreateRequestDTO request
            , @RequestParam Long clientId ) {
        medicalHistoryService.saveMedicalHistory(request, clientId);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Medical history is created")
                .success(true)
                .build(), HttpStatus.CREATED);
    }

    @Operation(summary = "Обновление анамнеза")
    @PutMapping("/update")
    public ResponseEntity<ResponseMessageDTO> updateMedicalHistory(@RequestBody MedicalHistoryUpdateRequestDTO request) {
        medicalHistoryService.updateMedicalHistory(request);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Medical history is updated")
                .success(true)
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "Получение всех анамнезов текущего юзера")
    @GetMapping("/get-all")
    public ResponseEntity<List<MedicalHistoryDTO>> getAllMedicalHistory() {
        return new ResponseEntity<>(medicalHistoryService.findAllMedicalHistoriesByCurrentUser(), HttpStatus.OK);
    }

    @Operation(summary = "Получение анамнезов по id клиента"
            , parameters = {@Parameter(name = "clientId", description = "Id клиента")})
    @GetMapping("/get-by-client-id")
    public ResponseEntity<List<MedicalHistoryDTO>> getMedicalHistory(@RequestParam Long clientId) {
        return new ResponseEntity<>(medicalHistoryService.findAllByClientId(clientId), HttpStatus.OK);
    }

    @Operation(summary = "Получение анамнеза по id")
    @GetMapping("/get-by-id")
    public ResponseEntity<MedicalHistoryDTO> getMedicalHistoryById(@RequestParam Long id) {
        return new ResponseEntity<>(medicalHistoryService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Создать Google форму", description = "Форма для опроса. " +
            "В ответе, в поле features лежит URL на форму")
    @PostMapping("/create-google-form")
    public ResponseEntity<ResponseMessageDTO> createGoogleForm() {
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Google form is created")
                .features(medicalHistoryService.createMedicalHistoryQuestionnaire())
                .success(true)
                .build(), HttpStatus.CREATED);
    }

}
