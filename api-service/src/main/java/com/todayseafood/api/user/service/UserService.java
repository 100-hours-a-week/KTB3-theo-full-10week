package com.todayseafood.api.user.service;


import com.todayseafood.api.app.aop.aspect.log.Loggable;
import com.todayseafood.api.app.response.BaseResponse;
import com.todayseafood.api.app.response.ResponseMessage;
import com.todayseafood.api.app.storage.ProfileImageStorage;
import com.todayseafood.api.user.dto.request.*;
import com.todayseafood.api.user.dto.response.*;
import com.todayseafood.api.user.entity.Role;
import com.todayseafood.api.user.entity.User;
import com.todayseafood.api.user.exception.*;
import com.todayseafood.api.user.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ProfileImageStorage profileImageStorage;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ProfileImageStorage profileImageStorage, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileImageStorage = profileImageStorage;
        this.passwordEncoder = passwordEncoder;
    }

    @Loggable
    public BaseResponse<RegistUserResponseDto> register(RegistUserRequestDto req) {
        String email = req.getEmail();
        String nickname = req.getNickname();
        String profileImageName = profileImageStorage.saveProfileImage(req.getProfileImage());

        if (userRepository.existsByEmail(email)) throw new EmailAlreadyRegisteredException();
        if (userRepository.existsByNickname(nickname)) throw new NicknameAlreadyRegisteredException();

        User toSave = User.builder()
                .email(email)
                .password(passwordEncoder.encode(req.getPassword()))
                .nickname(req.getNickname())
                .profileImage(profileImageName)
                .role(Role.USER)
                .build();

        User saved = userRepository.save(toSave);
        return new BaseResponse(ResponseMessage.USER_REGISTER_SUCCESS, RegistUserResponseDto.toDto(saved));

    }

    @Loggable
    @PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
    public BaseResponse<FindUserResponseDto> findById(@P("userId") Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return new BaseResponse(ResponseMessage.USERINFO_LOAD_SUCCESS, FindUserResponseDto.toDto(user));
    }

    @Loggable
    @PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
    public BaseResponse deleteById(@P("userId") Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        String profileImage = user.getProfileImage();

        boolean isDelete = profileImageStorage.deleteProfileImage(profileImage);
        if (!isDelete) {
            throw new DeleteProfileImageFailException();
        }
        userRepository.deleteById(userId);

        return new BaseResponse(ResponseMessage.USER_DELETE_SUCCESS);
    }

    @Loggable
    public BaseResponse<CheckNicknameAvailabilityResponseDto> doubleCheckNickname(CheckNicknameAvailabilityRequestDto req) {
        String nickname = req.getNickname();
        if (userRepository.existsByNickname(nickname)) {
            return new BaseResponse(ResponseMessage.NICKNAME_IS_NOT_AVAILABLE,
                    CheckNicknameAvailabilityResponseDto.toDto(nickname, false));
        }
        return new BaseResponse(ResponseMessage.NICKNAME_IS_AVAILABLE,
                CheckNicknameAvailabilityResponseDto.toDto(nickname, true));
    }

    @Loggable
    public BaseResponse<CheckEmailAvailabilityResponseDto> doubleCheckEmail(CheckEmailAvailabilityRequestDto req) {
        String email = req.getEmail();
        if (userRepository.existsByEmail(email)) {
            return new BaseResponse(ResponseMessage.EMAIL_IS_NOT_AVAILABLE,
                    CheckEmailAvailabilityResponseDto.toDto(email, false));
        }
        return new BaseResponse(ResponseMessage.EMAIL_IS_AVAILABLE,
                CheckEmailAvailabilityResponseDto.toDto(email, true));
    }

    @Loggable
    @PreAuthorize("#userId == principal.userId")
    public BaseResponse<EditProfileResponseDto> editProfile(@P("userId")Long userId, EditProfileRequestDto req) {
        User toUpdate = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if(req.getProfileImage() != null && !req.getProfileImage().isEmpty()) {
            String oldFileName = req.getOldFileName();
            String newProfileImageUrl = profileImageStorage.updateProfileImage(req.getProfileImage(), oldFileName);
            toUpdate.updateProfileImage(newProfileImageUrl);
        }

        String newNickname = req.getNickname();
        if (!toUpdate.getNickname().equals(newNickname)) {
            toUpdate.updateNickname(newNickname);
        }

        toUpdate.updateNowTime(LocalDateTime.now());

        return new BaseResponse(ResponseMessage.EDIT_PROFILE_SUCCESS, EditProfileResponseDto.toDto(toUpdate));
    }

    @Loggable
    @PreAuthorize("#userId == principal.userId")
    public BaseResponse<UpdateNicknameResponseDto> editNickname(@P("userId") Long userId, UpdateNicknameRequestDto req) {
        String nickname = req.getNickname();

        if (userRepository.existsByNickname(nickname)) throw new NicknameAlreadyRegisteredException();

        User toUpdate = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        toUpdate.updateNickname(nickname);
        toUpdate.updateNowTime(LocalDateTime.now());

        return new BaseResponse(ResponseMessage.NICKNAME_EDIT_SUCCESS, UpdateNicknameResponseDto.toDto(toUpdate));
    }

    @Loggable
    @PreAuthorize("#userId == principal.userId")
    public BaseResponse<UpdatePasswordResponseDto> changePassword(@P("userId") Long userId, UpdatePasswordRequestDto req) {
        String password = req.getPassword();

        User toUpdate = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        toUpdate.updatePassword(passwordEncoder.encode(password));
        toUpdate.updateNowTime(LocalDateTime.now());

        return new BaseResponse(ResponseMessage.PASSWORD_CHANGE_SUCCESS, UpdatePasswordResponseDto.toDto(toUpdate.getId()));
    }

}
