package ru.app.nutritionologycrm.dto.client;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(name = "Тело для создания клиента")
@Data
@Builder
public class ClientCreateRequestDTO {

    @Schema(description = "Имя", example = "Василий")
    private String name;

    @Schema(description = "Номер телефона", example = "89999999999")
    private String contacts;

    @Schema(description = "Возраст", example = "30")
    private Integer age;

    @Schema(description = "Пол", example = "Мужской")
    private String sex;

    @Schema(description = "Статус", example = "true")
    private Boolean status;

    @Schema(description = "Ссылка на telegram-чат")
    private String tgUrl;

}
