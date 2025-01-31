package ru.app.nutritionologycrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.app.nutritionologycrm.entity.MedicalHistoryEntity;

import java.util.List;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistoryEntity, Long> {

    List<MedicalHistoryEntity> findAllByClientId(Long id);

    MedicalHistoryEntity findByClientId(Long id);


}
