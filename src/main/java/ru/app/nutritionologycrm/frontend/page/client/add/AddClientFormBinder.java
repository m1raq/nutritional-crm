package ru.app.nutritionologycrm.frontend.page.client.add;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.dto.client.ClientCreateRequestDTO;
import ru.app.nutritionologycrm.frontend.page.client.list.ClientListView;
import ru.app.nutritionologycrm.service.ClientService;

@Slf4j
@Component
public class AddClientFormBinder {

    private final ClientService clientService;

    private final AddClientForm addClientForm;

    private static SecurityContext securityContext;

    @Autowired
    public AddClientFormBinder(ClientService clientService, AddClientForm addClientForm) {
        this.clientService = clientService;
        this.addClientForm = addClientForm;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<ClientCreateRequestDTO> binder = new BeanValidationBinder<>(ClientCreateRequestDTO.class);
        binder.bindInstanceFields(addClientForm);

        addClientForm.getSaveButton().addClickListener(event -> {
            try {
                SecurityContextHolder.setContext(securityContext);
                ClientCreateRequestDTO client = new ClientCreateRequestDTO();

                binder.writeBean(client);
                client.setStatus(true);

                clientService.saveClient(client);

                showSuccess();
            } catch (ValidationException exception) {
                exception.printStackTrace();
            }
        });

        addClientForm.getBackButton().addClickListener(event -> {
            SecurityContextHolder.setContext(securityContext);
            ClientListView.initSecurity(securityContext);
            UI.getCurrent().navigate("clients/active");
        });
    }

    private void showSuccess() {
        Notification notification =
                Notification.show("Клиент успешно добавлен");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        ClientListView.initSecurity(securityContext);

        UI.getCurrent().navigate("clients/active");
    }

    public static void initSecurity(SecurityContext context) {
        securityContext = context;
    }

}
