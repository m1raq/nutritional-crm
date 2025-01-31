package ru.app.nutritionologycrm.frontend.page.client.recommendation;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.frontend.page.client.ClientDashboardForm;
import ru.app.nutritionologycrm.service.RecommendationService;

@PageTitle("Рекомендации")
@Lazy
@RolesAllowed("USER")
@PreserveOnRefresh
@Component
@Route("recommendations")
public class RecommendationListView extends VerticalLayout implements HasUrlParameter<String> {

    private final RecommendationService recommendationService;

    private static SecurityContext context;

    private RecommendationListForm recommendationListForm;

    private HorizontalLayout dashboardLayout;


    @Autowired
    public RecommendationListView(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        SecurityContextHolder.setContext(context == null
                ? SecurityContextHolder.getContext() : context);

        if (recommendationListForm != null) {
            remove(recommendationListForm, dashboardLayout);
        }

        dashboardLayout = new HorizontalLayout();
        dashboardLayout.setJustifyContentMode(JustifyContentMode.START);
        dashboardLayout.setAlignItems(Alignment.START);

        ClientDashboardForm clientDashboardForm = new ClientDashboardForm(context == null
                ? SecurityContextHolder.getContext() : context);
        dashboardLayout.add(clientDashboardForm);

        recommendationListForm = new RecommendationListForm(recommendationService
                .findRecommendationByClientId(Long.valueOf(parameter))
                ,  context == null ? SecurityContextHolder.getContext() : context);

        recommendationListForm.setWidth("500px");
        setHorizontalComponentAlignment(Alignment.CENTER, recommendationListForm);
        add(dashboardLayout, recommendationListForm);
    }

    public static void initSecurity(SecurityContext securityContext) {
        context = securityContext;
        SecurityContextHolder.setContext(securityContext);
    }
}
