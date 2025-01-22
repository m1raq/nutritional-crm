package ru.app.nutritionologycrm.dto.recommendation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.app.nutritionologycrm.dto.UserDTO;
import ru.app.nutritionologycrm.dto.client.ClientDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class RecommendationDTO {

    private Long id;

    private String foodRecommendation;

    private String drinkingMode;

    private String nutraceuticals;

    private String physicalActivity;

    private String lifeMode;

    private String stressControl;

    private ClientDTO client;

    private UserDTO user;

}
