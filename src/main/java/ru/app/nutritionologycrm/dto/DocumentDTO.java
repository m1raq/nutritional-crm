package ru.app.nutritionologycrm.dto;

import lombok.Builder;
import lombok.Data;
import ru.app.nutritionologycrm.dto.client.ClientDTO;

import java.time.LocalDate;

@Builder
@Data
public class DocumentDTO {

    private Long id;

    private String name;

    private LocalDate date;

    private UserDTO user;

    private ClientDTO client;

}
