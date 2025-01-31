package ru.app.nutritionologycrm.frontend.page.client.document;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.frontend.page.client.ClientDashboardForm;
import ru.app.nutritionologycrm.service.DocumentService;

import java.io.File;

@RolesAllowed("USER")
@Lazy
@PreserveOnRefresh
@Component
@Route("documents")
public class UploadDownloadView extends VerticalLayout implements BeforeEnterObserver {

    private final DocumentService documentService;

    private static SecurityContext securityContext;

    private DocumentUploadForm uploadArea;

    private DocumentListForm documentListForm;

    private HorizontalLayout dashboardLayout;

    @Autowired
    public UploadDownloadView(DocumentService documentService) {
        this.documentService = documentService;
    }

    private static File getUploadFolder() {
        File folder = new File("/app/uploaded-files");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    public static void initSecurity(SecurityContext context) {
        securityContext = context;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (uploadArea != null) {
            remove(dashboardLayout, uploadArea, documentListForm);
        }

        SecurityContextHolder.setContext(securityContext);

        File uploadFolder = getUploadFolder();
        uploadArea = new DocumentUploadForm(uploadFolder, this.documentService);
        documentListForm = new DocumentListForm(securityContext, uploadFolder, documentService);
        documentListForm.setMaxWidth("500px");

        ClientDashboardForm dashboardArea = new ClientDashboardForm(securityContext);

        dashboardLayout = new HorizontalLayout();
        dashboardLayout.setJustifyContentMode(JustifyContentMode.START);
        dashboardLayout.setAlignItems(Alignment.START);
        dashboardLayout.add(dashboardArea);

        uploadArea.getUploadField().addSucceededListener(e -> uploadArea.hideErrorField());

        setHorizontalComponentAlignment(Alignment.CENTER, uploadArea, documentListForm);
        add(dashboardLayout, documentListForm,uploadArea);
    }
}
