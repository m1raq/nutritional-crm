package ru.app.nutritionologycrm.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.entity.RoleType;
import ru.app.nutritionologycrm.entity.UserEntity;
import ru.app.nutritionologycrm.exception.EntityProcessingException;
import ru.app.nutritionologycrm.repository.UserRepository;
import ru.app.nutritionologycrm.dto.security.AuthRequestDTO;
import ru.app.nutritionologycrm.dto.security.AuthResponseDTO;
import ru.app.nutritionologycrm.dto.security.RefreshTokenRequestDTO;
import ru.app.nutritionologycrm.dto.security.RefreshTokenResponseDTO;
import ru.app.nutritionologycrm.entity.RefreshTokenEntity;
import ru.app.nutritionologycrm.security.jwt.JwtUtils;
import ru.app.nutritionologycrm.security.jwt.RefreshTokenService;
import ru.app.nutritionologycrm.service.UserService;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SecurityService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    private final UserRepository userRepository;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityService(AuthenticationManager authenticationManager, JwtUtils jwtUtils
            , RefreshTokenService refreshTokenService, UserRepository userRepository
            , UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDTO authenticateUser(AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequestDTO.getUsername(),
                authRequestDTO.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserEntity userDetails = (UserEntity) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return AuthResponseDTO.builder()
                .id(userDetails.getId())
                .token(jwtUtils.generateJwtToken(userDetails))
                .refreshToken(refreshToken.getToken())
                .username(userDetails.getUsername())
                .roles(roles)
                .build();
    }


    public void register(AuthRequestDTO authRequestDTO) {
        log.info("Попытка регистрации под адресом {}", authRequestDTO.getUsername());

        if (userRepository.existsByUsername(authRequestDTO.getUsername())) {
            throw new EntityProcessingException("Пользователь с таким email уже существует");
        }

        UserEntity user = new UserEntity();
        user.setUsername(authRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(authRequestDTO.getPassword()));
        user.setClients(new ArrayList<>());
        user.setDocuments(new ArrayList<>());
        user.setMeets(new ArrayList<>());
        user.setBiomarkers(new ArrayList<>());
        user.setRecommendations(new ArrayList<>());
        user.setRole(RoleType.ROLE_USER);
        userRepository.save(user);

    }

    public RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO request){
        String requestRefreshToken = request.getRefreshToken();

        try {
            return refreshTokenService.findByRefreshToken(requestRefreshToken)
                    .map(refreshTokenService::checkRefreshToken)
                    .map(RefreshTokenEntity::getUserId)
                    .map(userId -> {
                        try {
                            UserEntity tokenOwner = userRepository.findById(userId).orElseThrow(Exception::new);
                            String token = jwtUtils.generateTokenFromUsername(tokenOwner.getUsername());
                            return new RefreshTokenResponseDTO(token, refreshTokenService.createRefreshToken(userId).getToken());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).orElseThrow(() -> new Exception("Refresh token not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
