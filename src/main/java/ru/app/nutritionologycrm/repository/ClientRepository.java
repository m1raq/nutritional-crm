package ru.app.nutritionologycrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.app.nutritionologycrm.entity.ClientEntity;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    ClientEntity findByTgBotChatId(String tgBotChatId);

    Boolean existsByContacts(String contacts);

    List<ClientEntity> findAllByUserUsername(String username);

    Boolean existsByTgBotChatId(String tgBotChatId);



}

