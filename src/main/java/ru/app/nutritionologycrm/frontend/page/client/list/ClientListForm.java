package ru.app.nutritionologycrm.frontend.page.client.list;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.frontend.VaadinComponentBuilder;
import ru.app.nutritionologycrm.frontend.page.client.add.AddClientFormBinder;
import ru.app.nutritionologycrm.frontend.page.client.add.AddClientView;
import ru.app.nutritionologycrm.frontend.page.client.biomarker.BiomarkerListForm;
import ru.app.nutritionologycrm.frontend.page.client.card.ClientCardForm;
import ru.app.nutritionologycrm.frontend.page.client.card.ClientCardView;
import ru.app.nutritionologycrm.frontend.page.client.ClientDashboardForm;
import ru.app.nutritionologycrm.frontend.page.client.document.DocumentListForm;
import ru.app.nutritionologycrm.frontend.page.client.document.DocumentUploadForm;
import ru.app.nutritionologycrm.frontend.page.client.medical.history.MedicalHistoryForm;
import ru.app.nutritionologycrm.frontend.page.client.meet.MeetForm;
import ru.app.nutritionologycrm.frontend.page.client.recommendation.RecommendationListForm;

import java.util.List;


@Getter
@Setter
public class ClientListForm extends VerticalLayout {

    private static Grid<ClientDTO> grid;

    public ClientListForm(List<ClientDTO> clients, SecurityContext context) {
        H2 title = new H2(clients.stream()
                .allMatch(ClientDTO::getStatus)
                ? "Активные клиенты" : "Неактивные клиенты");

        Button addButton = new Button("Новый клиент");
        addButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addButton.addClickListener(event -> {
            SecurityContextHolder.setContext(context);
            AddClientFormBinder.initSecurity(context);

            UI.getCurrent().navigate(AddClientView.class);
        });

        grid = new Grid<>(ClientDTO.class, false);
        Grid.Column<ClientDTO> nameColumn = grid.addColumn(createPersonRenderer())
                .setWidth("230px").setFlexGrow(0);
        Grid.Column<ClientDTO> clientTgUrl = grid.addColumn(ClientDTO::getTgUrl);

        GridListDataView<ClientDTO> dataView = grid.setItems(clients);
        PersonFilter personFilter = new PersonFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(nameColumn).setComponent(
                VaadinComponentBuilder.createFilterHeader("Имя", personFilter::setFullName));
        headerRow.getCell(clientTgUrl).setComponent(
                VaadinComponentBuilder.createFilterHeader("Telegram", personFilter::setTgUrl));

        grid.addItemClickListener(event -> {

            SecurityContextHolder.setContext(context);
            ClientCardView.initSecurity(context);
            ClientCardForm.initSecurityContext(context);

            ClientCardForm.initBackClientStatus(event.getItem().getStatus() ? "active" : "archive");
            ClientDashboardForm.initActualClient(event.getItem());
            BiomarkerListForm.initActualClient(event.getItem());
            BiomarkerListForm.initBackButtonStatus(event.getItem().getStatus() ? "active" : "archive");

            MedicalHistoryForm.initSecurity(context);
            MedicalHistoryForm.initClient(event.getItem());
            MedicalHistoryForm.initBackClientStatus(event.getItem().getStatus() ? "active" : "archive");

            DocumentUploadForm.initSecurity(context);
            DocumentUploadForm.initActualClient(event.getItem());

            DocumentListForm.initActualClient(event.getItem());
            DocumentListForm.initBackButtonStatus(event.getItem().getStatus() ? "active" : "archive");

            MeetForm.initActualClient(event.getItem());
            MeetForm.initSecurity(context);

            RecommendationListForm.initActualClient(event.getItem());
            RecommendationListForm.initBackButtonStatus(event.getItem().getStatus() ? "active" : "archive");

            UI.getCurrent().navigate("client/" + event.getItem().getId());
        });

        add(title, addButton, grid);
    }


    private static class PersonFilter {
        private final GridListDataView<ClientDTO> dataView;

        private String fullName;
        private String tgUrl;

        public PersonFilter(GridListDataView<ClientDTO> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
            this.dataView.refreshAll();
        }

        public void setTgUrl(String tgUrl) {
            this.tgUrl = tgUrl;
            this.dataView.refreshAll();
        }


        public boolean test(ClientDTO person) {
            boolean matchesFullName = matches(person.getName(), fullName);
            boolean matchesTgUrl = matches(person.getTgUrl(), tgUrl);

            return matchesFullName && matchesTgUrl;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }


    private static Renderer<ClientDTO> createPersonRenderer() {
        return LitRenderer.<ClientDTO> of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "  <vaadin-avatar img=\"${item.pictureUrl}\" name=\"${item.fullName}\"></vaadin-avatar>"
                                + "  <span> ${item.fullName} </span>"
                                + "</vaadin-horizontal-layout>")
//                .withProperty("pictureUrl", Person::getPictureUrl)
                .withProperty("fullName", ClientDTO::getName);
    }

}
