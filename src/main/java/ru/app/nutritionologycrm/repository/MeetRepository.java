package ru.app.nutritionologycrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.app.nutritionologycrm.entity.MeetEntity;

import java.util.List;

@Repository
public interface MeetRepository extends JpaRepository<MeetEntity, Long> {

    List<MeetEntity> findAllByClientContacts(String contacts);

}
