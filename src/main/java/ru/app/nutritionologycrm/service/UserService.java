package ru.app.nutritionologycrm.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.app.nutritionologycrm.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity findUserById(Long id);

    List<UserEntity> findAllUsers();

    void saveUser(UserEntity user);

    UserEntity findByEmail(String username);

    void deleteUserById(Long id);

    UserDetailsService userDetailsService();

    UserEntity getCurrentUser();

}
