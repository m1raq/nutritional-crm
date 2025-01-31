package ru.app.nutritionologycrm.dto.client;

import lombok.Builder;
import lombok.Data;

@Data
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
