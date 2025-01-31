package ru.app.nutritionologycrm.frontend.page.client.biomarker;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerCreateRequestDTO;
import ru.app.nutritionologycrm.frontend.VaadinComponentBuilder;
import ru.app.nutritionologycrm.service.BiomarkerService;

import java.util.stream.Stream;

@Setter
@Getter
public class BiomarkerAddForm extends FormLayout {

    private TextField name;

    private TextField value;

    private TextField normalValue;

    private TextField clinicalReferences;

    private TextField nutritionistReferences;

    private TextField unit;

    private DatePicker date;

    private Button saveButton;

    private Button backButton;

    public BiomarkerAddForm(BiomarkerService biomarkerService, SecurityContext securityContext, Long clientId) {

        H3 title = new H3("Новый биомаркер");

        name = new TextField("Название");
        value = new TextField("Значение");
        normalValue = new TextField("Нормальное значение");
        clinicalReferences = new TextField("Клинические референсы");
        nutritionistReferences = new TextField("Референсы нутрициолога");
        unit = new TextField("Единица измерения");
        date = new DatePicker("Дата сдачи");

        setRequiredIndicatorVisible(name, value, normalValue, clinicalReferences, nutritionistReferences, unit, date);

        saveButton = new Button("Сохранить");
        backButton = VaadinComponentBuilder.buildBackButton(securityContext
                , "biomarkers/" + clientId, UI.getCurrent());

        saveButton.addClickListener(event ->  {
            BeanValidationBinder<BiomarkerCreateRequestDTO> binder
                    = new BeanValidationBinder<>(BiomarkerCreateRequestDTO.class);
            binder.bindInstanceFields(this);
            try {
                SecurityContextHolder.setContext(securityContext);
                BiomarkerCreateRequestDTO biomarker = BiomarkerCreateRequestDTO.builder().build();

                binder.writeBean(biomarker);

                biomarkerService.saveBiomarker(biomarker, clientId);

                UI.getCurrent().navigate("biomarkers/" + clientId);
            } catch (ValidationException exception) {
                throw new RuntimeException(exception.getCause());
            }
        });

        add(title, name, value, normalValue, clinicalReferences
                , nutritionistReferences, unit, date, saveButton, backButton);
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
