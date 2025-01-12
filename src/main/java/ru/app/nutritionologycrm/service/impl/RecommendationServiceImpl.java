package ru.app.nutritionologycrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationCreateRequestDTO;
import ru.app.nutritionologycrm.dto.recommendation.RecommendationUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.RecommendationEntity;
import ru.app.nutritionologycrm.exception.EntityProcessingException;
import ru.app.nutritionologycrm.repository.RecommendationRepository;
import ru.app.nutritionologycrm.service.ClientService;
import ru.app.nutritionologycrm.service.RecommendationService;
import ru.app.nutritionologycrm.service.UserService;

import java.util.List;

@Slf4j
@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final ClientService clientService;

    private final UserService userService;

    @Autowired
    public RecommendationServiceImpl(RecommendationRepository recommendationRepository, ClientService clientService, UserService userService) {
        this.recommendationRepository = recommendationRepository;
        this.clientService = clientService;
        this.userService = userService;
    }

    @Override
    public void save(RecommendationCreateRequestDTO request, Long clientId) {
        log.info("Attempt to save recommendation");

        RecommendationEntity recommendation = new RecommendationEntity();
        recommendation.setFoodRecommendation(request.getFoodRecommendation());
        recommendation.setDrinkingMode(request.getDrinkingMode());
        recommendation.setNutraceuticals(request.getNutraceuticals());
        recommendation.setPhysicalActivity(request.getPhysicalActivity());
        recommendation.setLifeMode(request.getLifeMode());
        recommendation.setStressControl(request.getStressControl());
        recommendation.setClient(clientService.findById(clientId));
        recommendation.setUser(userService.findByUsername(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName()));

        recommendationRepository.save(recommendation);

    }

    @Override
    public void update(RecommendationUpdateRequestDTO updates) {
        log.info("Attempt to update recommendation {}", updates.getId());

        RecommendationEntity recommendation = recommendationRepository.findById(updates.getId())
                .orElseThrow(() -> new EntityProcessingException("Recommendation with id "
                        + updates.getId() + " not found"));

        recommendation.setFoodRecommendation(updates.getFoodRecommendation());
        recommendation.setDrinkingMode(updates.getDrinkingMode());
        recommendation.setNutraceuticals(updates.getNutraceuticals());
        recommendation.setPhysicalActivity(updates.getPhysicalActivity());
        recommendation.setLifeMode(updates.getLifeMode());
        recommendation.setStressControl(updates.getStressControl());
        recommendationRepository.save(recommendation);
    }

    @Override
    public List<RecommendationEntity> findRecommendationByClientId(Long clientId) {
        log.info("Attempt to find recommendation by client id {}", clientId);
        return recommendationRepository.findAllByClientId(clientId);
    }
}
