package ru.app.nutritionologycrm.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "Тело для авторизации")
@Data
public class AuthRequestDTO {

    @Schema(description = "Юзернейм", example = "sasha123")
    private String username;

    @Schema(description = "Пароль", example = "qwerty")
    private String password;

}
