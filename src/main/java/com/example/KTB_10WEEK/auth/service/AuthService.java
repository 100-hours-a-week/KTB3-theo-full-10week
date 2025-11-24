package com.example.KTB_10WEEK.auth.service;

import com.example.KTB_10WEEK.app.aop.aspect.log.Loggable;
import com.example.KTB_10WEEK.app.response.BaseResponse;
import com.example.KTB_10WEEK.app.response.ResponseMessage;
import com.example.KTB_10WEEK.auth.dto.request.LoginRequestDto;
import com.example.KTB_10WEEK.auth.dto.response.LoginResponseDto;
import com.example.KTB_10WEEK.post.repository.PostLikeRepository;
import com.example.KTB_10WEEK.user.entity.User;
import com.example.KTB_10WEEK.user.exception.UserNotFoundException;
import com.example.KTB_10WEEK.user.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    public AuthService(UserRepository userRepository, PostLikeRepository postLikeRepository) {
        this.userRepository = userRepository;
        this.postLikeRepository = postLikeRepository;
    }

    @Loggable
    public BaseResponse<LoginResponseDto> login(LoginRequestDto req) {
        String email = req.getEmail();
        String password = req.getPassword();

        boolean isLoginSuccess = false;
        ResponseMessage resMsg = ResponseMessage.LOGIN_FAIL;

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        Set<Long> likedPostIds = postLikeRepository.findLikePostIdsByUserId(user.getId());

        if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
            isLoginSuccess = true;
            resMsg = ResponseMessage.LOGIN_SUCCESS;
        }
        return new BaseResponse(resMsg, LoginResponseDto.toDto(user, likedPostIds, isLoginSuccess));
    }

    @Loggable
    public BaseResponse logout() {
        return new BaseResponse(ResponseMessage.LOGOUT_SUCCESS, new User());
    }

}
