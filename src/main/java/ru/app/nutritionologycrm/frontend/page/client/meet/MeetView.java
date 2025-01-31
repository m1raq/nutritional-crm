package ru.app.nutritionologycrm.frontend.page.client.meet;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.frontend.page.client.ClientDashboardForm;
import ru.app.nutritionologycrm.service.MeetService;

@Lazy
@RolesAllowed("USER")
@PreserveOnRefresh
@Component
@Route("/meet")
public class MeetView extends VerticalLayout implements BeforeEnterObserver {

    private MeetForm meetForm;

    private final MeetService meetService;

    private HorizontalLayout dashboardLayout;

    @Autowired
    public MeetView(MeetService meetService) {
        this.meetService = meetService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (meetForm != null) {
            remove(dashboardLayout, meetForm);
        }

        dashboardLayout = new HorizontalLayout();
        dashboardLayout.setJustifyContentMode(JustifyContentMode.START);
        dashboardLayout.setAlignItems(Alignment.START);

        ClientDashboardForm clientDashboardForm = new ClientDashboardForm(SecurityContextHolder.getContext());
        dashboardLayout.add(clientDashboardForm);

        meetForm = new MeetForm(meetService);
        meetForm.setWidth("1000px");
        meetForm.setHeight("600px");

        setHorizontalComponentAlignment(Alignment.CENTER, meetForm);
        add(dashboardLayout, meetForm);
    }
}
