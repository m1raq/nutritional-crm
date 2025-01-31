package ru.app.nutritionologycrm.frontend.page.client.document;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.upload.MultiFileReceiver;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.entity.DocumentEntity;
import ru.app.nutritionologycrm.service.DocumentService;


@Setter
@Getter
public class DocumentUploadForm extends FormLayout {

    private Upload uploadField;

    private final Span errorField;

    private final DocumentService documentService;

    private static ClientDTO actualClient;

    private static SecurityContext securityContext;

    public DocumentUploadForm(File uploadFolder, DocumentService documentService) {
        this.documentService = documentService;

        uploadField = new Upload(createFileReceiver(uploadFolder));
        uploadField.setMaxFiles(100);
        uploadField.setMaxFileSize(50 * 1024 * 1024);

        errorField = new Span();
        errorField.setVisible(false);
        errorField.getStyle().set("color", "red");

        uploadField.addFailedListener(e -> showErrorMessage(e.getReason().getMessage()));
        uploadField.addFileRejectedListener(e -> showErrorMessage(e.getErrorMessage()));

        add(uploadField, errorField);
    }

    public void hideErrorField() {
        errorField.setVisible(false);
    }

    private Receiver createFileReceiver(File uploadFolder) {
        return (MultiFileReceiver) (filename, mimeType) -> {
            String finalFileName = filename.substring(0, filename.lastIndexOf("."))
                    + "[" + actualClient.getName() + "]" + filename.substring(filename.lastIndexOf("."));

            File file = new File(uploadFolder, finalFileName);
            SecurityContextHolder.setContext(securityContext);

            DocumentEntity document = new DocumentEntity();
            document.setName(finalFileName);

            documentService.save(document, actualClient.getId());

            UI.getCurrent().navigate("documents");
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e.getCause());
            }
        };
    }

    private void showErrorMessage(String message) {
        errorField.setVisible(true);
        errorField.setText(message);
    }

    public static void initActualClient(ClientDTO client) {
        actualClient = client;
    }

    public static void initSecurity(SecurityContext context) {
        securityContext = context;
    }

}
