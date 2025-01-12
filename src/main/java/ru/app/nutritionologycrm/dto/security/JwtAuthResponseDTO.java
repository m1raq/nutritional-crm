package ru.app.nutritionologycrm.dto.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthResponseDTO {

    private String token;

}
