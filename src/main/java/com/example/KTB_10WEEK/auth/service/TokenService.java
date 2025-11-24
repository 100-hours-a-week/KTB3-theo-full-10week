package com.example.KTB_10WEEK.auth.service;

import com.example.KTB_10WEEK.auth.dto.response.TokenPair;
import com.example.KTB_10WEEK.auth.repository.RefreshTokenRepository;
import com.example.KTB_10WEEK.auth.service.decoder.Decoder;
import com.example.KTB_10WEEK.auth.service.encoder.Encoder;
import com.example.KTB_10WEEK.auth.service.encryption.Encrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;

@Service
@Transactional
public class TokenService {

    @Value("${application.auth.secret}")
    private String SECRET_KEY;
    @Value("${application.auth.token.issuer}")
    private String issuer;
    private final RefreshTokenRepository refreshTokenRepository;
    private Encoder encoder;
    private Decoder decoder;
    private Encrypt encryptor;
    private static final long ACCESS_TOKEN_TTL_MILLS = 1000 * 60 * 15; // 15분
    private static final long REFRESH_TOKEN_TTL_MILLS = 1000 * 60 * 60 * 24 * 7; // 7일

    public TokenService(RefreshTokenRepository refreshTokenRepository, Encoder encoder, Decoder decoder, Encrypt encryptor) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.encoder = encoder;
        this.decoder = decoder;
        this.encryptor = encryptor;
    }

    public TokenPair issueTokens(String email) {
        String accessToken = issueAccessToken(email);
        String refreshToken = issueRefreshToken(email);

        TokenPair tokenPair = new TokenPair(accessToken, refreshToken);

        return tokenPair;
    }

    private String issueAccessToken(String email) {
        return issue(ACCESS_TOKEN_TTL_MILLS, "ACCESS_TOKEN", email);
    }

    private String issueRefreshToken(String email) {
        return issue(REFRESH_TOKEN_TTL_MILLS, "REFRESH_TOKEN", email);
    }

    // 토큰 발급
    private String issue(Long ttlMillis, String tokenType, String email) {
        long now = System.currentTimeMillis();
        long exp = now + ttlMillis;
        Map<String, Object> header = Map.of(
                "alg", encryptor.getAlgorithm(),
                "typ", "jwt"
        );

        Map<String, Object> payload = Map.of(
                "iss", issuer, // 발급자
                "sub", tokenType, // 제목
                "email", email, // 유저 이메일
                "exp", exp, // 만료 시간
                "iat", now // 발급 시간
        );

        String headerBase64 = encoder.encodeJson(header);
        String payloadBase64 = encoder.encodeJson(payload);
        String signature = encoder.encodeToString(encryptor.encrypt(headerBase64 + "." + payloadBase64, SECRET_KEY));
        String token = headerBase64 + "." + payloadBase64 + "." + signature;

        return token;
    }
}
