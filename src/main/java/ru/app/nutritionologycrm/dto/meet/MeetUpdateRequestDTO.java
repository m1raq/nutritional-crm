package ru.app.nutritionologycrm.dto.meet;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MeetUpdateRequestDTO {

    private Long id;

    private Date date;

    private String place;

    private String duration;
}
