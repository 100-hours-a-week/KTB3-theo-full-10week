package com.example.KTB_10WEEK.auth.service;

import com.example.KTB_10WEEK.security.exception.*;
import com.example.KTB_10WEEK.security.role.RoleConfig;
import com.example.KTB_10WEEK.auth.dto.response.TokenPair;
import com.example.KTB_10WEEK.auth.entity.RefreshToken;
import com.example.KTB_10WEEK.auth.repository.RefreshTokenRepository;
import com.example.KTB_10WEEK.auth.service.decoder.Decoder;
import com.example.KTB_10WEEK.auth.service.encoder.Encoder;
import com.example.KTB_10WEEK.auth.service.encryption.Encrypt;
import com.example.KTB_10WEEK.auth.service.property.TokenProperty;
import com.example.KTB_10WEEK.user.entity.Role;
import com.example.KTB_10WEEK.user.entity.User;
import com.example.KTB_10WEEK.user.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@Service
@Transactional
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final TokenProperty tokenProperty;

    private final Encoder encoder;
    private final Decoder decoder;
    private final Encrypt encryptor;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public TokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository,
                        Encoder encoder, Decoder decoder
            , Encrypt encryptor, TokenProperty tokenProperty) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.decoder = decoder;
        this.encryptor = encryptor;
        this.tokenProperty = tokenProperty;
    }

    public TokenPair issueTokens(long userId, Role role) {
        String roleName = role.getRole();
        RoleConfig roleConfig = RoleConfig.from(roleName);

        String accessToken = issueAccessToken(userId, roleConfig);
        String refreshToken = issueRefreshToken(userId, roleConfig);

        TokenPair tokenPair = new TokenPair(accessToken, refreshToken);
        User user = userRepository.getReferenceById(userId);

        refreshTokenRepository.findByUserId(userId).map(token -> {
            token.updateToken(refreshToken);
            return token;
        }).orElseGet(() -> {
            RefreshToken newRefreshToken = new RefreshToken.Builder()
                    .user(user)
                    .token(refreshToken)
                    .build();
            saveRefreshToken(newRefreshToken);
            return newRefreshToken;
        });

        return tokenPair;
    }

    public TokenPair refreshTokens(String refreshToken) {
        Map<String, Object> payload = verifyAndGetPayload(refreshToken);

        long userId = ((Number) payload.get("userId")).longValue();
        String roleName = (String) payload.get("role");

        RoleConfig roleConfig = RoleConfig.from(roleName);

        RefreshToken saved = refreshTokenRepository.findByUserId(userId).orElseThrow(RefreshTokenNotFoundException::new);
        if (!saved.getToken().equals(refreshToken)) {
            throw new AlreadyRotatedTokenException();
        }

        String newAccessToken = issueAccessToken(userId, roleConfig);
        String newRefreshToken = issueRefreshToken(userId, roleConfig);

        saved.updateToken(newRefreshToken);

        return new TokenPair(newAccessToken, newRefreshToken);
    }

    private void saveRefreshToken(RefreshToken refreshToken) {
        try {
            refreshTokenRepository.save(refreshToken);
        } catch (Exception e) {
            throw new FailRefreshTokenSaveException();
        }
    }

    private String issueAccessToken(long userId, RoleConfig roleConfig) {
        return issue(tokenProperty.accessTokenTtlMillis(), "ACCESS_TOKEN", userId, roleConfig);
    }

    private String issueRefreshToken(long userId, RoleConfig roleConfig) {
        return issue(tokenProperty.refreshTokenTtlMillis(), "REFRESH_TOKEN", userId, roleConfig);
    }

    // 토큰 발급
    private String issue(long ttlMillis, String tokenType, long userId, RoleConfig roleConfig) {
        long now = System.currentTimeMillis();
        long exp = now + ttlMillis;
        Map<String, Object> header = Map.of(
                "alg", encryptor.getAlgorithm(),
                "typ", "JWT"
        );

        Map<String, Object> payload = Map.of(
                "iss", tokenProperty.issuer(), // 발급자
                "sub", tokenType, // 제목
                "exp", exp, // 만료 시간
                "iat", now, // 발급 시간
                "userId", userId,
                "role", roleConfig.getRole(),
                "authorities", roleConfig.getPermittedActionsList()
        );

        String headerBase64 = encoder.encodeJson(header);
        String payloadBase64 = encoder.encodeJson(payload);
        String signature = encoder.encodeToString(encryptor.encrypt(headerBase64 + "." + payloadBase64, tokenProperty.secretkey()));

        return headerBase64 + "." + payloadBase64 + "." + signature;
    }

    public void verify(String token) {
        verifyAndGetPayload(token);
    }

    public Map<String, Object> verifyAndGetPayload(String token) {
        String[] parts = splitToken(token);

        String headerPart = parts[0];
        String payloadPart = parts[1];
        String signaturePart = parts[2];

        Map<String, Object> header = base64JsonToMap(headerPart);
        Map<String, Object> payload = base64JsonToMap(payloadPart);

        validateHeader(header);
        validateSignature(headerPart, payloadPart, signaturePart);
        validateExpiration(payload);

        return payload;
    }

    private String[] splitToken(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new NotJwtTokenException();
        }
        return parts;
    }

    private Map<String, Object> base64JsonToMap(String base64) {
        try {
            byte[] json = decoder.decode(base64);
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new NotJwtTokenException();
        }
    }

    private void validateHeader(Map<String, Object> header) {
        if (!header.get("alg").equals(encryptor.getAlgorithm()) || !header.get("typ").equals("JWT")) { // JWT 토큰 타입 확인
            throw new NotJwtTokenException();
        }
    }

    private void validateSignature(String headerPart, String payloadPart, String signaturePart) {
        byte[] encryptedHeaderAndPayload = encryptor.encrypt(headerPart + "." + payloadPart, tokenProperty.secretkey());
        if (!signaturePart.equals(encoder.encodeToString(encryptedHeaderAndPayload))) {
            throw new InvalidTokenSignatureException();
        }
    }

    private void validateExpiration(Map<String, Object> payload) {
        long now = System.currentTimeMillis();
        long exp = ((Number) payload.get("exp")).longValue();
        if (exp < now) { // 만료된 토큰 검증
            throw new AlreadyExpiredTokenException();
        }
    }
}
