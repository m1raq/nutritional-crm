package ru.app.nutritionologycrm.dto.recommendation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(name = "Тело для обновления рекомендации")
@Data
@Builder
public class RecommendationUpdateRequestDTO {

    @Schema(description = "Id существующей рекомендации")
    private Long id;

    @Schema(description = "Пищевые рекомендации")
    private String foodRecommendation;

    @Schema(description = "Питьевой режим")
    private String drinkingMode;

    @Schema(description = "Нутрицевтики")
    private String nutraceuticals;

    @Schema(description = "Физическая активность")
    private String physicalActivity;

    @Schema(description = "Режим жизни")
    private String lifeMode;

    @Schema(description = "Управление стрессом")
    private String stressControl;

}
