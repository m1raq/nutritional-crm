package ru.app.nutritionologycrm.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientCreateRequestDTO {

    private String name;

    private String contacts;

    private Integer age;

    private String sex;

    private String status;

    private String tgUrl;

}
