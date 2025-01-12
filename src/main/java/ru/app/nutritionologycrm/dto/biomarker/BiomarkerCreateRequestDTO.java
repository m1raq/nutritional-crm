package ru.app.nutritionologycrm.dto.biomarker;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BiomarkerCreateRequestDTO {

    private String name;

    private String value;

    private String normalValue;

    private String clinicalReferences;

    private String nutritionist;

    private String unit;

}
