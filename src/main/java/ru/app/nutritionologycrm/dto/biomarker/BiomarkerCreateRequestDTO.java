package ru.app.nutritionologycrm.dto.biomarker;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class BiomarkerCreateRequestDTO {

    private String name;

    private String value;

    private String normalValue;

    private String clinicalReferences;

    private String nutritionistReferences;

    private String unit;

    private LocalDate date;


}
