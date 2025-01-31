package ru.app.nutritionologycrm.frontend.page.client.card;

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
import ru.app.nutritionologycrm.service.ClientService;

@Lazy
@RolesAllowed("USER")
@PageTitle("Клиент")
@PreserveOnRefresh
@Route("client")
@Component
public class ClientCardView extends VerticalLayout implements HasUrlParameter<String> {

    private static SecurityContext securityContext;

    private ClientCardForm clientCardForm;

    private final ClientService clientService;

    private HorizontalLayout dashboardLayout;

    @Autowired
    public ClientCardView(ClientService clientService){
        this.clientService = clientService;
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (clientCardForm != null) {
            remove(clientCardForm, dashboardLayout);
        }

        dashboardLayout = new HorizontalLayout();
        dashboardLayout.setJustifyContentMode(JustifyContentMode.START);
        dashboardLayout.setAlignItems(Alignment.START);

        ClientDashboardForm clientDashboardForm = new ClientDashboardForm(securityContext == null
                ? SecurityContextHolder.getContext() : securityContext);
        dashboardLayout.add(clientDashboardForm);


        if (parameter.endsWith("U")) {
            ClientCardForm.initClientId(Long.parseLong(parameter.split("-")[0]));
            clientCardForm = new ClientCardForm(clientService, true);
        } else {
            ClientCardForm.initClientId(Long.parseLong(parameter));
            clientCardForm = new ClientCardForm(clientService, false);
        }
        clientCardForm.setWidth("500px");
        setHorizontalComponentAlignment(Alignment.CENTER, clientCardForm);
        add(dashboardLayout, clientCardForm);

    }

    public static void initSecurity(SecurityContext context){
        securityContext = context;
    }

}
