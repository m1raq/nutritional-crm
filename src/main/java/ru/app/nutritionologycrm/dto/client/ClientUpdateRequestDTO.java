package ru.app.nutritionologycrm.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(name = "Тело для обновления клиента")
@Data
@Builder
public class ClientUpdateRequestDTO {

    @Schema(description = "Id существующего клиента", example = "1")
    private Long id;

    @Schema(description = "Имя", example = "Василий")
    private String name;

    @Schema(description = "Возраст", example = "30")
    private Integer age;

    @Schema(description = "Пол", example = "Мужской")
    private String sex;

    @Schema(description = "Статус(Принимает только - Активный/Неактивный", example = "Активный")
    private String status;

    @Schema(description = "Номер телефона", example = "89999999999")
    private String contacts;

    @Schema(description = "Ссылка на telegram-чат")
    private String tgUrl;

}
