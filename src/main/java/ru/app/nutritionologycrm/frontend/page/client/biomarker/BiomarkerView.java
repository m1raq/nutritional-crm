package ru.app.nutritionologycrm.frontend.page.client.biomarker;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.frontend.page.client.ClientDashboardForm;
import ru.app.nutritionologycrm.service.BiomarkerService;

@PageTitle("Биомаркер")
@RolesAllowed("USER")
@PreserveOnRefresh
@Route("biomarker")
@Component
public class BiomarkerView extends VerticalLayout implements HasUrlParameter<String> {

    private static SecurityContext securityContext;

    private final BiomarkerService biomarkerService;

    private BiomarkerForm biomarkerForm;

    private HorizontalLayout dashboardLayout;

    @Autowired
    public BiomarkerView(BiomarkerService biomarkerService) {
        this.biomarkerService = biomarkerService;
    }


    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (biomarkerForm != null) {
            remove(biomarkerForm, dashboardLayout);
        }

        BiomarkerForm.initSecurity(securityContext);
        dashboardLayout = new HorizontalLayout();
        dashboardLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        dashboardLayout.setAlignItems(FlexComponent.Alignment.START);

        ClientDashboardForm clientDashboardForm = new ClientDashboardForm(securityContext == null
                ? SecurityContextHolder.getContext() : securityContext);
        dashboardLayout.add(clientDashboardForm);

        if (parameter.endsWith("U")) {
            biomarkerForm = new BiomarkerForm(biomarkerService, true
                    , Long.valueOf(parameter.split("-")[0]));
        } else {
            biomarkerForm = new BiomarkerForm(biomarkerService, false, Long.valueOf(parameter));
        }

        biomarkerForm.setWidth("500px");
        setHorizontalComponentAlignment(Alignment.CENTER, biomarkerForm);
        add(dashboardLayout, biomarkerForm);
    }

    public static void initSecurity(SecurityContext context){
        securityContext = context;
    }
}
