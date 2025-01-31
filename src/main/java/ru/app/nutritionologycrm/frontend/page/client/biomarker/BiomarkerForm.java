package ru.app.nutritionologycrm.frontend.page.client.biomarker;

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
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerDTO;
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerUpdateRequestDTO;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.frontend.VaadinComponentBuilder;
import ru.app.nutritionologycrm.service.BiomarkerService;


@Setter
@Getter
public class BiomarkerForm extends FormLayout{

    private TextField name;

    private TextField value;

    private TextField normalValue;

    private TextField clinicalReferences;

    private TextField nutritionist;

    private TextField unit;

    private TextField date;

    private Button edit;

    private Button back;

    private H3 title;

    private static SecurityContext securityContext;

    private final BiomarkerService biomarkerService;

    private static ClientDTO actualClient;

    private Long biomarkerId;

    public BiomarkerForm(BiomarkerService biomarkerService, Boolean isUpdating, Long biomarkerId) {
        this.biomarkerService = biomarkerService;
        this.biomarkerId = biomarkerId;

        BiomarkerDTO biomarker = biomarkerService.findBiomarkerById(biomarkerId);

        title = new H3("Биомаркер");

        name = new TextField("Название");
        name.setValue(biomarker.getName());

        value  = new TextField("Значение");
        value.setValue(biomarker.getValue());

        normalValue = new TextField("Нормативное значение");
        normalValue.setValue(biomarker.getNormalValue());

        clinicalReferences = new TextField("Клинические референсы");
        clinicalReferences.setValue(biomarker.getClinicalReferences());

        nutritionist = new TextField("Референсы нутрициолога");
        nutritionist.setValue(biomarker.getNutritionist());

        unit = new TextField("Единица измерения");
        unit.setValue(biomarker.getUnit());

        date = new TextField("Дата сдачи");
        date.setValue(biomarker.getDate().toString());

        initFields(isUpdating);
        back = VaadinComponentBuilder.buildBackButton(securityContext
                , "biomarkers/" + actualClient.getId()
                , UI.getCurrent());

        add(title, name, value, normalValue, clinicalReferences, nutritionist, unit, date, edit, back);
        setMaxWidth("500px");
        setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        setColspan(title, 2);
        setColspan(nutritionist, 2);
        setColspan(clinicalReferences, 2);
    }


    private void initFields(Boolean isUpdating) {
        date.setReadOnly(true);

        if (!isUpdating) {
            name.setReadOnly(true);
            value.setReadOnly(true);
            normalValue.setReadOnly(true);
            clinicalReferences.setReadOnly(true);
            nutritionist.setReadOnly(true);
            unit.setReadOnly(true);

            edit = VaadinComponentBuilder.buildEditButton("Редактировать", securityContext
                    , "biomarker/" + biomarkerId + "-U"
                    , UI.getCurrent());
        } else {
            edit = new Button("Сохранить");
            edit.addThemeVariants(ButtonVariant.LUMO_SMALL);
            edit.addClickListener(event -> {
                SecurityContextHolder.setContext(securityContext);
                BiomarkerView.initSecurity(securityContext);

                BiomarkerUpdateRequestDTO biomarkerUpdateRequestDTO
                        =  BiomarkerUpdateRequestDTO.builder().build();

                try {
                    BeanValidationBinder<BiomarkerUpdateRequestDTO> binder
                            = new BeanValidationBinder<>(BiomarkerUpdateRequestDTO.class);
                    binder.bindInstanceFields(this);
                    binder.writeBean(biomarkerUpdateRequestDTO);
                    biomarkerUpdateRequestDTO.setId(biomarkerId);
                    biomarkerService.updateBiomarker(biomarkerUpdateRequestDTO);
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }

                UI.getCurrent().navigate("biomarker/" + biomarkerId);
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
