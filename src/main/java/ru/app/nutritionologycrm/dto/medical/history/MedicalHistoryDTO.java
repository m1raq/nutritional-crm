package ru.app.nutritionologycrm.dto.medical.history;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.app.nutritionologycrm.dto.client.ClientDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class MedicalHistoryDTO {

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

    private ClientDTO client;

}
