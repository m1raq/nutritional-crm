package ru.app.nutritionologycrm.dto.biomarker;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(name = "Тело для обновления биомаркера")
@Builder
@Data
public class BiomarkerUpdateRequestDTO {

    @Schema(description = "Id существующего биомаркера")
    private Long id;

    @Schema(description = "Название биомаркера")
    private String name;

    @Schema(description = "Значение")
    private String value;

    @Schema(description = "Клинические референсы")
    private String clinicalReferences;

    @Schema(description = "Референсы нутрициолога")
    private String nutritionist;

    @Schema(description = "Единица измерения")
    private String unit;

}
