package ru.app.nutritionologycrm.frontend.page.client.medical.history;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryUpdateRequestDTO;
import ru.app.nutritionologycrm.frontend.VaadinComponentBuilder;
import ru.app.nutritionologycrm.service.MedicalHistoryService;
import ru.app.nutritionologycrm.service.impl.BotService;

@Setter
@Getter
public class MedicalHistoryForm extends FormLayout {

    private TextField anthropometry;

    private TextField lifeMode;

    private TextField complaints;

    private TextField hypotheses;

    private TextField nutrition;

    private TextField drinkingMode;

    private TextField physicalActivity;

    private TextField goals;

    private TextField specialConditions;

    private Button edit;

    private Button sendForm;

    private Button back;

    private H3 title;

    private static SecurityContext securityContext;

    private final MedicalHistoryService medicalHistoryService;

    private static ClientDTO actualClient;

    private static String backClientStatus;


    public MedicalHistoryForm(MedicalHistoryService medicalHistoryService, Boolean isUpdating
            , MedicalHistoryView view) {
        this.medicalHistoryService = medicalHistoryService;

        MedicalHistoryDTO medicalHistory = medicalHistoryService.findByClientId(actualClient.getId());

        title = new H3("Анамнез");

        anthropometry = new TextField("Антропометрия");
        anthropometry.setValue(medicalHistory.getAnthropometry() == null ? "" : medicalHistory.getAnthropometry());

        lifeMode = new TextField("Режим жизни");
        lifeMode.setValue(medicalHistory.getLifeMode() == null ? "" : medicalHistory.getLifeMode());

        complaints = new TextField("Жалобы");
        complaints.setValue(medicalHistory.getComplaints() == null ? "" : medicalHistory.getComplaints());

        hypotheses = new TextField("Гипотезы");
        hypotheses.setValue(medicalHistory.getHypotheses() == null ? "" : medicalHistory.getHypotheses());

        nutrition = new TextField("Питание");
        nutrition.setValue(medicalHistory.getNutrition() == null ? "" : medicalHistory.getNutrition());

        drinkingMode = new TextField("Питьевой режим");
        drinkingMode.setValue(medicalHistory.getDrinkingMode() == null ? "" : medicalHistory.getDrinkingMode());

        physicalActivity = new TextField("Физическая активность");
        physicalActivity.setValue(medicalHistory.getPhysicalActivity()
                == null ? "" : medicalHistory.getPhysicalActivity());

        goals = new TextField("Цели");
        goals.setValue(medicalHistory.getGoals() == null ? "" : medicalHistory.getGoals());

        specialConditions = new TextField("Особые состояния");
        specialConditions.setValue(medicalHistory.getSpecialConditions()
                == null ? "" : medicalHistory.getSpecialConditions());

        BotService.initMedicalHistoryView(view);
        initSendFormButton();
        initMedicalHistoryFields(isUpdating);
        back = VaadinComponentBuilder.buildBackButton(securityContext, "clients/" + backClientStatus
                , UI.getCurrent());

        add(title, anthropometry, lifeMode, complaints
                , hypotheses, nutrition, drinkingMode
                , physicalActivity, goals, specialConditions
                , edit, sendForm, back);

        setMaxWidth("500px");
        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));
        setColspan(title, 2);
        setColspan(anthropometry, 2);

    }

    private void initSendFormButton() {
        sendForm = new Button("Отправить для заполнения");
        sendForm.addClickListener(event -> {
            SecurityContextHolder.setContext(securityContext);
            UI.getCurrent().getPage().open("https://t.me/share/url?url=https://t.me/anamnes1111_bot" +
                    "?start=anamnes-" + actualClient.getId() +
                    "&text=Перейдите, пожалуйста, в бота для заполнения формы вашего анамнеза");
        });
    }

    private void initMedicalHistoryFields(Boolean isUpdating) {
        if (!isUpdating) {
            anthropometry.setReadOnly(true);
            lifeMode.setReadOnly(true);
            complaints.setReadOnly(true);
            hypotheses.setReadOnly(true);
            nutrition.setReadOnly(true);
            drinkingMode.setReadOnly(true);
            physicalActivity.setReadOnly(true);
            goals.setReadOnly(true);
            specialConditions.setReadOnly(true);

            edit = VaadinComponentBuilder.buildEditButton("Рекдактировать"
                    , securityContext
                    , "medical-history/" + actualClient.getMedicalHistory().getId() + "-U", UI.getCurrent());

        } else {

            edit = new Button("Сохранить");
            edit.addThemeVariants(ButtonVariant.LUMO_SMALL);
            edit.addClickListener(event -> {
                SecurityContextHolder.setContext(securityContext);
                MedicalHistoryView.initSecurity(securityContext);

                MedicalHistoryUpdateRequestDTO medicalHistoryUpdateRequestDTO
                        =  MedicalHistoryUpdateRequestDTO.builder().build();

                try {
                    BeanValidationBinder<MedicalHistoryUpdateRequestDTO> binder
                            = new BeanValidationBinder<>(MedicalHistoryUpdateRequestDTO.class);
                    binder.bindInstanceFields(this);
                    binder.writeBean(medicalHistoryUpdateRequestDTO);
                    medicalHistoryUpdateRequestDTO.setId(actualClient.getMedicalHistory().getId());
                    medicalHistoryService.updateMedicalHistory(medicalHistoryUpdateRequestDTO);
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }

                UI.getCurrent().navigate("medical-history/" + actualClient.getMedicalHistory().getId());
            });
        }
    }

    public static void initClient(ClientDTO dto) {
        actualClient = dto;
    }

    public static void initSecurity(SecurityContext context) {
        securityContext = context;
    }

    public static void initBackClientStatus(String status) {
        backClientStatus = status;
    }
}
