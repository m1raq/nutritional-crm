package ru.app.nutritionologycrm.dto.biomarker;

import lombok.Builder;
import lombok.Data;
import ru.app.nutritionologycrm.dto.UserDTO;
import ru.app.nutritionologycrm.dto.client.ClientDTO;

import java.time.LocalDate;

@Data
@Builder
public class BiomarkerDTO {

    private Long id;

    private String name;

    private String value;

    private String normalValue;

    private String clinicalReferences;

    private String nutritionist;

    private String unit;

    private LocalDate date;

    private ClientDTO client;

    private UserDTO user;

}
