package com.todayseafood.api.auth.service;

import com.todayseafood.api.app.response.BaseResponse;
import com.todayseafood.api.app.response.ResponseMessage;
import com.todayseafood.api.auth.dto.request.LoginRequestDto;
import com.todayseafood.api.auth.dto.response.LoginResponseDto;
import com.todayseafood.api.auth.dto.response.LoginWithTokenResponseDto;
import com.todayseafood.api.auth.dto.response.TokenPair;
import com.todayseafood.api.post.repository.PostLikeRepository;
import com.todayseafood.api.user.entity.Role;
import com.todayseafood.api.user.entity.User;
import com.todayseafood.api.user.exception.UserNotFoundException;
import com.todayseafood.api.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthService(TokenService tokenService, UserRepository userRepository, PostLikeRepository postLikeRepository, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.postLikeRepository = postLikeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginWithTokenResponseDto login(LoginRequestDto req) {
        String email = req.getEmail();
        String rawPassword = req.getPassword();

        System.out.println(email);
        System.out.println(rawPassword);
        boolean isLoginSuccess = false;
        BaseResponse<LoginResponseDto> loginResponse;

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Set<Long> likedPostIds = postLikeRepository.findLikePostIdsByUserId(user.getId());

        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            isLoginSuccess = true;
            loginResponse = new BaseResponse(ResponseMessage.LOGIN_SUCCESS, LoginResponseDto.success(user, likedPostIds, isLoginSuccess));
            TokenPair issuedTokens = issueTokens(user.getId(), user.getRole());
            return new LoginWithTokenResponseDto(loginResponse, issuedTokens);
        } else {
            loginResponse = new BaseResponse(ResponseMessage.LOGIN_FAIL, LoginResponseDto.fail());
            return new LoginWithTokenResponseDto(loginResponse, null);
        }
    }

    private TokenPair issueTokens(long userId, Role role) {
        return tokenService.issueTokens(userId, role);
    }

    public TokenPair refreshTokens(String refreshToken) {
        return tokenService.refreshTokens(refreshToken);
    }

    public BaseResponse logout() {

        return new BaseResponse(ResponseMessage.LOGOUT_SUCCESS, User.builder().build());
    }

}
