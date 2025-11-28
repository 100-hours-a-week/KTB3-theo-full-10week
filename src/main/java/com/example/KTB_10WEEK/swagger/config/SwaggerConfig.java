package com.example.KTB_10WEEK.swagger.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "아무말 대잔치 커뮤니티 API 명세서",
                version = "v1.0.0",
                description = "이 문서는 아무말 대잔치 커뮤니티에서 제공하는 API를 설명합니다."
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth", // 보안 스킴의 이름
        type = SecuritySchemeType.HTTP, // 인증 타입
        scheme = "bearer", // 스킴 유형
        bearerFormat = "JWT" // 토큰 형식
)
@Configuration
public class SwaggerConfig {
}
