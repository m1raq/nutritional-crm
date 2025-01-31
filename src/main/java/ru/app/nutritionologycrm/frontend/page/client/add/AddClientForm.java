package ru.app.nutritionologycrm.frontend.page.client.add;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;
import lombok.Setter;


import java.util.stream.Stream;

@Getter
@Setter
public class AddClientForm extends FormLayout {

    private TextField name;

    private TextField contacts;

    private TextField age;

    private TextField sex;

    private TextField tgUrl;

    private Button saveButton;

    private Button backButton;

    Span errorMessageField;

    public AddClientForm() {
        H3 title = new H3("Регистрация клиента");

        name = new TextField("Имя");

        contacts = new TextField("Контакты");
        contacts.setPattern("[0-9]+");
        contacts.setErrorMessage("Поле принимает только цифры");

        age = new TextField("Возраст");
        age.setPattern("[0-9]+");
        age.setErrorMessage("Поле принимает только цифры");

        sex = new TextField("Пол");

        tgUrl = new TextField("Telegram-ссылка");
        tgUrl.setPattern("https://t.me/.+");
        tgUrl.setErrorMessage("Формат ссылки: https://t.me/client_username");


        errorMessageField = new Span();

        setRequiredIndicatorVisible(name, contacts, age, sex, tgUrl);

        saveButton = new Button("Сохранить");
        backButton = new Button("Назад");
        backButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        add(name,  contacts, age, sex, tgUrl, saveButton, backButton);
        setMaxWidth("500px");
        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));
        setColspan(title, 2);
        setColspan(name, 2);
        setColspan(contacts, 2);
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }


}
