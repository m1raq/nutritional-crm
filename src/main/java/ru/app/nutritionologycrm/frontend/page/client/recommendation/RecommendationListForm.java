package ru.app.nutritionologycrm.frontend.page.client.recommendation;

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
import ru.app.nutritionologycrm.dto.recommendation.RecommendationDTO;
import ru.app.nutritionologycrm.frontend.VaadinComponentBuilder;

import java.util.List;

@Setter
@Getter
public class RecommendationListForm extends VerticalLayout{

    private static Grid<RecommendationDTO> grid;

    private static String backButtonStatus;

    private Button backButton;

    private static ClientDTO actualClient;

    public RecommendationListForm(List<RecommendationDTO> recommendations, SecurityContext context) {
        H2 title = new H2("Рекомендации");

        Button addButton = new Button("Новая рекомендация");
        addButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addButton.addClickListener(event -> {
            SecurityContextHolder.setContext(context);
            RecommendationAddView.initSecurity(context);


            UI.getCurrent().navigate("recommendation-create/" + actualClient.getId());
        });

        grid = new Grid<>(RecommendationDTO.class, false);
        Grid.Column<RecommendationDTO> nameColumn = grid.addColumn(createPersonRenderer())
                .setWidth("460px").setFlexGrow(0);

        GridListDataView<RecommendationDTO> dataView = grid.setItems(recommendations);
        RecommendationListForm.RecommendationFilter recommendationFilter = new RecommendationListForm
                .RecommendationFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(nameColumn).setComponent(
                VaadinComponentBuilder.createFilterHeader(recommendationFilter::setName));

        grid.addItemClickListener(event -> {

            SecurityContextHolder.setContext(context);

            RecommendationView.initSecurity(context);
            RecommendationForm.initActualClient(actualClient);

            UI.getCurrent().navigate("recommendation/" + event.getItem().getId());
        });

        backButton = VaadinComponentBuilder
                .buildBackButton(context, "clients/" + backButtonStatus, UI.getCurrent());
        add(title, addButton, grid, backButton);
    }

    public static void initBackButtonStatus(String status) {
        backButtonStatus = status;
    }

    public static void initActualClient(ClientDTO client) {
        actualClient = client;
    }

    private static class RecommendationFilter {
        private final GridListDataView<RecommendationDTO> dataView;

        private String name;

        public RecommendationFilter(GridListDataView<RecommendationDTO> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setName(String name) {
            this.name = name;
            this.dataView.refreshAll();
        }


        public boolean test(RecommendationDTO recommendation) {

            return matches(recommendation.getName(), name);
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }


    private static Renderer<RecommendationDTO> createPersonRenderer() {
        return LitRenderer.<RecommendationDTO>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "  <vaadin-avatar img=\"${item.pictureUrl}\" name=\"${item.fullName}\"></vaadin-avatar>"
                                + "  <span> ${item.fullName} </span>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("fullName", RecommendationDTO::getName);
    }
}
