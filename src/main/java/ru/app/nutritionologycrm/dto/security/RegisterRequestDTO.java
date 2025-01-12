package ru.app.nutritionologycrm.dto.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDTO {

    private String username;

    private String password;

}
