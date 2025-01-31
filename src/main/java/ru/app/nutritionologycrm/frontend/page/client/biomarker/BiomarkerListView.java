package ru.app.nutritionologycrm.frontend.page.client.biomarker;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.frontend.page.client.ClientDashboardForm;
import ru.app.nutritionologycrm.service.BiomarkerService;

@PageTitle("Исследования")
@Lazy
@RolesAllowed("USER")
@PreserveOnRefresh
@Component
@Route("biomarkers")
public class BiomarkerListView extends VerticalLayout implements HasUrlParameter<String> {

    private final BiomarkerService biomarkerService;

    private static SecurityContext context;

    private BiomarkerListForm biomarkerListForm;

    private HorizontalLayout dashboardLayout;


    @Autowired
    public BiomarkerListView(BiomarkerService biomarkerService) {
        this.biomarkerService = biomarkerService;
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        SecurityContextHolder.setContext(context == null
                ? SecurityContextHolder.getContext() : context);

        if (biomarkerListForm != null) {
            remove(biomarkerListForm, dashboardLayout);
        }

        dashboardLayout = new HorizontalLayout();
        dashboardLayout.setJustifyContentMode(JustifyContentMode.START);
        dashboardLayout.setAlignItems(Alignment.START);

        ClientDashboardForm clientDashboardForm = new ClientDashboardForm(context == null
                ? SecurityContextHolder.getContext() : context);
        dashboardLayout.add(clientDashboardForm);

        biomarkerListForm = new BiomarkerListForm(biomarkerService
                .findAllBiomarkersByClientId(Long.valueOf(parameter))
                ,  context == null ? SecurityContextHolder.getContext() : context);

        biomarkerListForm.setWidth("500px");
        setHorizontalComponentAlignment(Alignment.CENTER, biomarkerListForm);
        add(dashboardLayout, biomarkerListForm);
    }

    public static void initSecurity(SecurityContext securityContext) {
        context = securityContext;
        SecurityContextHolder.setContext(securityContext);
    }
}
