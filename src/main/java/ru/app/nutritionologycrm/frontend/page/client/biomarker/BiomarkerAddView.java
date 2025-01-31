package ru.app.nutritionologycrm.frontend.page.client.biomarker;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.service.BiomarkerService;

@Lazy
@PageTitle("Новый биомаркер")
@Component
@RolesAllowed("USER")
@PreserveOnRefresh
@Route("biomarker-create")
public class BiomarkerAddView extends VerticalLayout implements HasUrlParameter<String> {

    private final BiomarkerService biomarkerService;

    private static SecurityContext securityContext;

    private BiomarkerAddForm biomarkerAddForm;

    @Autowired
    public BiomarkerAddView(BiomarkerService biomarkerService) {
        this.biomarkerService = biomarkerService;
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (biomarkerAddForm != null) {
            remove(biomarkerAddForm);
        }
        biomarkerAddForm = new BiomarkerAddForm(biomarkerService
                , securityContext, Long.valueOf(parameter));

        setHorizontalComponentAlignment(Alignment.CENTER, biomarkerAddForm);
        add(biomarkerAddForm);
    }

    public static void initSecurity(SecurityContext context) {
        securityContext = context;
    }
}
