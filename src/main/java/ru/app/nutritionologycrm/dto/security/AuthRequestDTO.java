package ru.app.nutritionologycrm.dto.security;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthRequestDTO {

    private String username;

    private String password;

}
