package ru.app.nutritionologycrm.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.app.nutritionologycrm.dto.client.ClientDTO;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class DocumentDTO {

    private Long id;

    private String name;

    private Date date;

    private UserDTO user;

    private ClientDTO client;

}
