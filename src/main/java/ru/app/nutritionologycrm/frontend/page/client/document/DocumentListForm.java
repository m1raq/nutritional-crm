package ru.app.nutritionologycrm.frontend.page.client.document;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.server.StreamResource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.app.nutritionologycrm.dto.DocumentDTO;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.frontend.VaadinComponentBuilder;
import ru.app.nutritionologycrm.service.DocumentService;


import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

@Setter
@Getter
public class DocumentListForm extends VerticalLayout {

    private static Grid<DocumentDTO> grid;

    private static String backButtonStatus;

    private final File uploadFolder;

    private static ClientDTO actualClient;

    private final DocumentService documentService;

    private Button backButton;

    public DocumentListForm(SecurityContext context, File uploadFolder, DocumentService documentService) {
        this.uploadFolder = uploadFolder;
        this.documentService = documentService;

        H2 title = new H2("Исследования");

        grid = new Grid<>(DocumentDTO.class, false);
        Grid.Column<DocumentDTO> nameColumn = grid.addColumn(createPersonRenderer())
                .setWidth("230px").setFlexGrow(0);
        Grid.Column<DocumentDTO> dateColumn = grid.addColumn(DocumentDTO::getDate)
                .setWidth("230px").setFlexGrow(0);

        GridListDataView<DocumentDTO> dataView = grid.setItems(documentService
                .findAllByUserUsernameAndAndClientId(context.getAuthentication().getName(), actualClient.getId()));
        DocumentListForm.DocumentFilter documentsFilter = new DocumentListForm.DocumentFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(nameColumn).setComponent(
                VaadinComponentBuilder.createFilterHeader("Название", documentsFilter::setName)
        );
        headerRow.getCell(dateColumn).setComponent(
                VaadinComponentBuilder.createFilterHeader("Дата", value -> {
                    try {
                        LocalDate date = value == null || value.isEmpty()
                                ? null : LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        documentsFilter.setDate(date);
                    } catch (Exception e) {
                        documentsFilter.setDate(null);
                    }
                })
        );

        grid.addItemClickListener(event -> {
            try {

                SecurityContextHolder.setContext(context);

                File file = Arrays.stream(Objects.requireNonNull(uploadFolder.listFiles()))
                        .filter(expected -> expected.getName().equals(event.getItem().getName()))
                        .findFirst()
                        .orElseThrow(() -> new FileNotFoundException("Файл не найден: " + event.getItem().getName()));

                StreamResource streamResource = getStreamResource(file);


                String downloadUrl = UI.getCurrent().getSession()
                        .getResourceRegistry()
                        .registerResource(streamResource)
                        .getResourceUri()
                        .toString();

                UI.getCurrent().getPage().executeJs(
                        "const a = document.createElement('a');" +
                                "a.href = $0;" +
                                "a.download = $1;" +
                                "document.body.appendChild(a);" +
                                "a.click();" +
                                "document.body.removeChild(a);",
                        downloadUrl, file.getName());
            } catch (Exception e) {
                Notification.show("Ошибка при скачивании файла: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
            }
        });

        backButton = VaadinComponentBuilder.buildBackButton(context, "clients/" + backButtonStatus
                , UI.getCurrent());
        add(title, grid, backButton);
    }

    private static StreamResource getStreamResource(File file) {
        StreamResource streamResource = new StreamResource(file.getName(), () -> {
            try {
                return new BufferedInputStream(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Не удалось открыть файл", e);
            }
        });

        streamResource.setContentType("application/octet-stream");
        streamResource.setCacheTime(0);
        return streamResource;
    }

    public static void initBackButtonStatus(String status) {
        backButtonStatus = status;
    }

    public static void initActualClient(ClientDTO client) {
        actualClient = client;
    }


    private static class DocumentFilter {
        private final GridListDataView<DocumentDTO> dataView;

        private String name;
        private LocalDate date;

        public DocumentFilter(GridListDataView<DocumentDTO> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setName(String name) {
            this.name = name;
            this.dataView.refreshAll();
        }

        public void setDate(LocalDate date) {
            this.date = date;
            this.dataView.refreshAll();
        }


        public boolean test(DocumentDTO document) {
            boolean matchesName = matches(document.getName(), name);
            boolean matchesDate = matches(document.getDate().toString(), date == null ? null : date.toString());

            return matchesName && matchesDate;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }


    private static Renderer<DocumentDTO> createPersonRenderer() {
        return LitRenderer.<DocumentDTO>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "  <vaadin-avatar img=\"${item.pictureUrl}\" name=\"${item.fullName}\"></vaadin-avatar>"
                                + "  <span> ${item.fullName} </span>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("fullName", DocumentDTO::getName);
    }

}
