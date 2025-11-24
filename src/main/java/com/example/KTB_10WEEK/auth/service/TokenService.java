package com.example.KTB_10WEEK.auth.service;

import com.example.KTB_10WEEK.app.aop.aspect.log.Loggable;
import com.example.KTB_10WEEK.auth.exception.AlreadyExpiredToken;
import com.example.KTB_10WEEK.auth.exception.FailTokenExpireException;
import com.example.KTB_10WEEK.auth.exception.InvalidTokenException;
import com.example.KTB_10WEEK.auth.repository.TokenRepository;
import com.example.KTB_10WEEK.auth.service.decoder.Decoder;
import com.example.KTB_10WEEK.auth.service.encoder.Encoder;
import com.example.KTB_10WEEK.auth.service.encryption.Encrypt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Component
public class TokenService {
    @Value("${application.auth.secret}")
    private String secret_key;

    private Encoder encoder;
    private Decoder decoder;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private Encrypt encryptor;
    private TokenRepository tokenRepository;

    public TokenService(Encoder encoder, Decoder decoder, Encrypt encryptor, TokenRepository tokenRepository) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.encryptor = encryptor;
        this.tokenRepository = tokenRepository;
    }

    // 토큰 발급
    @Loggable
    public String issue(Duration ttl) {
        long now = System.currentTimeMillis();
        long exp = now + ttl.toMillis();
        Map<String, Object> header = Map.of(
                "alg", encryptor.getAlgorithm(),
                "typ", "JWT"
        );

        Map<String, Object> payload = Map.of(
                "iss", "KTB-5WEEK", // 발급자
                "sub", "token", // 제목
                "exp", exp, // 만료 시간
                "iat", now // 발급 시간
        );

        String headerBase64 = encoder.encodeJson(header);
        String payloadBase64 = encoder.encodeJson(payload);
        String signature = encoder.encodeToString(encryptor.encrypt(headerBase64 + "." + payloadBase64, secret_key));
        String token = headerBase64 + "." + payloadBase64 + "." + signature;

        return token;
    }

    // 토큰 검증
    @Loggable
    public Optional<Boolean> verify(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) return Optional.ofNullable(false);

        if (tokenRepository.isBlackList(token).get()) {
            throw new AlreadyExpiredToken();
        }

        try {
            Map<String, Object> header = objectMapper.readValue(decoder.decode(parts[0]),
                    new TypeReference<Map<String, Object>>() {
                    });
            Map<String, Object> payload = objectMapper.readValue(decoder.decode(parts[1]),
                    new TypeReference<Map<String, Object>>() {
                    });

            if (!header.get("alg").equals(encryptor.getAlgorithm()) || !header.get("typ").equals("JWT")) {
                return Optional.ofNullable(false);
            }

            if (!parts[2].equals(encoder.encodeToString(encryptor.encrypt(parts[0] + "." + parts[1], secret_key)))) {
                return Optional.ofNullable(false);
            }

            long now = System.currentTimeMillis();
            long exp = ((Number) payload.get("exp")).longValue();
            if (exp < now) return Optional.ofNullable(false);

            return Optional.ofNullable(false);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    // 토큰 무효화
    @Loggable
    public boolean expire(String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            tokenRepository.isBlackList(token).orElseThrow(() -> new AlreadyExpiredToken());
            return tokenRepository.toBlackList(token).orElseThrow(() -> new FailTokenExpireException());
        }
        throw new InvalidTokenException();
    }
}
