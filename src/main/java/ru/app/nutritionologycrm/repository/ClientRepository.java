package ru.app.nutritionologycrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.app.nutritionologycrm.entity.ClientEntity;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    ClientEntity findByContacts(String contacts);

    List<ClientEntity> findAllByAge(Integer age);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE client SET status = :status WHERE id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") String status);

    Boolean existsByContacts(String contacts);

    List<ClientEntity> findAllByUserUsername(String username);

    List<ClientEntity> findAllByNameAndUserUsername(String name, String username);
}
