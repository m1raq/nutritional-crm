package ru.app.nutritionologycrm.dto.meet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Schema(name = "Тело для обновления встречи")
@Data
@Builder
public class MeetUpdateRequestDTO {

    @Schema(description = "Id существующей встречи")
    private Long id;

    @Schema(description = "Дата встречи")
    private Date date;

    @Schema(description = "Место встречи")
    private String place;

    @Schema(description = "Продолжительность встречи")
    private String duration;
}
