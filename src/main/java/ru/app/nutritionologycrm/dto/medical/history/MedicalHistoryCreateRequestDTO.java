package ru.app.nutritionologycrm.dto.medical.history;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(name = "Тело для создания анамнеза")
@Data
@Builder
public class MedicalHistoryCreateRequestDTO {

    @Schema(description = "Антропометрия")
    private String anthropometry;

    @Schema(description = "Режим жизни")
    private String lifeMode;

    @Schema(description = "Жалобы")
    private String complaints;

    @Schema(description = "Гипотезы")
    private String hypotheses;

    @Schema(description = "Питание")
    private String nutrition;

    @Schema(description = "Питьевой режи")
    private String drinkingMode;

    @Schema(description = "Физическая активность")
    private String physicalActivity;

    @Schema(description = "Цели")
    private String goals;

    @Schema(description = "Особые состояния")
    private String specialConditions;


}
