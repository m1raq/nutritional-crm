package ru.app.nutritionologycrm.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.app.nutritionologycrm.dto.JwtAuthResponseDTO;
import ru.app.nutritionologycrm.dto.LoginRequestDTO;
import ru.app.nutritionologycrm.dto.RegisterRequestDTO;
import ru.app.nutritionologycrm.security.service.AuthService;


@Slf4j
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public JwtAuthResponseDTO signUp(@RequestBody @Valid RegisterRequestDTO request) {
        return authService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthResponseDTO signIn(@RequestBody @Valid LoginRequestDTO request) {
        return authService.signIn(request);
    }
}
