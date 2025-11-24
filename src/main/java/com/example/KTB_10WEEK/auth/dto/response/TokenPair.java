package com.example.KTB_10WEEK.auth.dto.response;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenPair {
    private String accessToken;
    private String refreshToken;
}
