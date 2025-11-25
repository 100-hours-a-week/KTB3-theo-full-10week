package com.example.KTB_10WEEK.auth.service;

import com.example.KTB_10WEEK.app.security.exception.*;
import com.example.KTB_10WEEK.auth.dto.response.TokenPair;
import com.example.KTB_10WEEK.auth.entity.RefreshToken;
import com.example.KTB_10WEEK.auth.repository.RefreshTokenRepository;
import com.example.KTB_10WEEK.auth.service.decoder.Decoder;
import com.example.KTB_10WEEK.auth.service.encoder.Encoder;
import com.example.KTB_10WEEK.auth.service.encryption.Encrypt;
import com.example.KTB_10WEEK.auth.service.property.TokenProperty;
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
    private final TokenProperty tokenProperty;

    private final Encoder encoder;
    private final Decoder decoder;
    private final Encrypt encryptor;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public TokenService(RefreshTokenRepository refreshTokenRepository, Encoder encoder, Decoder decoder
            , Encrypt encryptor, TokenProperty tokenProperty) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.encoder = encoder;
        this.decoder = decoder;
        this.encryptor = encryptor;
        this.tokenProperty = tokenProperty;
    }

    public TokenPair issueTokens(Long userId, String email) {

        String accessToken = issueAccessToken(userId, email);
        String refreshToken = issueRefreshToken(userId, email);

        TokenPair tokenPair = new TokenPair(accessToken, refreshToken);

        RefreshToken rt = refreshTokenRepository.findByUserId(userId).map(token -> {
            token.updateToken(refreshToken);
            return token;
        }).orElseGet(() -> {
            RefreshToken newRefreshToken = new RefreshToken.Builder()
                    .userId(userId)
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
        String email = (String) payload.get("email");

        RefreshToken saved = refreshTokenRepository.findByUserId(userId).orElseThrow(() -> new RefreshTokenNotFoundException());
        if (!saved.getToken().equals(refreshToken)) {
            throw new AlreadyRotatedTokenException();
        }

        String newAccessToken = issueAccessToken(userId, email);
        String newRefreshToken = issueRefreshToken(userId, email);

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

    private String issueAccessToken(Long userId, String email) {
        return issue(tokenProperty.accessTokenTtlMillis(), "ACCESS_TOKEN", userId, email);
    }

    private String issueRefreshToken(Long userId, String email) {
        return issue(tokenProperty.refreshTokenTtlMillis(), "REFRESH_TOKEN", userId, email);
    }

    // 토큰 발급
    private String issue(long ttlMillis, String tokenType, Long userId, String email) {
        long now = System.currentTimeMillis();
        long exp = now + ttlMillis;
        Map<String, Object> header = Map.of(
                "alg", encryptor.getAlgorithm(),
                "typ", "JWT"
        );

        Map<String, Object> payload = Map.of(
                "iss", tokenProperty.issuer(), // 발급자
                "sub", tokenType, // 제목
                "userId", userId,
                "email", email, // 유저 이메일
                "exp", exp, // 만료 시간
                "iat", now // 발급 시간
        );

        String headerBase64 = encoder.encodeJson(header);
        String payloadBase64 = encoder.encodeJson(payload);
        String signature = encoder.encodeToString(encryptor.encrypt(headerBase64 + "." + payloadBase64, tokenProperty.secretkey()));
        String token = headerBase64 + "." + payloadBase64 + "." + signature;

        return token;
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
