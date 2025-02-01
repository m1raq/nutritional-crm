package ru.app.nutritionologycrm.frontend.page.registration;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.security.SecurityService;

@AnonymousAllowed
@PreserveOnRefresh
@Component
@PageTitle("Регистрация")
@Route("registration")
public class RegistrationView extends VerticalLayout {

    @Autowired
    public RegistrationView(SecurityService securityService) {
        RegistrationForm registrationForm = new RegistrationForm();
        // Center the RegistrationForm
        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

        add(registrationForm);


        RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm
                , securityService);
        registrationFormBinder.addBindingAndValidation();
    }

}
