package ru.app.nutritionologycrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(name = "Тело результата запрсоа")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ResponseMessageDTO {

    @Schema(description = "Успех")
    private Boolean success;

    @Schema(description = "Сообщение")
    private String message;

    @Schema(description = "Особенности")
    private String features;

}
