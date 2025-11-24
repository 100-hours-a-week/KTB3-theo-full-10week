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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ProfileImageStorage profileImageStorage;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    // 생성자 DI
    public UserService(UserRepository userRepository, ProfileImageStorage profileImageStorage) {
        this.userRepository = userRepository;
        this.profileImageStorage = profileImageStorage;
    }

    /**
     * User Service Business Logic & Convert <BaseResponse> Method
     **/
    // 회원가입
    @Loggable
    public BaseResponse<RegistUserResponseDto> register(RegistUserRequestDto req) {
        String email = req.getEmail();
        String nickname = req.getNickname();
        String profileImageUrl = profileImageStorage.saveProfileImage(req.getProfileImage());

        if (userRepository.existsByEmail(email)) throw new EmailAlreadyRegisteredException(); // 이메일 중복 검사
        if (userRepository.existsByNickname(nickname)) throw new NicknameAlreadyRegisteredException(); // 닉네임 중복 검사

        User toSave = new User.Builder() // User 생성
                .email(email)
                .password(bCryptPasswordEncoder.encode(req.getPassword()))
                .nickname(req.getNickname())
                .profileImage(profileImageUrl)
                .build();

        User saved = userRepository.save(toSave); // DB save User
        return new BaseResponse(ResponseMessage.USER_REGISTER_SUCCESS, RegistUserResponseDto.toDto(saved));

    }

    // 회원정보 조회
    @Loggable
    public BaseResponse<FindUserResponseDto> findById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        return new BaseResponse(ResponseMessage.USERINFO_LOAD_SUCCESS, FindUserResponseDto.toDto(user));
    }

    // 회원정보 삭제
    @Loggable
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

    // 닉네임 중복 검사
    @Loggable
    public BaseResponse<CheckNicknameAvailabilityResponseDto> doubleCheckNickname(CheckNicknameAvailabilityRequestDto req) {
        String nickname = req.getNickname();
        if (userRepository.existsByNickname(nickname)) { // if 닉네임 is unique
            return new BaseResponse(ResponseMessage.NICKNAME_IS_NOT_AVAILABLE,
                    CheckNicknameAvailabilityResponseDto.toDto(nickname, false)); // Nickname is Not Available
        }
        return new BaseResponse(ResponseMessage.NICKNAME_IS_AVAILABLE,
                CheckNicknameAvailabilityResponseDto.toDto(nickname, true)); // Nickname is Available
    }

    // 이메일 중복 검사
    @Loggable
    public BaseResponse<CheckEmailAvailabilityResponseDto> doubleCheckEmail(CheckEmailAvailabilityRequestDto req) {
        String email = req.getEmail();
        if (userRepository.existsByEmail(email)) { // if 이메일 is unique
            return new BaseResponse(ResponseMessage.EMAIL_IS_NOT_AVAILABLE,
                    CheckEmailAvailabilityResponseDto.toDto(email, false)); // Email Not Available
        }
        return new BaseResponse(ResponseMessage.EMAIL_IS_AVAILABLE,
                CheckEmailAvailabilityResponseDto.toDto(email, true)); // Email is Available
    }

    // 유저 프로필 수정(닉네임, 프로필 이미지)
    @Loggable
    public BaseResponse<EditProfileResponseDto> editProfile(Long userId, EditProfileRequestDto req) {
        User toUpdate = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        String newNickname = req.getNickname();
        String oldFileName = req.getOldFileName();
        String newProfileImageUrl = profileImageStorage.updateProfileImage(req.getProfileImage(), oldFileName);

        if (!toUpdate.getNickname().equals(newNickname)) { // 닉네임 수정
            toUpdate.updateNickname(newNickname);
        }
        toUpdate.updateProfileImage(newProfileImageUrl); // 프로필 이미지 수정
        toUpdate.updateNowTime(); // 업데이트 시간 최산화

        return new BaseResponse(ResponseMessage.EDIT_PROFILE_SUCCESS, EditProfileResponseDto.toDto(toUpdate));
    }

    // 닉네임 수정
    @Loggable
    public BaseResponse<UpdateNicknameResponseDto> editNickname(long userId, NicknameEditRequestDto req) {
        String nickname = req.getNickname();

        if (userRepository.existsByNickname(nickname)) throw new NicknameAlreadyRegisteredException();

        User toUpdate = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        // 변경 감지
        toUpdate.updateNickname(nickname); // 닉네임 수정
        toUpdate.updateNowTime(); // 업데이트 시간 최신화

        return new BaseResponse(ResponseMessage.NICKNAME_EDIT_SUCCESS, UpdateNicknameResponseDto.toDto(toUpdate));
    }

    // 비밀번호 변경
    @Loggable
    public BaseResponse changePassword(long userId, PasswordChangeRequestDto req) {
        String password = req.getPassword();

        User toUpdate = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        // 변경감지
        toUpdate.updatePassword(password); // 비밀번호 수정
        toUpdate.updateNowTime(); // 업데이트 시간 최신화

        return new BaseResponse(ResponseMessage.PASSWORD_CHANGE_SUCCESS, UpdatePasswordResponseDto.toDto(toUpdate.getId()));
    }

}
