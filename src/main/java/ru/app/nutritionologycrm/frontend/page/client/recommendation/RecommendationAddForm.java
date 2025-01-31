package ru.app.nutritionologycrm.frontend.page.client.recommendation;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationCreateRequestDTO;
import ru.app.nutritionologycrm.frontend.VaadinComponentBuilder;
import ru.app.nutritionologycrm.service.RecommendationService;

import java.util.stream.Stream;

@Setter
@Getter
public class RecommendationAddForm extends FormLayout {

    private TextField name;

    private TextField foodRecommendation;

    private TextField drinkingMode;

    private TextField nutraceuticals;

    private TextField physicalActivity;

    private TextField lifeMode;

    private TextField stressControl;

    private Button saveButton;

    private Button backButton;

    public RecommendationAddForm(RecommendationService recommendationService
            , SecurityContext securityContext, Long clientId) {

        H3 title = new H3("Новая рекомендация");

        name = new TextField("Название");
        foodRecommendation = new TextField("Пищевые рекомендации");
        drinkingMode = new TextField("Питьевой режим");
        nutraceuticals = new TextField("Нутрицевтики");
        physicalActivity = new TextField("Физическая активность");
        lifeMode = new TextField("Режим жизни");
        stressControl = new TextField("Управление стрессом");

        setRequiredIndicatorVisible(name, foodRecommendation, drinkingMode, nutraceuticals
                , physicalActivity, lifeMode, stressControl);

        saveButton = new Button("Сохранить");
        backButton = VaadinComponentBuilder.buildBackButton(securityContext
                , "recommendations/" + clientId
                , UI.getCurrent());

        saveButton.addClickListener(event ->  {
            BeanValidationBinder<RecommendationCreateRequestDTO> binder
                    = new BeanValidationBinder<>(RecommendationCreateRequestDTO.class);
            binder.bindInstanceFields(this);
            try {
                SecurityContextHolder.setContext(securityContext);
                RecommendationCreateRequestDTO recommendation = RecommendationCreateRequestDTO.builder().build();

                binder.writeBean(recommendation);

                recommendationService.save(recommendation, clientId);

                UI.getCurrent().navigate("recommendations/" + clientId);
            } catch (ValidationException exception) {
                throw new RuntimeException(exception.getCause());
            }
        });

        add(title, name,  foodRecommendation, drinkingMode
                , nutraceuticals, physicalActivity, lifeMode
                , stressControl, saveButton, backButton);
        setMaxWidth("500px");
        setColspan(title, 2);
        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

}
