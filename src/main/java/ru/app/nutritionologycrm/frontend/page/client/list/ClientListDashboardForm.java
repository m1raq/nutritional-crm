package ru.app.nutritionologycrm.frontend.page.client.list;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.router.RouteParam;
import com.vaadin.flow.router.RouteParameters;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.app.nutritionologycrm.frontend.component.SideNavItemWithClickNotifier;


@Setter
@Getter
public class ClientListDashboardForm extends HorizontalLayout {

    private SideNav dashBoard;

    private Button logOut;


    public ClientListDashboardForm(SecurityContext securityContext) {

        dashBoard = new SideNav();
        logOut = new Button("Log out", VaadinIcon.SIGN_OUT.create());
        logOut.addClickListener(event -> {
            UI.getCurrent().navigate("login");
        });

        SideNavItemWithClickNotifier activeClients
                = new SideNavItemWithClickNotifier("Активные клиенты", VaadinIcon.USER.create());
        SideNavItemWithClickNotifier archiveClients
                = new SideNavItemWithClickNotifier("Неактивные клиенты", VaadinIcon.USER.create());

        activeClients.addClickListener(event -> {
            SecurityContextHolder.setContext(securityContext);
            ClientListView.initSecurity(securityContext);

            UI.getCurrent().navigate(ClientListView.class
                    , new RouteParameters(new RouteParam("query", "active")));
        });

        archiveClients.addClickListener(event -> {
            SecurityContextHolder.setContext(securityContext);
            ClientListView.initSecurity(securityContext);

            UI.getCurrent().navigate(ClientListView.class
                    , new RouteParameters(new RouteParam("query", "archive")));
        });

        SecurityContextHolder.setContext(securityContext);
        ClientListView.initSecurity(securityContext);

        dashBoard.addItem(activeClients, archiveClients);
        add(dashBoard, logOut);
    }



}
