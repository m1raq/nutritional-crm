package ru.app.nutritionologycrm.dto.recommendation;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RecommendationCreateRequestDTO {

    private String name;

    private String foodRecommendation;

    private String drinkingMode;

    private String nutraceuticals;

    private String physicalActivity;

    private String lifeMode;

    private String stressControl;

}
