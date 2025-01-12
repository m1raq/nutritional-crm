package ru.app.nutritionologycrm.dto.medical.history;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicalHistoryUpdateRequestDTO {

    private Long id;

    private String anthropometry;

    private String lifeMode;

    private String complaints;

    private String hypotheses;

    private String nutrition;

    private String drinkingMode;

    private String physicalActivity;

    private String goals;

    private String specialConditions;

}
