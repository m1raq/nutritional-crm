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
import ru.app.nutritionologycrm.dto.recommendation.RecommendationCreateRequestDTO;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationDTO;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationUpdateRequestDTO;
import ru.app.nutritionologycrm.service.RecommendationService;

import java.util.List;

@Tag(name = "Рекоммендации для клиентов")
@SecurityRequirement(name = "JWT")
@RequestMapping("/api/v1/recommendation")
@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Operation(summary = "Создание рекоммендации"
            , parameters = {@Parameter(name = "clientId", description = "Id клиента, " +
            "которому будет присвоена рекомендация")})
    @PostMapping("/create")
    public ResponseEntity<ResponseMessageDTO> create(@RequestBody RecommendationCreateRequestDTO request
            , @RequestParam Long clientId) {
        recommendationService.save(request, clientId);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Recommendation is created")
                .success(true)
                .build(), HttpStatus.CREATED);
    }

    @Operation(summary = "Обновление рекомендации")
    @PutMapping("/update")
    public ResponseEntity<ResponseMessageDTO> update(@RequestBody RecommendationUpdateRequestDTO request) {
        recommendationService.update(request);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Recommendation is updated")
                .success(true)
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "Получение рекомендации по id клиента"
            , parameters = {@Parameter(name = "clientId", description = "Id клиента")})
    @GetMapping("/get-by-client-id")
    public ResponseEntity<List<RecommendationDTO>> getByClientId(@RequestParam Long clientId) {
        return new ResponseEntity<>(recommendationService.findRecommendationByClientId(clientId), HttpStatus.OK);
    }


}
