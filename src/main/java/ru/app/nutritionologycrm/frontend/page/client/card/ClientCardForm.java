package ru.app.nutritionologycrm.frontend.page.client.card;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.dto.client.ClientUpdateRequestDTO;
import ru.app.nutritionologycrm.frontend.VaadinComponentBuilder;
import ru.app.nutritionologycrm.service.ClientService;

@Setter
@Getter
public class ClientCardForm extends FormLayout {

    private TextField name;

    private TextField contacts;

    private TextField age;

    private TextField sex;

    private TextField status;

    private TextField tgUrl;

    private Button edit;

    private Button inArchive;

    private Button back;

    private Button toChat;

    private H3 title;

    private static SecurityContext securityContext;

    private final ClientService clientService;

    private static Long clientId;

    private static String backClientStatus;

    public ClientCardForm(ClientService clientService, Boolean isUpdating) {
        this.clientService = clientService;

        title = new H3("Карточка клиента");

        ClientDTO client = clientService.findById(clientId);

        name = new TextField("Имя");
        name.setValue(client.getName());

        contacts = new TextField("Контакты");
        contacts.setValue(client.getContacts());

        age = new TextField("Возраст");
        age.setValue(client.getAge().toString());

        sex = new TextField("Пол");
        sex.setValue(client.getSex());

        status = new TextField("Статус");
        status.setReadOnly(true);
        status.setValue(client.getStatus() ? "Активный" : "Неактивный");

        tgUrl = new TextField("Telegram-ссылка");
        tgUrl.setValue(client.getTgUrl());

        initClientFields(isUpdating);
        back = VaadinComponentBuilder.buildBackButton(securityContext
                , "clients/" + backClientStatus, UI.getCurrent());
        initStatusButton(client.getStatus());
        initToChatButton(client);

        add(title, name, age, sex, status, contacts, tgUrl, edit, inArchive, back, toChat);

        setMaxWidth("500px");
        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));
        setColspan(title, 2);
        setColspan(name, 2);
    }

    private void initToChatButton(ClientDTO client) {
        toChat = new Button("Перейти в чат");
        toChat.setIcon(VaadinIcon.PAPERPLANE.create());
        toChat.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toChat.addClickListener(event -> {
            SecurityContextHolder.setContext(securityContext);
            ClientCardView.initSecurity(securityContext);
            UI.getCurrent().getPage().open(client.getTgUrl());
        });
    }

    private void initStatusButton(Boolean status) {
        inArchive = new Button(status ? "В архив" : "Активировать");
        inArchive.addThemeVariants(ButtonVariant.LUMO_SMALL);

        if (status) {
            inArchive.addClickListener(event -> {
                SecurityContextHolder.setContext(securityContext);
                ClientCardView.initSecurity(securityContext);

                ClientUpdateRequestDTO clientUpdateRequestDTO = ClientUpdateRequestDTO.builder().build();
                try {
                    BeanValidationBinder<ClientUpdateRequestDTO> binder
                            = new BeanValidationBinder<>(ClientUpdateRequestDTO.class);
                    binder.bindInstanceFields(this);
                    binder.writeBean(clientUpdateRequestDTO);
                    clientUpdateRequestDTO.setStatus("Неактивный");
                    clientUpdateRequestDTO.setId(clientId);

                    clientService.updateClient(clientUpdateRequestDTO);
                    UI.getCurrent().navigate("client/" + clientId);
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            inArchive.addClickListener(event -> {
                SecurityContextHolder.setContext(securityContext);
                ClientCardView.initSecurity(securityContext);

                ClientUpdateRequestDTO clientUpdateRequestDTO = ClientUpdateRequestDTO.builder().build();

                try {
                    BeanValidationBinder<ClientUpdateRequestDTO> binder
                            = new BeanValidationBinder<>(ClientUpdateRequestDTO.class);
                    binder.bindInstanceFields(this);
                    binder.writeBean(clientUpdateRequestDTO);
                    clientUpdateRequestDTO.setStatus("Активный");
                    clientUpdateRequestDTO.setId(clientId);

                    clientService.updateClient(clientUpdateRequestDTO);
                    UI.getCurrent().navigate("client/" + clientId);
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void initClientFields(Boolean isUpdating) {
        if (!isUpdating) {
            name.setReadOnly(true);
            contacts.setReadOnly(true);
            age.setReadOnly(true);
            sex.setReadOnly(true);
            tgUrl.setReadOnly(true);

            edit = VaadinComponentBuilder.buildEditButton("Редактировать", securityContext
                    , "client/" + clientId + "-U", UI.getCurrent());
            edit.addThemeVariants(ButtonVariant.LUMO_SMALL);
            edit.addClickListener(event -> {
                SecurityContextHolder.setContext(securityContext);
                ClientCardView.initSecurity(securityContext);
                UI.getCurrent().navigate("client/" + clientId + "-U");
            });
        } else {

            edit = new Button("Сохранить");
            edit.addThemeVariants(ButtonVariant.LUMO_SMALL);
            edit.addClickListener(event -> {
                SecurityContextHolder.setContext(securityContext);
                ClientCardView.initSecurity(securityContext);

                ClientUpdateRequestDTO clientUpdateRequestDTO =  ClientUpdateRequestDTO.builder().build();

                try {
                    BeanValidationBinder<ClientUpdateRequestDTO> binder
                            = new BeanValidationBinder<>(ClientUpdateRequestDTO.class);
                    binder.bindInstanceFields(this);
                    binder.writeBean(clientUpdateRequestDTO);
                    clientUpdateRequestDTO.setId(clientId);
                    clientService.updateClient(clientUpdateRequestDTO);
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }

                UI.getCurrent().navigate("client/" + clientId);
            });
        }
    }

    public static void initSecurityContext(SecurityContext context) {
        securityContext = context;
    }

    public static void initClientId(Long id) {
        clientId = id;
    }

    public static void initBackClientStatus(String status) {
        backClientStatus = status;
    }

}
