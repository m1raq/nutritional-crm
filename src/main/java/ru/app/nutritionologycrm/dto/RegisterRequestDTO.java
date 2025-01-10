package ru.app.nutritionologycrm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDTO {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

}
