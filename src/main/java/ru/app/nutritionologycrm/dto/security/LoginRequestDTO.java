package ru.app.nutritionologycrm.dto.security;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequestDTO {

    private String email;

    private String password;

}
