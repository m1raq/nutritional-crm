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
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerCreateRequestDTO;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerDTO;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerUpdateRequestDTO;
import ru.app.nutritionologycrm.service.BiomarkerService;

import java.util.List;

@SecurityRequirement(name = "JWT")
@Tag(name = "Биомаркеры")
@RequestMapping("/api/v1/biomarker")
@RestController
public class BiomarkerController {

    private final BiomarkerService biomarkerService;

    @Autowired
    public BiomarkerController(BiomarkerService biomarkerService) {
        this.biomarkerService = biomarkerService;
    }

    @Operation(summary = "Создание биомаркера")
    @PostMapping("/create")
    public ResponseEntity<ResponseMessageDTO> create(@RequestBody BiomarkerCreateRequestDTO request
            , @RequestParam Long clientId) {
        biomarkerService.saveBiomarker(request, clientId);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .success(true)
                .message("Biomarker is created")
                .build(), HttpStatus.CREATED);
    }


    @Operation(summary = "Обновление биомаркера")
    @PutMapping("/update")
    public ResponseEntity<ResponseMessageDTO> update(@RequestBody BiomarkerUpdateRequestDTO request) {
        biomarkerService.updateBiomarker(request);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .success(true)
                .message("Biomarker is updated")
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "Получение всех биомаркеров текущего юзера")
    @GetMapping("/get-all")
    public ResponseEntity<List<BiomarkerDTO>> getAll() {
        return new ResponseEntity<>(biomarkerService.findAllBiomarkersByCurrentUser(), HttpStatus.OK);
    }

    @Operation(summary = "Получение всех биомаркеров по id клиента"
            , parameters = {@Parameter(name = "clientId", description = "Id клиента")})
    @GetMapping("/get-by-client-id")
    public ResponseEntity<List<BiomarkerDTO>> getByClientId(@RequestParam Long clientId) {
        return new ResponseEntity<>(biomarkerService.findAllBiomarkersByClientId(clientId), HttpStatus.OK);
    }

}
