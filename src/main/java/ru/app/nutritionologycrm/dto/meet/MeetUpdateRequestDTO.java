package ru.app.nutritionologycrm.dto.meet;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MeetUpdateRequestDTO {

    private Long id;

    private LocalDateTime start;

    private LocalDateTime end;

    private String place;

    private String duration;
}
