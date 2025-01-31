package ru.app.nutritionologycrm.frontend.page.client.medical.history;

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
import ru.app.nutritionologycrm.service.MedicalHistoryService;


@PageTitle("Анамнез")
@RolesAllowed("USER")
@Lazy
@PreserveOnRefresh
@Component
@Route("medical-history")
public class MedicalHistoryView extends VerticalLayout implements HasUrlParameter<String> {

    private static SecurityContext securityContext;

    private final MedicalHistoryService medicalHistoryService;

    private MedicalHistoryForm medicalHistoryForm;

    private HorizontalLayout dashboardLayout;

    @Autowired
    public MedicalHistoryView(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (medicalHistoryForm != null) {
            remove(medicalHistoryForm, dashboardLayout);
        }

        dashboardLayout = new HorizontalLayout();
        dashboardLayout.setJustifyContentMode(JustifyContentMode.START);
        dashboardLayout.setAlignItems(Alignment.START);

        ClientDashboardForm clientDashboardForm = new ClientDashboardForm(securityContext == null
                ? SecurityContextHolder.getContext() : securityContext);
        dashboardLayout.add(clientDashboardForm);

        if (parameter.endsWith("U")) {
            medicalHistoryForm = new MedicalHistoryForm(medicalHistoryService, true, this);
        } else {
            medicalHistoryForm = new MedicalHistoryForm(medicalHistoryService, false, this);
        }
        medicalHistoryForm.setWidth("500px");
        setHorizontalComponentAlignment(Alignment.CENTER, medicalHistoryForm);
        add(dashboardLayout, medicalHistoryForm);
    }

    public void showUpdateNotification() {
        if (medicalHistoryForm != null) {
            this.getUI().ifPresent(ui -> ui.access(() -> {
                remove(medicalHistoryForm);
                medicalHistoryForm = new MedicalHistoryForm(medicalHistoryService, false, this);
                medicalHistoryForm.setWidth("500px");
                setHorizontalComponentAlignment(Alignment.CENTER, medicalHistoryForm);
                add(medicalHistoryForm);
            }));
        }
    }


    public static void initSecurity(SecurityContext context) {
        securityContext = context;
    }
}
