package ru.app.nutritionologycrm.dto;

import lombok.Builder;
import lombok.Data;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerDTO;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.dto.meet.MeetDTO;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationDTO;
import ru.app.nutritionologycrm.entity.*;

import java.util.List;

@Data
@Builder
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private List<ClientDTO> clients;

    private List<BiomarkerDTO> biomarkers;

    private List<RecommendationDTO> recommendations;

    private List<MeetDTO> meets;

    private List<DocumentDTO> documents;

    private RoleType role;


}

