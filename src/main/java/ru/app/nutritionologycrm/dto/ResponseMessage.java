package ru.app.nutritionologycrm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessage {

    private Boolean success;

    private String message;

}
