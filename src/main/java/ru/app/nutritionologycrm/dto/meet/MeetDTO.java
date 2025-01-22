package ru.app.nutritionologycrm.dto.meet;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.app.nutritionologycrm.dto.UserDTO;
import ru.app.nutritionologycrm.dto.client.ClientDTO;


import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
