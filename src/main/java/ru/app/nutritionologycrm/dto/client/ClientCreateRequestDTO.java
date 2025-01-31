package ru.app.nutritionologycrm.dto.client;



import lombok.Data;

@Data
public class ClientCreateRequestDTO {

    private String name;

    private String contacts;

    private Integer age;

    private String sex;

    private Boolean status;

    private String tgUrl;

}
