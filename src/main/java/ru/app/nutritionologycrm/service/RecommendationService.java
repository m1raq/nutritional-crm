package ru.app.nutritionologycrm.service;

import ru.app.nutritionologycrm.dto.recommendation.RecommendationCreateRequestDTO;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationDTO;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationUpdateRequestDTO;

import java.util.List;

public interface RecommendationService {

    void save(RecommendationCreateRequestDTO request, Long clientId);

    void update(RecommendationUpdateRequestDTO updates);

    List<RecommendationDTO> findRecommendationByClientId(Long clientId);

}
