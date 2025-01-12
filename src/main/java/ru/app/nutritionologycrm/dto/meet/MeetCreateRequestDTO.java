package ru.app.nutritionologycrm.dto.meet;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class MeetCreateRequestDTO {

    private Date date;

    private String place;

    private String duration;

}
