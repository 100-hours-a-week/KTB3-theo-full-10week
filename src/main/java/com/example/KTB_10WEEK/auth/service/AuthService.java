package com.example.KTB_10WEEK.auth.service;

import com.example.KTB_10WEEK.app.response.BaseResponse;
import com.example.KTB_10WEEK.app.response.ResponseMessage;
import com.example.KTB_10WEEK.auth.dto.request.LoginRequestDto;
import com.example.KTB_10WEEK.auth.dto.response.LoginResponseDto;
import com.example.KTB_10WEEK.auth.dto.response.LoginWithTokenResponseDto;
import com.example.KTB_10WEEK.auth.dto.response.TokenPair;
import com.example.KTB_10WEEK.post.repository.PostLikeRepository;
import com.example.KTB_10WEEK.user.entity.User;
import com.example.KTB_10WEEK.user.exception.UserNotFoundException;
import com.example.KTB_10WEEK.user.repository.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        boolean isLoginSuccess = false;
        BaseResponse<LoginResponseDto> loginResponse;

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        Set<Long> likedPostIds = postLikeRepository.findLikePostIdsByUserId(user.getId());

        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            isLoginSuccess = true;
            loginResponse = new BaseResponse(ResponseMessage.LOGIN_SUCCESS, LoginResponseDto.success(user, likedPostIds, isLoginSuccess));
            TokenPair issuedTokens = issueTokens(user.getId(), email);
            return new LoginWithTokenResponseDto(loginResponse, issuedTokens);
        } else {
            loginResponse = new BaseResponse(ResponseMessage.LOGIN_FAIL, LoginResponseDto.fail());
            return new LoginWithTokenResponseDto(loginResponse, null);
        }
    }

    private TokenPair issueTokens(long userId, String email) {
        return tokenService.issueTokens(userId, email);
    }

    public TokenPair refreshTokens(String refreshToken) {
        return tokenService.refreshTokens(refreshToken);
    }

    public BaseResponse logout() {
        return new BaseResponse(ResponseMessage.LOGOUT_SUCCESS, new User());
    }
}
