package ru.app.nutritionologycrm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ClientUpdateRequestDTO {

    private String name;

    private String age;

    private String sex;

    private String status;

    private String contacts;

    private String tgUrl;

}
