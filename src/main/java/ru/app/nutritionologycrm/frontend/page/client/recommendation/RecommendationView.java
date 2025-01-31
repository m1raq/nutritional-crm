package ru.app.nutritionologycrm.frontend.page.client.recommendation;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.frontend.page.client.ClientDashboardForm;
import ru.app.nutritionologycrm.service.RecommendationService;

@PageTitle("Рекомендация")
@RolesAllowed("USER")
@PreserveOnRefresh
@Route("recommendation")
@Component
public class RecommendationView extends VerticalLayout implements HasUrlParameter<String> {

    private static SecurityContext securityContext;

    private final RecommendationService recommendationService;

    private RecommendationForm recommendationForm;

    private HorizontalLayout dashboardLayout;

    @Autowired
    public RecommendationView(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (recommendationForm != null) {
            remove(recommendationForm, dashboardLayout);
        }

        RecommendationForm.initSecurity(securityContext);
        dashboardLayout = new HorizontalLayout();
        dashboardLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        dashboardLayout.setAlignItems(FlexComponent.Alignment.START);

        ClientDashboardForm clientDashboardForm = new ClientDashboardForm(securityContext == null
                ? SecurityContextHolder.getContext() : securityContext);
        dashboardLayout.add(clientDashboardForm);

        if (parameter.endsWith("U")) {
            recommendationForm = new RecommendationForm(recommendationService, true
                    , Long.valueOf(parameter.split("-")[0]));
        } else {
            recommendationForm = new RecommendationForm(recommendationService
                    , false, Long.valueOf(parameter));
        }

        recommendationForm.setWidth("500px");
        setHorizontalComponentAlignment(Alignment.CENTER, recommendationForm);
        add(dashboardLayout, recommendationForm);
    }

    public static void initSecurity(SecurityContext context){
        securityContext = context;
    }

}
