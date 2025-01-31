package ru.app.nutritionologycrm.dto.meet;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Builder
@Data
public class MeetCreateRequestDTO {

    private LocalDateTime start;

    private LocalDateTime end;

    private String place;

    private String duration;

}
