package ru.app.nutritionologycrm.dto.biomarker;


import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class BiomarkerUpdateRequestDTO {

    private Long id;

    private String name;

    private String value;

    private String clinicalReferences;

    private String nutritionist;

    private String unit;

}
