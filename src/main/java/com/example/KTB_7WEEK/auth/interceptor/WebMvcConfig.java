package com.example.KTB_7WEEK.auth.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final TokenAuthInterceptor tokenAuthInterceptor;

    public WebMvcConfig(TokenAuthInterceptor tokenAuthInterceptor) {
        this.tokenAuthInterceptor = tokenAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenAuthInterceptor);
//                .addPathPatterns("/**");
//                .excludePathPatterns("/auth/**",
//                        "/user",
//                        "/user/nickname/double-check",
//                        "/user/email/double-check",
//                        "/api-docs/**",
//                        "/swagger-ui/**",
//                        "/error/**");
    }
}
