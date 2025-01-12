package ru.app.nutritionologycrm.dto.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ClientUpdateRequestDTO {

    private Long id;

    private String name;

    private Integer age;

    private String sex;

    private String status;

    private String contacts;

    private String tgUrl;

}
