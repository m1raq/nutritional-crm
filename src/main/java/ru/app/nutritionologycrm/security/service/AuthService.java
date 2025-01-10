package ru.app.nutritionologycrm.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.dto.JwtAuthResponseDTO;
import ru.app.nutritionologycrm.dto.LoginRequestDTO;
import ru.app.nutritionologycrm.dto.RegisterRequestDTO;
import ru.app.nutritionologycrm.entity.RoleType;
import ru.app.nutritionologycrm.entity.UserEntity;
import ru.app.nutritionologycrm.service.UserService;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthService(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder
            , AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public JwtAuthResponseDTO signUp(RegisterRequestDTO request) {

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(RoleType.ROLE_USER);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        userService.saveUser(user);

        return JwtAuthResponseDTO.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthResponseDTO signIn(LoginRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());

        return JwtAuthResponseDTO
                .builder()
                .token(jwtService.generateToken(user))
                .build();
    }
}
