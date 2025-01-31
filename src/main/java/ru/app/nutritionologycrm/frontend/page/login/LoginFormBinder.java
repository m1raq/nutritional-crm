package ru.app.nutritionologycrm.frontend.page.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.router.RouteParam;
import com.vaadin.flow.router.RouteParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.dto.security.AuthRequestDTO;
import ru.app.nutritionologycrm.entity.UserEntity;
import ru.app.nutritionologycrm.frontend.page.client.list.ClientListView;
import ru.app.nutritionologycrm.security.SecurityService;
import ru.app.nutritionologycrm.service.ClientService;

@Component
public class LoginFormBinder {

    private final LoginForm loginForm;

    private final SecurityService securityService;

    private final ClientService clientService;

    @Autowired
    public LoginFormBinder(LoginForm loginForm, SecurityService securityService, ClientService clientService) {
        this.loginForm = loginForm;
        this.securityService = securityService;
        this.clientService = clientService;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<UserEntity> binder = new BeanValidationBinder<>(UserEntity.class);
        binder.bindInstanceFields(loginForm);

        // A custom validator for password fields
        binder.forField(loginForm.getPassword())
                .withValidator(this::passwordValidator).bind("password");


        // Set the label where bean-level error messages go
        binder.setStatusLabel(loginForm.getErrorMessageField());

        // And finally the submit button
        loginForm.getLoginButton().addClickListener(event -> {
            try {
                // Create empty bean to store the details into
                UserEntity userBean = new UserEntity();

                // Run validators and write the values to the bean
                binder.writeBean(userBean);

                securityService.authenticateUser(AuthRequestDTO.builder()
                                .password(userBean.getPassword())
                                .username(userBean.getUsername())
                        .build());
                // Show success message if everything went well
                showSuccess(userBean);
            } catch (ValidationException exception) {
                exception.printStackTrace();
            }
        });
    }


    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {
        /*
         * Just a simple length check. A real version should check for password
         * complexity as well!
         */

        if (pass1 == null || pass1.length() < 8) {
            return ValidationResult.error("Password should be at least 8 characters long");
        }

        if (pass1 != null) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }

    private void showSuccess(UserDetails userBean) {
        Notification notification =
                Notification.show("Welcome " + userBean.getUsername());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        UI.getCurrent().navigate(ClientListView.class
                , new RouteParameters(new RouteParam("query", "active")));
    }
}
