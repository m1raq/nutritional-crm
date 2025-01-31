package ru.app.nutritionologycrm.frontend.page.client.add;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.service.ClientService;

@Lazy
@PreserveOnRefresh
@RolesAllowed("USER")
@PageTitle("Add")
@Route("addClient")
@Component
public class AddClientView extends VerticalLayout {

    private final ClientService clientService;

    @Autowired
    public AddClientView(ClientService clientService) {
        this.clientService = clientService;

        AddClientForm addClientForm = new AddClientForm();
        setHorizontalComponentAlignment(Alignment.CENTER, addClientForm);

        AddClientFormBinder addClientFormBinder = new AddClientFormBinder(clientService, addClientForm);
        addClientFormBinder.addBindingAndValidation();
        add(addClientForm);
    }


}
