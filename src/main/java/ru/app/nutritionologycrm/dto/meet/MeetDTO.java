package ru.app.nutritionologycrm.dto.meet;


import lombok.Builder;
import lombok.Data;
import ru.app.nutritionologycrm.dto.UserDTO;
import ru.app.nutritionologycrm.dto.client.ClientDTO;


import java.util.Date;

@Builder
@Data
public class MeetDTO {

    private Long id;

    private Date date;

    private String place;

    private String duration;

    private ClientDTO client;

    private UserDTO user;

}
