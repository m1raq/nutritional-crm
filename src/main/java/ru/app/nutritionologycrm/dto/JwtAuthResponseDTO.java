package ru.app.nutritionologycrm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthResponseDTO {

    private String token;

}
