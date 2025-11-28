package com.example.KTB_10WEEK.user.service;


import com.example.KTB_10WEEK.app.aop.aspect.log.Loggable;
import com.example.KTB_10WEEK.app.response.BaseResponse;
import com.example.KTB_10WEEK.app.response.ResponseMessage;
import com.example.KTB_10WEEK.app.storage.ProfileImageStorage;
import com.example.KTB_10WEEK.user.entity.User;
import com.example.KTB_10WEEK.user.repository.user.UserRepository;
import com.example.KTB_10WEEK.user.dto.request.*;
import com.example.KTB_10WEEK.user.dto.response.*;
import com.example.KTB_10WEEK.user.exception.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        String profileImageUrl = profileImageStorage.saveProfileImage(req.getProfileImage());

        if (userRepository.existsByEmail(email)) throw new EmailAlreadyRegisteredException();
        if (userRepository.existsByNickname(nickname)) throw new NicknameAlreadyRegisteredException();

        User toSave = new User.Builder()
                .email(email)
                .password(passwordEncoder.encode(req.getPassword()))
                .nickname(req.getNickname())
                .profileImage(profileImageUrl)
                .build();

        User saved = userRepository.save(toSave);
        return new BaseResponse(ResponseMessage.USER_REGISTER_SUCCESS, RegistUserResponseDto.toDto(saved));

    }

    @Loggable
    @PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
    public BaseResponse<FindUserResponseDto> findById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        return new BaseResponse(ResponseMessage.USERINFO_LOAD_SUCCESS, FindUserResponseDto.toDto(user));
    }

    @Loggable
    @PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
    public BaseResponse deleteById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        String profileImage = user.getProfileImage();
        boolean isDelete = profileImageStorage.deleteProfileImage(profileImage);
        if (!isDelete) {
            throw new DeleteProfileImageFailException();
        }
        userRepository.deleteById(userId);

        return new BaseResponse(ResponseMessage.USER_DELETE_SUCCESS, new User());
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
    public BaseResponse<EditProfileResponseDto> editProfile(Long userId, EditProfileRequestDto req) {
        User toUpdate = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        String newNickname = req.getNickname();
        String oldFileName = req.getOldFileName();
        String newProfileImageUrl = profileImageStorage.updateProfileImage(req.getProfileImage(), oldFileName);

        if (!toUpdate.getNickname().equals(newNickname)) {
            toUpdate.updateNickname(newNickname);
        }
        toUpdate.updateProfileImage(newProfileImageUrl);
        toUpdate.updateNowTime();

        return new BaseResponse(ResponseMessage.EDIT_PROFILE_SUCCESS, EditProfileResponseDto.toDto(toUpdate));
    }

    @Loggable
    @PreAuthorize("#userId == principal.userId")
    public BaseResponse<UpdateNicknameResponseDto> editNickname(long userId, NicknameEditRequestDto req) {
        String nickname = req.getNickname();

        if (userRepository.existsByNickname(nickname)) throw new NicknameAlreadyRegisteredException();

        User toUpdate = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        toUpdate.updateNickname(nickname);
        toUpdate.updateNowTime();

        return new BaseResponse(ResponseMessage.NICKNAME_EDIT_SUCCESS, UpdateNicknameResponseDto.toDto(toUpdate));
    }

    @Loggable
    @PreAuthorize("#userId == principal.userId")
    public BaseResponse changePassword(long userId, PasswordChangeRequestDto req) {
        String password = req.getPassword();

        User toUpdate = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        toUpdate.updatePassword(passwordEncoder.encode(password));
        toUpdate.updateNowTime();

        return new BaseResponse(ResponseMessage.PASSWORD_CHANGE_SUCCESS, UpdatePasswordResponseDto.toDto(toUpdate.getId()));
    }

}
