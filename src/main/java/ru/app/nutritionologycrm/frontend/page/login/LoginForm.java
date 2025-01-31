package ru.app.nutritionologycrm.frontend.page.login;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;
import lombok.Setter;


import java.util.stream.Stream;


@Setter
@Getter
public class LoginForm extends VerticalLayout {

    private H3 title;

    private TextField username;

    private PasswordField password;

    private Button loginButton;

    private Span errorMessageField;

    public LoginForm() {
        title = new H3("Login");
        username = new TextField("Username");
        password = new PasswordField("Password");

        loginButton = new Button("Login");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        setRequiredIndicatorVisible(username, password);
        errorMessageField = new Span();
        username.setWidth("300px");
        loginButton.setWidth("300px");
        password.setWidth("300px");
        add(title, username, password, errorMessageField, loginButton);
        setMaxWidth("500px");
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

}
