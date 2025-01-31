package ru.app.nutritionologycrm.frontend.page.client.recommendation;

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
import ru.app.nutritionologycrm.dto.recommendation.RecommendationDTO;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationUpdateRequestDTO;
import ru.app.nutritionologycrm.frontend.VaadinComponentBuilder;
import ru.app.nutritionologycrm.service.RecommendationService;

@Setter
@Getter
public class RecommendationForm extends FormLayout {

    private TextField name;

    private TextField foodRecommendation;

    private TextField drinkingMode;

    private TextField nutraceuticals;

    private TextField physicalActivity;

    private TextField lifeMode;

    private TextField stressControl;

    private Button edit;

    private Button send;

    private Button back;

    private H3 title;

    private static SecurityContext securityContext;

    private final RecommendationService recommendationService;

    private static ClientDTO actualClient;

    private Long recommendationId;

    public RecommendationForm(RecommendationService recommendationService, Boolean isUpdating, Long recommendationId) {
        this.recommendationService = recommendationService;
        this.recommendationId = recommendationId;

        RecommendationDTO recommendation = recommendationService.findRecommendationById(recommendationId);

        title = new H3("Биомаркер");

        name = new TextField("Название");
        name.setValue(recommendation.getName());

        foodRecommendation = new TextField("Пищевые рекомендации");
        foodRecommendation.setValue(recommendation.getFoodRecommendation());

        drinkingMode = new TextField("Питьевой режим");
        drinkingMode.setValue(recommendation.getDrinkingMode());

        nutraceuticals = new TextField("Нутрицевтики");
        nutraceuticals.setValue(recommendation.getNutraceuticals());

        physicalActivity = new TextField("Физическая активность");
        physicalActivity.setValue(recommendation.getPhysicalActivity());

        lifeMode = new TextField("Режим жизни");
        lifeMode.setValue(recommendation.getLifeMode());

        stressControl = new TextField("Управление стрессом");
        stressControl.setValue(recommendation.getStressControl());

        initFields(isUpdating);
        back = VaadinComponentBuilder.buildBackButton(securityContext
                , "recommendations/" + actualClient.getId()
                , UI.getCurrent());
        initSendButton();

        add(title, name, foodRecommendation, drinkingMode, nutraceuticals
                , physicalActivity, lifeMode, stressControl, edit, send, back);
        setMaxWidth("500px");
        setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        setColspan(title, 2);
        setColspan(name, 2);
        setColspan(foodRecommendation, 2);
        setColspan(drinkingMode, 2);
        setColspan(nutraceuticals, 2);
        setColspan(physicalActivity, 2);
        setColspan(lifeMode, 2);
        setColspan(stressControl, 2);
    }

    private void initSendButton() {
        send = new Button("Отправить клиенту");
        send.addClickListener(event -> {
            String messageText = "Название: " + name.getValue() + "%0A" +
                    "Пищевые рекомендации - " + foodRecommendation.getValue() + "%0A" +
                    "Питьевой режим - " + drinkingMode.getValue() + "%0A" +
                    "Нутрицевтики - " + nutraceuticals.getValue() + "%0A" +
                    "Физическая активность - " + physicalActivity.getValue() + "%0A" +
                    "Режим жизни - " + lifeMode.getValue() + "%0A" +
                    "Управление стрессом - " + stressControl.getValue() + "%0A";

            SecurityContextHolder.setContext(securityContext);
            UI.getCurrent().getPage().open("https://t.me/share/url?url="
                    + "\uD83D\uDCD1 Рекомендации для клиента:"
                    + "&text=" + messageText);
        });
    }

    private void initFields(Boolean isUpdating) {

        if (!isUpdating) {
            name.setReadOnly(true);
            foodRecommendation.setReadOnly(true);
            drinkingMode.setReadOnly(true);
            nutraceuticals.setReadOnly(true);
            physicalActivity.setReadOnly(true);
            lifeMode.setReadOnly(true);
            stressControl.setReadOnly(true);

            edit = VaadinComponentBuilder.buildEditButton("Редактировать"
                    , securityContext
                    , "recommendation/" + recommendationId + "-U"
                    , UI.getCurrent());
        } else {

            edit = new Button("Сохранить");
            edit.addThemeVariants(ButtonVariant.LUMO_SMALL);
            edit.addClickListener(event -> {
                SecurityContextHolder.setContext(securityContext);
                RecommendationView.initSecurity(securityContext);

                RecommendationUpdateRequestDTO recommendationUpdateRequestDTO
                        =  RecommendationUpdateRequestDTO.builder().build();

                try {
                    BeanValidationBinder<RecommendationUpdateRequestDTO> binder
                            = new BeanValidationBinder<>(RecommendationUpdateRequestDTO.class);
                    binder.bindInstanceFields(this);
                    binder.writeBean(recommendationUpdateRequestDTO);
                    recommendationUpdateRequestDTO.setId(recommendationId);
                    recommendationService.update(recommendationUpdateRequestDTO);
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }

                UI.getCurrent().navigate("recommendation/" + recommendationId);
            });
        }
    }


    public static void initSecurity(SecurityContext context) {
        securityContext = context;
    }

    public static void initActualClient(ClientDTO client) {
        actualClient = client;
    }

}
