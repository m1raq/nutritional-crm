package ru.app.nutritionologycrm.frontend.page.client.list;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.service.ClientService;


@Lazy
@RolesAllowed("USER")
@PreserveOnRefresh
@PageTitle("Клиенты")
@Route("clients/:query?")
@Component
public class ClientListView extends VerticalLayout implements HasUrlParameter<String> {

    private final ClientService clientService;

    private static SecurityContext context;

    private ClientListForm clientListForm;

    private ClientListDashboardForm clientDashboardForm;

    private HorizontalLayout dashboardLayout;

    @Autowired
    public ClientListView(ClientService clientService) {
        this.clientService = clientService;
    }


    public static void initSecurity(SecurityContext securityContext) {
        context = securityContext;
        SecurityContextHolder.setContext(securityContext);
    }


    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        SecurityContextHolder.setContext(context == null
                ? SecurityContextHolder.getContext() : context);

        if (clientListForm != null && clientDashboardForm != null) {
            remove(clientListForm, dashboardLayout);
        }

        dashboardLayout = new HorizontalLayout();
        dashboardLayout.setJustifyContentMode(JustifyContentMode.START);
        dashboardLayout.setAlignItems(Alignment.START);

        if (event.getRouteParameters().get("query").isPresent()
                && event.getRouteParameters().get("query").get().equals("active")) {
            clientListForm = new ClientListForm(clientService.findAllByCurrentUser()
                    .stream()
                    .filter(ClientDTO::getStatus)
                    .toList(), context == null
                    ? SecurityContextHolder.getContext() : context);
            clientDashboardForm = new ClientListDashboardForm(context == null
                    ? SecurityContextHolder.getContext() : context);
            dashboardLayout.add(clientDashboardForm);

            clientListForm.setWidth("500px");
            setHorizontalComponentAlignment(Alignment.CENTER, clientListForm);
            add(dashboardLayout, clientListForm);
        } else if (event.getRouteParameters().get("query").isPresent()
                && event.getRouteParameters().get("query").get().equals("archive")) {
            clientListForm = new ClientListForm(clientService.findAllByCurrentUser()
                    .stream()
                    .filter(client -> !client.getStatus())
                    .toList(), context == null
                    ? SecurityContextHolder.getContext() : context);
            clientDashboardForm = new ClientListDashboardForm(context == null
                    ? SecurityContextHolder.getContext() : context);
            dashboardLayout.add(clientDashboardForm);

            clientListForm.setWidth("500px");
            setHorizontalComponentAlignment(Alignment.CENTER, clientListForm);
            add(dashboardLayout, clientListForm);
        }
    }
}
