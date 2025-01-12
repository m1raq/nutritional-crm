package ru.app.nutritionologycrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.app.nutritionologycrm.entity.BiomarkerEntity;

import java.util.List;

@Repository
public interface BiomarkerRepository extends JpaRepository<BiomarkerEntity, Long> {

    List<BiomarkerEntity> findAllByUserUsername(String username);

    List<BiomarkerEntity> findAllByClientId(Long id);

}

