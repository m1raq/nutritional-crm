package ru.app.nutritionologycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.app.nutritionologycrm.dto.ResponseMessageDTO;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationCreateRequestDTO;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.RecommendationEntity;
import ru.app.nutritionologycrm.service.RecommendationService;

import java.util.List;

@RequestMapping("/api/v1/recommendation")
@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessageDTO> create(@RequestBody RecommendationCreateRequestDTO request
            , @RequestParam Long clientId) {
        recommendationService.save(request, clientId);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Recommendation is created")
                .success(true)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessageDTO> update(@RequestBody RecommendationUpdateRequestDTO request) {
        recommendationService.update(request);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Recommendation is updated")
                .success(true)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get-by-client-id")
    public ResponseEntity<List<RecommendationEntity>> getByClientId(@RequestParam Long clientId) {
        return new ResponseEntity<>(recommendationService.findRecommendationByClientId(clientId), HttpStatus.OK);
    }


}
