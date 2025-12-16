package com.todatseafood.api.user.service;

import com.todayseafood.api.app.response.BaseResponse;
import com.todayseafood.api.app.response.ResponseMessage;
import com.todayseafood.api.app.storage.ProfileImageStorage;
import com.todayseafood.api.user.dto.request.*;
import com.todayseafood.api.user.dto.response.*;
import com.todayseafood.api.user.entity.Role;
import com.todayseafood.api.user.entity.User;
import com.todayseafood.api.user.exception.DeleteProfileImageFailException;
import com.todayseafood.api.user.exception.EmailAlreadyRegisteredException;
import com.todayseafood.api.user.exception.NicknameAlreadyRegisteredException;
import com.todayseafood.api.user.exception.UserNotFoundException;
import com.todatseafood.api.user.fixture.UserFixture;
import com.todayseafood.api.user.repository.UserRepository;
import com.todayseafood.api.user.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ProfileImageStorage profileImageStorage;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserService userService;


    @Nested
    class Register {
        @Test
        void register_성공() {
            // given
            String email = "test@test.com";
            String rawPassword = "1q2w3e4r!Q";
            String encodedPassword = "encoded-password";
            String nickname = "닉네임";
            String storedProfileImage = "profileImage.png";

            RegistUserRequestDto req = mock(RegistUserRequestDto.class);
            MultipartFile multipartFile = mock(MultipartFile.class);

            when(req.getEmail()).thenReturn(email);
            when(req.getNickname()).thenReturn(nickname);
            when(req.getPassword()).thenReturn(rawPassword);
            when(req.getProfileImage()).thenReturn(multipartFile);

            when(userRepository.existsByEmail(email)).thenReturn(false);
            when(userRepository.existsByNickname(nickname)).thenReturn(false);
            when(profileImageStorage.saveProfileImage(multipartFile)).thenReturn(storedProfileImage);
            when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

            // save()로 넘어가는 User를 캡처
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

            // 레포지토리가 리턴해 줄 "저장된 유저"
            User savedUser = UserFixture.createUserWithId(1L); // 여기 값은 DTO 검증용으로만 사용
            when(userRepository.save(userCaptor.capture())).thenReturn(savedUser);

            // when
            BaseResponse<RegistUserResponseDto> res = userService.register(req);

            // then
            // 1) 실제 DB에 저장되려고 했던 User 값 검증
            User toSave = userCaptor.getValue();
            assertEquals(email, toSave.getEmail());
            assertEquals(encodedPassword, toSave.getPassword());
            assertEquals(nickname, toSave.getNickname());
            assertEquals(storedProfileImage, toSave.getProfileImage());
            assertEquals(Role.USER, toSave.getRole());

            // 2) 응답 DTO 검증
            assertEquals(ResponseMessage.USER_REGISTER_SUCCESS.getMessage(), res.getMessage());
            assertNotNull(res.getData());

            RegistUserResponseDto dto = res.getData();
            assertEquals(savedUser.getId(), dto.getId());

        }

        @Test
        void register_호출_시_중복된_이메일은_EmailAlreadyRegisterException을_반환한다() {
            // given
            String duplicatedEmail = "중복된 이메일";
            String nickname = "닉네임";
            MultipartFile newProfileImage = mock(MultipartFile.class);
            String newProfileImageName = "새로운 프로필 이미지 이름";

            RegistUserRequestDto req = mock(RegistUserRequestDto.class);
            when(req.getEmail()).thenReturn(duplicatedEmail);
            when(req.getNickname()).thenReturn(nickname);
            when(req.getProfileImage()).thenReturn(newProfileImage);

            // when
            when(profileImageStorage.saveProfileImage(newProfileImage)).thenReturn(newProfileImageName);
            when(userRepository.existsByEmail("중복된 이메일")).thenReturn(true);

            // then
            assertThrows(EmailAlreadyRegisteredException.class, () -> {
                userService.register(req);
            });

            verify(userRepository, times(1)).existsByEmail(duplicatedEmail);
            verify(userRepository, never()).existsByNickname(nickname);
            verify(passwordEncoder, never()).encode(anyString());
            verify(userRepository, never()).save(any(User.class));
        }

        @Test
        void register_호출_시_중복된_닉네임은_NicknameAlreadyRegisteredException을_반환한다() {
            // given
            String email = "이메일";
            String duplicatedNickname = "중복된 닉네임";
            MultipartFile profileImage = mock(MultipartFile.class);

            RegistUserRequestDto req = mock(RegistUserRequestDto.class);
            when(req.getEmail()).thenReturn(email);
            when(req.getNickname()).thenReturn(duplicatedNickname);
            when(req.getProfileImage()).thenReturn(profileImage);

            // when
            when(userRepository.existsByEmail(email)).thenReturn(false);
            when(userRepository.existsByNickname(duplicatedNickname)).thenReturn(true);

            // then
            assertThrows(NicknameAlreadyRegisteredException.class, () -> {
                userService.register(req);
            });

            verify(userRepository, times(1)).existsByEmail(email);
            verify(userRepository, times(1)).existsByNickname(duplicatedNickname);
            verify(passwordEncoder, never()).encode(anyString());
            verify(userRepository, never()).save(any(User.class));
        }
    }

    @Nested
    class FindById {
        @Test
        void findById_성공() {
            // given
            User user = UserFixture.createUserWithId(1L);
            Long userId = user.getId();

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));

            // when
            BaseResponse<FindUserResponseDto> res = userService.findById(userId);

            // then
            assertEquals(ResponseMessage.USERINFO_LOAD_SUCCESS.getMessage(), res.getMessage());
            assertEquals(FindUserResponseDto.toDto(user), res.getData());
        }

        @Test
        void 존재하지_않는_userId_입력_시_findById는_UserNotFoundException을_반환한다() {
            // given
            Long noUserId = 0L;
            when(userRepository.findById(noUserId)).thenReturn(Optional.empty());

            // when & then
            assertThrows(UserNotFoundException.class, () -> {
                userService.findById(noUserId);
            });
        }
    }

    @Nested
    class DeleteById {
        @Test
        void deleteById_성공() {
            // given
            User user = UserFixture.createUserWithId(1L);
            Long userId = user.getId();

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(profileImageStorage.deleteProfileImage(user.getProfileImage())).thenReturn(true);

            // when
            BaseResponse res = userService.deleteById(userId);

            // then
            verify(userRepository, times(1)).deleteById(userId);

            assertEquals(ResponseMessage.USER_DELETE_SUCCESS.getMessage(), res.getMessage());
        }

        @Test
        void 존재하지_않는_userId_입력_시_deleteById는_UserNotFoundException을_반환한다() {
            // given
            User user = UserFixture.createUserWithId(1L);
            Long userId = user.getId();

            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            // when & then
            assertThrows(UserNotFoundException.class, () -> {
                userService.deleteById(userId);
            });
        }

        @Test
        void deleteById_유저_프로필_이미지_삭제_실패시_DeleteProfileImageFailException을_반환하고_유저는_삭제되지_않는다() {
            // given
            User user = UserFixture.createUserWithId(1L);
            Long userId = user.getId();

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(profileImageStorage.deleteProfileImage(user.getProfileImage())).thenReturn(false);

            // when & then
            assertThrows(DeleteProfileImageFailException.class, () -> {
                userService.deleteById(userId);
            });
            verify(userRepository, never()).deleteById(userId);
        }
    }

    @Nested
    class DoubleCheckNickname {
        @Test
        void 닉네임이_중복인_경우_doubleCheckNickname은_CheckNicknameAvailablityResponseDto의_isAvailable필드가_false반환() {
            // given
            CheckNicknameAvailabilityRequestDto req = mock(CheckNicknameAvailabilityRequestDto.class);
            when(req.getNickname()).thenReturn("존재하는 닉네임");

            when(userRepository.existsByNickname("존재하는 닉네임")).thenReturn(true);

            // when
            BaseResponse<CheckNicknameAvailabilityResponseDto> res = userService.doubleCheckNickname(req);

            // then
            assertEquals(ResponseMessage.NICKNAME_IS_NOT_AVAILABLE.getMessage(), res.getMessage());
            assertNotNull(res.getData());
            assertFalse(res.getData().isAvailable());
        }

        @Test
        void 닉네임이_중복이_아닌_경우_doubleCheckNickname은_CheckNicknameAvailablityResponseDto의_isAvailable필드가_true반환() {
            // given
            CheckNicknameAvailabilityRequestDto req = mock(CheckNicknameAvailabilityRequestDto.class);
            when(req.getNickname()).thenReturn("중복되지 않은 닉네임");

            when(userRepository.existsByNickname("중복되지 않은 닉네임")).thenReturn(false);

            // when
            BaseResponse<CheckNicknameAvailabilityResponseDto> res = userService.doubleCheckNickname(req);

            // then
            assertEquals(ResponseMessage.NICKNAME_IS_AVAILABLE.getMessage(), res.getMessage());
            assertNotNull(res.getData());
            assertTrue(res.getData().isAvailable());
        }
    }

    @Nested
    class DoubleCheckEmail {
        @Test
        void 이메일이_중복이_아닌_경우_doubleCheckEmail은_CheckEmailAvailabilityResponseDto의_isAvailable필드가_true반환() {
            // given
            CheckEmailAvailabilityRequestDto req = mock(CheckEmailAvailabilityRequestDto.class);
            when(req.getEmail()).thenReturn("중복되지 않은 이메일");

            when(userRepository.existsByEmail("중복되지 않은 이메일")).thenReturn(false);

            // when
            BaseResponse<CheckEmailAvailabilityResponseDto> res = userService.doubleCheckEmail(req);

            // then
            assertEquals(ResponseMessage.EMAIL_IS_AVAILABLE.getMessage(), res.getMessage());
            assertNotNull(res.getData());
            assertTrue(res.getData().isAvailable());
        }

        @Test
        void 이메일이_중복인_경우_doubleCheckEmail은_CheckEmailAvailabilityResponseDto의_isAvailable필드가_false반환() {
            // given
            CheckEmailAvailabilityRequestDto req = mock(CheckEmailAvailabilityRequestDto.class);
            when(req.getEmail()).thenReturn("중복된 이메일");

            when(userRepository.existsByEmail("중복된 이메일")).thenReturn(true);

            // when
            BaseResponse<CheckEmailAvailabilityResponseDto> res = userService.doubleCheckEmail(req);

            //then
            assertEquals(ResponseMessage.EMAIL_IS_NOT_AVAILABLE.getMessage(), res.getMessage());
            assertNotNull(res.getData());
            assertFalse(res.getData().isAvailable());
        }
    }

    @Nested
    class EditProfile {
        @Test
        void editProfile_성공() {
            User toUpdate = UserFixture.createUserWithId(1L);
            Long userId = toUpdate.getId();

            MultipartFile newProfileImage = mock(MultipartFile.class);

            EditProfileRequestDto req = mock(EditProfileRequestDto.class);
            when(req.getNickname()).thenReturn("새로운 닉네임");
            when(req.getProfileImage()).thenReturn(newProfileImage);
            when(req.getOldFileName()).thenReturn("이전 이미지 파일 이름");

            when(userRepository.findById(userId)).thenReturn(Optional.of(toUpdate));
            when(profileImageStorage.updateProfileImage(eq(newProfileImage), eq("이전 이미지 파일 이름"))).thenReturn("새로운 프로필 이미지가 저장된 경로");

            BaseResponse<EditProfileResponseDto> res = userService.editProfile(userId, req);

            assertEquals(ResponseMessage.EDIT_PROFILE_SUCCESS.getMessage(), res.getMessage());
            assertNotNull(res.getData());
            assertEquals("새로운 닉네임", res.getData().getNickname());
            assertEquals("새로운 프로필 이미지가 저장된 경로", res.getData().getProfileImage());

            assertEquals("새로운 닉네임", toUpdate.getNickname());
            assertEquals("새로운 프로필 이미지가 저장된 경로", toUpdate.getProfileImage());

            verify(userRepository, times(1)).findById(userId);
            verify(profileImageStorage, times(1)).updateProfileImage(newProfileImage, "이전 이미지 파일 이름");
        }

        @Test
        void editProfile_프로필_이미지_null이고__닉네임만_변경() {
            User toUpdate = UserFixture.createUserWithId(1L);
            Long userId = toUpdate.getId();
            String oldProfileImage = toUpdate.getProfileImage();

            EditProfileRequestDto req = mock(EditProfileRequestDto.class);
            when(req.getNickname()).thenReturn("새로운 닉네임");
            when(req.getProfileImage()).thenReturn(null);

            when(userRepository.findById(userId)).thenReturn(Optional.of(toUpdate));

            BaseResponse<EditProfileResponseDto> res = userService.editProfile(userId, req);

            assertEquals(ResponseMessage.EDIT_PROFILE_SUCCESS.getMessage(), res.getMessage());
            assertNotNull(res.getData());
            assertEquals("새로운 닉네임", res.getData().getNickname());
            assertEquals(oldProfileImage, res.getData().getProfileImage());

            verify(userRepository, times(1)).findById(userId);
            verify(profileImageStorage, never()).updateProfileImage(any(), anyString());
        }

        @Test
        void editProfile_프로필_이미지_empty이고_닉네임만_변경() {
            User toUpdate = UserFixture.createUserWithId(1L);
            Long userId = toUpdate.getId();
            String oldProfileImage = toUpdate.getProfileImage();

            MultipartFile emptyProfileImage = mock(MultipartFile.class);
            when(emptyProfileImage.isEmpty()).thenReturn(true);

            EditProfileRequestDto req = mock(EditProfileRequestDto.class);
            when(req.getNickname()).thenReturn("새로운 닉네임");
            when(req.getProfileImage()).thenReturn(emptyProfileImage);

            when(userRepository.findById(userId)).thenReturn(Optional.of(toUpdate));

            BaseResponse<EditProfileResponseDto> res = userService.editProfile(userId, req);

            assertEquals(ResponseMessage.EDIT_PROFILE_SUCCESS.getMessage(), res.getMessage());
            assertNotNull(res.getData());
            assertEquals("새로운 닉네임", res.getData().getNickname());
            assertEquals(oldProfileImage, res.getData().getProfileImage());

            verify(userRepository, times(1)).findById(userId);
            verify(profileImageStorage, never()).updateProfileImage(any(), anyString());
        }

        @Test
        void editProfile_닉네임_변경없이_프로필_이미지만_변경() {
            User toUpdate = UserFixture.createUserWithId(1L);
            String oldNickname = toUpdate.getNickname();
            Long userId = toUpdate.getId();

            MultipartFile newProfileImage = mock(MultipartFile.class);

            EditProfileRequestDto req = mock(EditProfileRequestDto.class);
            when(req.getNickname()).thenReturn(oldNickname);
            when(req.getProfileImage()).thenReturn(newProfileImage);
            when(req.getOldFileName()).thenReturn("이전 이미지 파일 이름");

            when(userRepository.findById(userId)).thenReturn(Optional.of(toUpdate));
            when(profileImageStorage.updateProfileImage(eq(newProfileImage), eq("이전 이미지 파일 이름"))).thenReturn("새로운 프로필 이미지가 저장된 경로");

            BaseResponse<EditProfileResponseDto> res = userService.editProfile(userId, req);

            assertEquals(ResponseMessage.EDIT_PROFILE_SUCCESS.getMessage(), res.getMessage());
            assertNotNull(res.getData());
            assertEquals(oldNickname, res.getData().getNickname());
            assertEquals("새로운 프로필 이미지가 저장된 경로", res.getData().getProfileImage());

            assertEquals(oldNickname, toUpdate.getNickname());
            assertEquals("새로운 프로필 이미지가 저장된 경로", toUpdate.getProfileImage());

            verify(userRepository, times(1)).findById(userId);
            verify(profileImageStorage, times(1)).updateProfileImage(newProfileImage, "이전 이미지 파일 이름");
        }

        @Test
        void editProfile_존재하지_않는_유저PK_입력시_UserNotFoundException반환() {
            Long notExistUserid = 0L;

            EditProfileRequestDto req = mock(EditProfileRequestDto.class);

            when(userRepository.findById(notExistUserid)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> {
                userService.editProfile(notExistUserid, req);
            });
            verify(profileImageStorage, never()).updateProfileImage(any(), anyString());
        }
    }

    @Nested
    class EditNickname {
        @Test
        void editNickname_성공() {
            // given
            User toUpdate = UserFixture.createUserWithId(1L);
            Long userId = toUpdate.getId();
            String newNickname = "새로운 닉네임";

            UpdateNicknameRequestDto req = mock(UpdateNicknameRequestDto.class);
            when(req.getNickname()).thenReturn(newNickname);

            when(userRepository.existsByNickname(newNickname)).thenReturn(false);
            when(userRepository.findById(userId)).thenReturn(Optional.of(toUpdate));

            // when
            BaseResponse<UpdateNicknameResponseDto> res = userService.editNickname(userId, req);

            // then
            assertEquals(ResponseMessage.NICKNAME_EDIT_SUCCESS.getMessage(), res.getMessage());
            assertNotNull(res.getData());
            assertEquals(newNickname, res.getData().getNickname());
            assertEquals(newNickname, toUpdate.getNickname());
        }

        @Test
        void editNickname_닉네임이_사용중이면_NicknameAlreadyRegisteredException반환() {
            // given
            User user = UserFixture.createUserWithId(1L);
            Long userId = user.getId();
            String newNickname = "새로운 닉네임";

            UpdateNicknameRequestDto req = mock(UpdateNicknameRequestDto.class);
            when(req.getNickname()).thenReturn(newNickname);

            when(userRepository.existsByNickname(newNickname)).thenReturn(true);

            // when & then
            assertThrows(NicknameAlreadyRegisteredException.class, () -> {
                userService.editNickname(userId, req);
            });

            verify(userRepository, times(1)).existsByNickname(newNickname);
            verify(userRepository, never()).findById(anyLong());
        }

        @Test
        void editNickname_존재하지_않는_유저PK_입력시_UserNotFoundException반환() {
            // given
            Long notExistUserId = 0L;
            String newNickname = "새로운 닉네임";

            UpdateNicknameRequestDto req = mock(UpdateNicknameRequestDto.class);
            when(req.getNickname()).thenReturn(newNickname);

            when(userRepository.existsByNickname(newNickname)).thenReturn(false);
            when(userRepository.findById(notExistUserId)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> {
                userService.editNickname(notExistUserId, req);
            });

            verify(userRepository, times(1)).existsByNickname(newNickname);
            verify(userRepository, times(1)).findById(notExistUserId);
        }
    }

    @Nested
    class ChangePassword {
        @Test
        void changePassword_성공() {
            User savedUser = UserFixture.createUserWithId(1L);
            Long userId = savedUser.getId();
            String newPassword = "새로운 비밀번호";
            String encryptedNewPassword = "암호화된 새로운 비밀번호";

            UpdatePasswordRequestDto req = mock(UpdatePasswordRequestDto.class);
            when(req.getPassword()).thenReturn(newPassword);

            when(userRepository.findById(userId)).thenReturn(Optional.of(savedUser));
            when(passwordEncoder.encode(newPassword)).thenReturn(encryptedNewPassword);

            // when
            BaseResponse<UpdatePasswordResponseDto> res = userService.changePassword(userId, req);

            // then
            assertEquals(ResponseMessage.PASSWORD_CHANGE_SUCCESS.getMessage(), res.getMessage());
            assertNotNull(res.getData());
            assertEquals("암호화된 새로운 비밀번호", savedUser.getPassword());
            assertEquals(userId, res.getData().getId());

            verify(userRepository, times(1)).findById(userId);
            verify(passwordEncoder, times(1)).encode(newPassword);
        }

        @Test
        void changePassword_존재하지_않는_유저PK면_UserNotFoundException반환() {
            Long notExistId = 0L;
            String newPassword = "새로운 비밀번호";

            UpdatePasswordRequestDto req = mock(UpdatePasswordRequestDto.class);
            when(req.getPassword()).thenReturn(newPassword);

            when(userRepository.findById(notExistId)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> {
                userService.changePassword(notExistId, req);
            });

            verify(userRepository, times(1)).findById(notExistId);
            verify(passwordEncoder, never()).encode(newPassword);
        }
    }
}
