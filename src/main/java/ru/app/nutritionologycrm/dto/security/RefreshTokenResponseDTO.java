package ru.app.nutritionologycrm.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Тело ответа ")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponseDTO {

    @Schema(description = "Новый токен для аунтефикации")
    private String accessToken;

    @Schema(description = "Новый токен для обновления")
    private String refreshToken;

}

