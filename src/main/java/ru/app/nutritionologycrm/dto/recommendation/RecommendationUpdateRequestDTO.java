package ru.app.nutritionologycrm.dto.recommendation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecommendationUpdateRequestDTO {

    private Long id;

    private String foodRecommendation;

    private String drinkingMode;

    private String nutraceuticals;

    private String physicalActivity;

    private String lifeMode;

    private String stressControl;

}
