package ru.app.nutritionologycrm.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.entity.RefreshTokenEntity;
import ru.app.nutritionologycrm.repository.RefreshTokenRepository;


import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {


    @Value("${security.jwt.jwtRefreshExpirationMs}")
    private Duration refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;


    public Optional<RefreshTokenEntity> findByRefreshToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshTokenEntity createRefreshToken(Long userId){

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration.toMillis()));
        refreshToken.setUserId(userId);
        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }


    public RefreshTokenEntity checkRefreshToken(RefreshTokenEntity token){
        if(token.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(token);
            try {
                throw new Exception("RefreshToken was expired. Repeat sign in action");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return token;
        }
    }

}
