package ru.app.nutritionologycrm.dto;


import lombok.Builder;
import lombok.Data;
import ru.app.nutritionologycrm.dto.client.ClientDTO;

import java.util.Date;

@Builder
@Data
public class DocumentDTO {

    private Long id;

    private String name;

    private Date date;

    private UserDTO user;

    private ClientDTO client;

}
