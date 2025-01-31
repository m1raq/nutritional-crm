package ru.app.nutritionologycrm.dto.meet;

import lombok.Builder;
import lombok.Data;
import ru.app.nutritionologycrm.dto.UserDTO;
import ru.app.nutritionologycrm.dto.client.ClientDTO;

import java.time.LocalDateTime;


@Builder
@Data
public class MeetDTO {

    private Long id;
    //TODO:!
    private LocalDateTime start;

    private LocalDateTime end;

    private String place;

    private String duration;

    private ClientDTO client;

    private UserDTO user;

}
