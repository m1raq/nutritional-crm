package ru.app.nutritionologycrm.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(name = "Тело ответа авторизации")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDTO {

    @Schema(description = "Id сущности")
    private Long id;

    @Schema(description = "Токен для аунтефикации при дальнейших запросах")
    private String token;

    @Schema(description = "Рефреш-токен для обновления jwt токена")
    private String refreshToken;

    @Schema(description = "Юзернейм")
    private String username;

    @Schema(description = "Роли")
    private List<String> roles;


}
