package ru.app.nutritionologycrm.frontend.page.client.recommendation;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import ru.app.nutritionologycrm.service.RecommendationService;

@Lazy
@PageTitle("Новая рекомендация")
@Component
@RolesAllowed("USER")
@PreserveOnRefresh
@Route("recommendation-create")
public class RecommendationAddView extends VerticalLayout implements HasUrlParameter<String> {

    private final RecommendationService recommendationService;

    private static SecurityContext securityContext;

    private RecommendationAddForm recommendationAddForm;

    @Autowired
    public RecommendationAddView(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (recommendationAddForm != null) {
            remove(recommendationAddForm);
        }
        recommendationAddForm = new RecommendationAddForm(recommendationService
                , securityContext, Long.valueOf(parameter));

        setHorizontalComponentAlignment(Alignment.CENTER, recommendationAddForm);
        add(recommendationAddForm);
    }

    public static void initSecurity(SecurityContext context) {
        securityContext = context;
    }

}
