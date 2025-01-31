package ru.app.nutritionologycrm.service;


import ru.app.nutritionologycrm.entity.UserEntity;


public interface UserService {

    UserEntity findByUsername(String username);

}
