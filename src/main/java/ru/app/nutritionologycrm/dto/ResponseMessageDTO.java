package ru.app.nutritionologycrm.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessageDTO {

    private Boolean success;

    private String message;

    private String features;

}
