package ru.app.nutritionologycrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.entity.UserEntity;
import ru.app.nutritionologycrm.repository.UserRepository;
import ru.app.nutritionologycrm.service.UserService;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity findUserById(Long id) {
        log.info("Attempt to load user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException(id.toString()));
    }

    @Override
    public List<UserEntity> findAllUsers() {
        log.info("Attempt to load all users");
        return userRepository.findAll();
    }

    @Override
    public void saveUser(UserEntity user) {
        log.info("Attempt to save new user: {}", user.toString());
        userRepository.save(user);
    }

    @Override
    public UserEntity findByEmail(String username) {
        log.info("Attempt to load user by username: {}", username);
        return userRepository.findByEmail(username);
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Attempt to delete user by id: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::findByEmail;
    }

    @Override
    public UserEntity getCurrentUser() {
        return findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }


}
