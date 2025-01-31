package ru.app.nutritionologycrm.frontend.page.client.biomarker;

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
import ru.app.nutritionologycrm.dto.biomarker.BiomarkerDTO;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.frontend.VaadinComponentBuilder;

import java.util.List;

@Setter
@Getter
public class BiomarkerListForm extends VerticalLayout{

    private static Grid<BiomarkerDTO> grid;

    private Button backButton;

    private static String backButtonStatus;

    private static ClientDTO actualClient;

    public BiomarkerListForm(List<BiomarkerDTO> biomarkers, SecurityContext context) {
        H2 title = new H2("Исследования");

        Button addButton = new Button("Новый биомаркер");
        addButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addButton.addClickListener(event -> {
            SecurityContextHolder.setContext(context);
            BiomarkerAddView.initSecurity(context);


            UI.getCurrent().navigate("biomarker-create/" + actualClient.getId());
        });

        grid = new Grid<>(BiomarkerDTO.class, false);
        Grid.Column<BiomarkerDTO> nameColumn = grid.addColumn(createPersonRenderer())
                .setWidth("460px").setFlexGrow(0);

        GridListDataView<BiomarkerDTO> dataView = grid.setItems(biomarkers);
        BiomarkerFilter biomarkerFilter = new BiomarkerFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(nameColumn).setComponent(
                VaadinComponentBuilder.createFilterHeader(biomarkerFilter::setName));

        grid.addItemClickListener(event -> {

            SecurityContextHolder.setContext(context);

            BiomarkerView.initSecurity(context);
            BiomarkerForm.initActualClient(actualClient);

            UI.getCurrent().navigate("biomarker/" + event.getItem().getId());
        });

        backButton = VaadinComponentBuilder.buildBackButton(context
                , "clients/" + backButtonStatus
                , UI.getCurrent());

        add(title, addButton, grid, backButton);
    }

    public static void initBackButtonStatus(String status) {
        backButtonStatus = status;
    }

    public static void initActualClient(ClientDTO client) {
        actualClient = client;
    }

    private static class BiomarkerFilter {
        private final GridListDataView<BiomarkerDTO> dataView;

        private String name;

        public BiomarkerFilter(GridListDataView<BiomarkerDTO> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setName(String name) {
            this.name = name;
            this.dataView.refreshAll();
        }

        public boolean test(BiomarkerDTO biomarker) {

            return matches(biomarker.getName(), name);
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }


    private static Renderer<BiomarkerDTO> createPersonRenderer() {
        return LitRenderer.<BiomarkerDTO>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "  <vaadin-avatar img=\"${item.pictureUrl}\" name=\"${item.fullName}\"></vaadin-avatar>"
                                + "  <span> ${item.fullName} </span>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("fullName", BiomarkerDTO::getName);
    }
}
