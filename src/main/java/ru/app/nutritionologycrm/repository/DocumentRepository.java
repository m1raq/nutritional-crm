package ru.app.nutritionologycrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.app.nutritionologycrm.entity.DocumentEntity;

import java.util.Date;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    Boolean existsByUserUsernameAndName(String username, String name);

    List<DocumentEntity> findAllByUserUsername(String username);

    DocumentEntity findByNameAndUserUsername(String name, String username);

    List<DocumentEntity> findByDateAndUserUsername(Date date, String username);
}
