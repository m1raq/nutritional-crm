package ru.app.nutritionologycrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.UserDTO;
import ru.app.nutritionologycrm.entity.UserEntity;
import ru.app.nutritionologycrm.mapper.UserMapper;
import ru.app.nutritionologycrm.repository.UserRepository;
import ru.app.nutritionologycrm.service.UserService;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO findUserById(Long id) {
        log.info("Attempt to load user by id: {}", id);
        return userMapper.toDTO(userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException(id.toString())));
    }

    @Override
    public List<UserDTO> findAllUsers() {
        log.info("Attempt to load all users");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public void saveUser(UserEntity user) {
        log.info("Attempt to save new user: {}", user.toString());
        userRepository.save(user);
    }

    @Override
    public UserEntity findByUsername(String username) {
        log.info("Attempt to load user by username: {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Attempt to delete user by id: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::findByUsername;
    }

    @Override
    public UserDTO getCurrentUser() {
        return userMapper.toDTO(findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
    }


    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
