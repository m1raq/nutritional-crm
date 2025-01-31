package ru.app.nutritionologycrm.frontend.page.registration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.entity.UserEntity;
import ru.app.nutritionologycrm.security.SecurityService;

@Component
public class RegistrationFormBinder {

    private final RegistrationForm registrationForm;

    private final SecurityService securityService;

    /**
     * Flag for disabling first run for password validation
     */
    private boolean enablePasswordValidation;

    @Autowired
    public RegistrationFormBinder(RegistrationForm registrationForm, SecurityService securityService) {
        this.registrationForm = registrationForm;
        this.securityService = securityService;
    }

    /**
     * Method to add the data binding and validation logics
     * to the registration form
     */
    public void addBindingAndValidation() {
        BeanValidationBinder<UserEntity> binder = new BeanValidationBinder<>(UserEntity.class);
        binder.bindInstanceFields(registrationForm);

        // A custom validator for password fields
        binder.forField(registrationForm.getPassword())
                .withValidator(this::passwordValidator).bind("password");

        // The second password field is not connected to the Binder, but we
        // want the binder to re-check the password validator when the field
        // value changes. The easiest way is just to do that manually.
        registrationForm.getPasswordConfirm().addValueChangeListener(e -> {
            // The user has modified the second field, now we can validate and show errors.
            // See passwordValidator() for how this flag is used.
            enablePasswordValidation = true;

            binder.validate();
        });

        // Set the label where bean-level error messages go
        binder.setStatusLabel(registrationForm.getErrorMessageField());

        // And finally the submit button
        registrationForm.getSubmitButton().addClickListener(event -> {
            try {
                // Create empty bean to store the details into
                UserEntity userBean = new UserEntity();

                // Run validators and write the values to the bean
                binder.writeBean(userBean);

                securityService.register(userBean);
                // Show success message if everything went well
                showSuccess(userBean);
            } catch (ValidationException exception) {
                exception.printStackTrace();
            }
        });
    }

    /**
     * Method to validate that:
     * <p>
     * 1) Password is at least 8 characters long
     * <p>
     * 2) Values in both fields match each other
     */
    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {
        /*
         * Just a simple length check. A real version should check for password
         * complexity as well!
         */

        if (pass1 == null || pass1.length() < 8) {
            return ValidationResult.error("Password should be at least 8 characters long");
        }

        if (!enablePasswordValidation) {
            // user hasn't visited the field yet, so don't validate just yet, but next time.
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = registrationForm.getPasswordConfirm().getValue();

        if (pass1 != null && pass2.equals(pass1)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }

    /**
     * We call this method when form submission has succeeded
     */
    private void showSuccess(UserDetails userBean) {
        Notification notification =
                Notification.show("Data saved, welcome " + userBean.getUsername());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        UI.getCurrent().navigate("login");
        // Here you'd typically redirect the user to another view
    }

}
