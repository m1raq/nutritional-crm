package ru.app.nutritionologycrm.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.app.nutritionologycrm.dto.ResponseMessageDTO;
import ru.app.nutritionologycrm.entity.RoleType;
import ru.app.nutritionologycrm.security.SecurityService;
import ru.app.nutritionologycrm.dto.security.AuthRequestDTO;
import ru.app.nutritionologycrm.dto.security.AuthResponseDTO;
import ru.app.nutritionologycrm.dto.security.RefreshTokenRequestDTO;
import ru.app.nutritionologycrm.dto.security.RefreshTokenResponseDTO;


@Slf4j
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final SecurityService securityService;

    @Autowired
    public AuthController(SecurityService securityService) {
        this.securityService = securityService;
    }


    @Operation(
            summary = "Регистрация пользователя",
            description = """
                    Введите адрес эл.почты и пароль.
                    
                    Можно зарегистрироваться, как исполнитель & заказчик.
                    При повторной регистрации с одной из двух сторон необходимо вводить данные, введенные при первичной
                    регистрации.
                    
                    Варианты параметра role:\s
                    - executor - исполнитель
                    - customer - заказчик
                    """
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO authRequestDTO,
                                      @RequestParam RoleType role) {
        securityService.register(authRequestDTO);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Регистрация прошла успешно")
                .build(), HttpStatus.OK);
    }


    @Operation(
            summary = "Логин",
            description = "Введите адрес эл.почты и пароль." +
                    " Из ответа использовать token для Bearer аунтефикации при дальнейших запросах"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        return new ResponseEntity<>(securityService.authenticateUser(authRequestDTO), HttpStatus.OK);
    }


    @Operation(
            summary = "Обновить токен"
    )
    @PutMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken
            (@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return new ResponseEntity<>(securityService.refreshToken(refreshTokenRequestDTO), HttpStatus.OK);
    }

}
