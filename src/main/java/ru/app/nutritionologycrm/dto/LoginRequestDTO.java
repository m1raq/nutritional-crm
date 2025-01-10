package ru.app.nutritionologycrm.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequestDTO {

    private String email;

    private String password;

}
