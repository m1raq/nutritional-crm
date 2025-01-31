package ru.app.nutritionologycrm.frontend;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class VaadinComponentBuilder {

    public static Notification buildNotification(String message, NotificationVariant theme) {
        Notification notification = new Notification();
        notification.addThemeVariants(theme);

        Div text = new Div(new Text(message));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.setAriaLabel("Close");
        closeButton.addClickListener(event -> notification.close());

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        notification.setPosition(Notification.Position.BOTTOM_START);

        notification.add(layout);
        return notification;
    }

    public static Button buildBackButton(SecurityContext context, String backPath, UI ui) {
        Button button = new Button("Назад");
        button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        button.addClickListener(event -> {
            SecurityContextHolder.setContext(context);
            ui.navigate(backPath);
        });
        return button;
    }

    public static Button buildEditButton(String label, SecurityContext context, String editPath, UI ui) {
        Button button = new Button(label);
        button.addThemeVariants(ButtonVariant.LUMO_SMALL);
        button.addClickListener(event -> {
            SecurityContextHolder.setContext(context);
            ui.navigate(editPath);
        });
        return button;
    }

    public static com.vaadin.flow.component.Component createFilterHeader(Consumer<String> filterChangeConsumer) {
        NativeLabel label = new NativeLabel("Название");
        label.getStyle().set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

    public static com.vaadin.flow.component.Component createFilterHeader(String labelText,
                                                                          Consumer<String> filterChangeConsumer) {
        NativeLabel label = new NativeLabel(labelText);
        label.getStyle().set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }


}
