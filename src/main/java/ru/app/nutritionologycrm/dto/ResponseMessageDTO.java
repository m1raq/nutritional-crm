package ru.app.nutritionologycrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ResponseMessageDTO {

    private Boolean success;

    private String message;

    private String features;

}
