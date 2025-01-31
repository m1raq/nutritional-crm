package ru.app.nutritionologycrm.frontend.page.client;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.frontend.component.SideNavItemWithClickNotifier;
import ru.app.nutritionologycrm.frontend.page.client.biomarker.BiomarkerView;
import ru.app.nutritionologycrm.frontend.page.client.card.ClientCardView;
import ru.app.nutritionologycrm.frontend.page.client.document.UploadDownloadView;
import ru.app.nutritionologycrm.frontend.page.client.medical.history.MedicalHistoryView;
import ru.app.nutritionologycrm.frontend.page.client.recommendation.RecommendationView;

@Setter
@Getter
public class ClientDashboardForm extends HorizontalLayout {

    private SideNav dashBoard;

    private static ClientDTO actualClient;

    public ClientDashboardForm(SecurityContext securityContext) {
        dashBoard = new SideNav();

        SideNavItemWithClickNotifier generalInfo
                = new SideNavItemWithClickNotifier("Общая информация");
        SideNavItemWithClickNotifier medicalHistory
                = new SideNavItemWithClickNotifier("Анамнез");
        SideNavItemWithClickNotifier biomarker
                = new SideNavItemWithClickNotifier("Исследования");
        SideNavItemWithClickNotifier documents
                = new SideNavItemWithClickNotifier("Документы");
        SideNavItemWithClickNotifier meet
                = new SideNavItemWithClickNotifier("Консультация");
        SideNavItemWithClickNotifier recommendations
                = new SideNavItemWithClickNotifier("Рекомендации");

        generalInfo.addClickListener(event -> {
            SecurityContextHolder.setContext(securityContext);
            ClientCardView.initSecurity(securityContext);

            UI.getCurrent().navigate("client/" + actualClient.getId());
        });

        medicalHistory.addClickListener(event -> {
            SecurityContextHolder.setContext(securityContext);
            MedicalHistoryView.initSecurity(securityContext);

            UI.getCurrent().navigate("medical-history/" + actualClient.getMedicalHistory().getId());
        });

        biomarker.addClickListener(event -> {
            SecurityContextHolder.setContext(securityContext);
            BiomarkerView.initSecurity(securityContext);

            UI.getCurrent().navigate("biomarkers/" + actualClient.getId());
        });

        documents.addClickListener(event -> {
            SecurityContextHolder.setContext(securityContext);
            UploadDownloadView.initSecurity(securityContext);

            UI.getCurrent().navigate("documents");
        });

        meet.addClickListener(event -> {
            SecurityContextHolder.setContext(securityContext);

            UI.getCurrent().navigate("meet");
        });

        recommendations.addClickListener(event -> {
            SecurityContextHolder.setContext(securityContext);
            RecommendationView.initSecurity(securityContext);

            UI.getCurrent().navigate("recommendations/" + actualClient.getId());
        });

        SecurityContextHolder.setContext(securityContext);
        ClientCardView.initSecurity(securityContext);

        dashBoard.addItem(generalInfo, medicalHistory, biomarker, documents, meet, recommendations);
        add(dashBoard);
    }

    public static void initActualClient(ClientDTO dto) {
        actualClient = dto;
    }

}
