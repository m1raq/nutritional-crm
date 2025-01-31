package ru.app.nutritionologycrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.app.nutritionologycrm.entity.DocumentEntity;

import java.util.Date;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    List<DocumentEntity> findAllByUserUsernameAndAndClientId(String username, Long clientId);

    Boolean existsByUserUsernameAndClientIdAndName(String username, Long clientId, String name);
}
