package com.example.KTB_7WEEK.auth.service;

import com.example.KTB_7WEEK.app.aop.aspect.log.Loggable;
import com.example.KTB_7WEEK.app.response.BaseResponse;
import com.example.KTB_7WEEK.app.response.ResponseMessage;
import com.example.KTB_7WEEK.auth.dto.request.LoginRequestDto;
import com.example.KTB_7WEEK.auth.dto.response.LoginResponseDto;
import com.example.KTB_7WEEK.user.entity.User;
import com.example.KTB_7WEEK.user.exception.UserNotFoundException;
import com.example.KTB_7WEEK.user.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Loggable
    public BaseResponse<LoginResponseDto> login(LoginRequestDto req) {
        String email = req.getEmail();
        String password = req.getPassword();
        boolean isLoginSuccess = false;
        ResponseMessage resMsg = ResponseMessage.LOGIN_FAIL;

                User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());

        if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
            isLoginSuccess = true;
            resMsg = ResponseMessage.LOGIN_SUCCESS;
        }
        return new BaseResponse(resMsg, LoginResponseDto.toDto(user.getId(), isLoginSuccess));
    }

    @Loggable
    public BaseResponse logout() {
        return new BaseResponse(ResponseMessage.LOGOUT_SUCCESS, new User());
    }

}
