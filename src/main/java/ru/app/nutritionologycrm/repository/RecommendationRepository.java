package ru.app.nutritionologycrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.app.nutritionologycrm.entity.RecommendationEntity;

@Repository
public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Long> {
}
