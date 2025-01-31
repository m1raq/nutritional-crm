package ru.app.nutritionologycrm.frontend.page.login;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.security.SecurityService;
import ru.app.nutritionologycrm.service.ClientService;

@AnonymousAllowed
@PreserveOnRefresh
@Component
@PageTitle("Login")
@Route("login")
public class LoginView extends VerticalLayout {

    @Autowired
    public LoginView(SecurityService securityService, ClientService clientService) {

        LoginForm loginForm = new LoginForm();
        setHorizontalComponentAlignment(Alignment.CENTER, loginForm);

        add(loginForm);
        LoginFormBinder loginFormBinder = new LoginFormBinder(loginForm
                , securityService, clientService);
        loginFormBinder.addBindingAndValidation();
    }
}
