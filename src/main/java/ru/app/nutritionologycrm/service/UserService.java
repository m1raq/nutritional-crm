package ru.app.nutritionologycrm.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.app.nutritionologycrm.dto.UserDTO;
import ru.app.nutritionologycrm.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserDTO findUserById(Long id);

    List<UserDTO> findAllUsers();

    void saveUser(UserEntity user);

    UserEntity findByUsername(String username);

    void deleteUserById(Long id);

    UserDetailsService userDetailsService();

    UserDTO getCurrentUser();

}
