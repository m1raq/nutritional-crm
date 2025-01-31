package ru.app.nutritionologycrm.dto.client;

import lombok.Builder;
import lombok.Data;
import ru.app.nutritionologycrm.dto.DocumentDTO;
import ru.app.nutritionologycrm.dto.UserDTO;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryDTO;
import ru.app.nutritionologycrm.dto.meet.MeetDTO;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationDTO;

import java.util.List;

@Builder
@Data
public class ClientDTO {

    private Long id;

    private String name;

    private String contacts;

    private Integer age;

    private String sex;

    private Boolean status;

    private String tgUrl;

    private String tgBotChatId;

    private UserDTO user;

    private MedicalHistoryDTO medicalHistory;

    private List<MeetDTO> meets;

    private List<BiomarkerDTO> biomarkers;

    private List<RecommendationDTO> recommendations;

    private List<DocumentDTO> documents;

}
