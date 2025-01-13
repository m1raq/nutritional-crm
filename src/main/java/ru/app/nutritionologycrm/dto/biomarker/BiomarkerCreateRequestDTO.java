package ru.app.nutritionologycrm.dto.biomarker;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(name = "Тело для создания биомаркера")
@Builder
@Data
public class BiomarkerCreateRequestDTO {

    @Schema(description = "Название биомаркера", example = "Холестерин")
    private String name;

    @Schema(description = "Значение")
    private String value;

    @Schema(description = "Нормативное значение")
    private String normalValue;

    @Schema(description = "Клинические референсы")
    private String clinicalReferences;

    @Schema(description = "Референсы нутрициолога")
    private String nutritionistReferences;

    @Schema(description = "Единица измерения")
    private String unit;

    @Schema(description = "Дата сдачи")
    private LocalDateTime date;


}
