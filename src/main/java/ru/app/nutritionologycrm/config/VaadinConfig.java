package ru.app.nutritionologycrm.config;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.app.nutritionologycrm.frontend.page.client.add.AddClientForm;
import ru.app.nutritionologycrm.frontend.page.login.LoginForm;
import ru.app.nutritionologycrm.frontend.page.registration.RegistrationForm;

@Push
@Configuration
public class VaadinConfig implements AppShellConfigurator {

    @Bean
    public RegistrationForm registrationForm() {
        return new RegistrationForm();
    }

    @Bean
    public LoginForm loginForm() {
        return new LoginForm();
    }

    @Bean
    public AddClientForm addClientForm() {
        return new AddClientForm();
    }


}
