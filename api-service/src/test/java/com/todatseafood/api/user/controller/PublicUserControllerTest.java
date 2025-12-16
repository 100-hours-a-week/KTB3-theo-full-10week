package com.todatseafood.api.user.controller;

import com.todayseafood.api.app.response.BaseResponse;
import com.todayseafood.api.app.response.ResponseMessage;
import com.todayseafood.api.user.controller.PublicUserController;
import com.todayseafood.api.user.dto.request.*;
import com.todayseafood.api.user.dto.response.*;
import com.todayseafood.api.user.entity.User;
import com.todayseafood.api.user.exception.DeleteProfileImageFailException;
import com.todayseafood.api.user.exception.NicknameAlreadyRegisteredException;
import com.todayseafood.api.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicUserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    PublicUserController userController;

    @Nested
    @DisplayName("GET /user/{userId}")
    class findByPublicUserId {
        @Test
        void findByPublicUserId_성공() {
            // given
            Long userId = 1L;
            FindUserResponseDto responseData = mock(FindUserResponseDto.class);

            BaseResponse<FindUserResponseDto> serviceResponse = new BaseResponse(ResponseMessage.USERINFO_LOAD_SUCCESS, responseData);

            when(userService.findById(userId)).thenReturn(serviceResponse);

            // when
            ResponseEntity<BaseResponse> httpResponse = userController.findByPublicUserId(userId);

            // then
            assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
            assertEquals(ResponseMessage.USERINFO_LOAD_SUCCESS.getMessage(), httpResponse.getBody().getMessage());
            assertSame(serviceResponse, httpResponse.getBody());

            verify(userService, times(1)).findById(userId);
        }
    }

    @Nested
    @DisplayName("POST /user")
    class CreatePublicUser {

        @Test
        void createPublicUser_성공() {
            // given
            RegistUserRequestDto request = mock(RegistUserRequestDto.class);

            RegistUserResponseDto response = mock(RegistUserResponseDto.class);
            BaseResponse<RegistUserResponseDto> serviceResponse = new BaseResponse<>(ResponseMessage.USER_REGISTER_SUCCESS, response);
            when(userService.register(request)).thenReturn(serviceResponse);

            // when
            ResponseEntity<BaseResponse> httpResponse = userController.createPublicUser(request);

            // then
            assertEquals(HttpStatus.CREATED, httpResponse.getStatusCode());
            assertEquals(ResponseMessage.USER_REGISTER_SUCCESS.getMessage(), httpResponse.getBody().getMessage());
            assertSame(serviceResponse, httpResponse.getBody());

            verify(userService, times(1)).register(request);
        }
    }

    @Nested
    @DisplayName("POST /user/nickname/double-check")
    class DoubleCheckNickname {
        @Test
        void doubleCheckNickname_사용가능한_닉네임은_NICKNAME_IS_AVAILABLE메시지를_반환() {
            // given
            CheckNicknameAvailabilityRequestDto request = mock(CheckNicknameAvailabilityRequestDto.class);

            CheckNicknameAvailabilityResponseDto response = mock(CheckNicknameAvailabilityResponseDto.class);
            BaseResponse<CheckNicknameAvailabilityResponseDto> serviceResponse = new BaseResponse<>(ResponseMessage.NICKNAME_IS_AVAILABLE, response);
            when(userService.doubleCheckNickname(request)).thenReturn(serviceResponse);

            // when
            ResponseEntity<BaseResponse> httpResponse = userController.doubleCheckNickname(request);

            // then
            assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
            assertEquals(ResponseMessage.NICKNAME_IS_AVAILABLE.getMessage(), httpResponse.getBody().getMessage());
            assertSame(serviceResponse, httpResponse.getBody());

            verify(userService, times(1)).doubleCheckNickname(request);
        }

        @Test
        void doubleCheckNickname_사용가능한_닉네임은_NICKNAME_IS_NOT_AVAILABLE메시지를_반환() {
            // given
            CheckNicknameAvailabilityRequestDto request = mock(CheckNicknameAvailabilityRequestDto.class);

            CheckNicknameAvailabilityResponseDto response = mock(CheckNicknameAvailabilityResponseDto.class);
            BaseResponse<CheckNicknameAvailabilityResponseDto> serviceResponse = new BaseResponse<>(ResponseMessage.NICKNAME_IS_NOT_AVAILABLE, response);
            when(userService.doubleCheckNickname(request)).thenReturn(serviceResponse);

            // when
            ResponseEntity<BaseResponse> httpResponse = userController.doubleCheckNickname(request);

            // then
            assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
            assertEquals(ResponseMessage.NICKNAME_IS_NOT_AVAILABLE.getMessage(), httpResponse.getBody().getMessage());
            assertSame(serviceResponse, httpResponse.getBody());

            verify(userService, times(1)).doubleCheckNickname(request);
        }
    }

    @Nested
    @DisplayName("POST /user/email/double-check")
    class DoubleCheckEmail {
        @Test
        void doubleCheckEmail_사용가능한_이메일은_EMIL_IS_AVAILABLE메시지를_반환() {
            // given
            CheckEmailAvailabilityRequestDto request = mock(CheckEmailAvailabilityRequestDto.class);

            CheckEmailAvailabilityResponseDto response = mock(CheckEmailAvailabilityResponseDto.class);
            BaseResponse<CheckEmailAvailabilityResponseDto> serviceResponse = new BaseResponse<>(ResponseMessage.EMAIL_IS_AVAILABLE, response);
            when(userService.doubleCheckEmail(request)).thenReturn(serviceResponse);

            // when
            ResponseEntity<BaseResponse> httpResponse = userController.doubleCheckEmail(request);

            // then
            assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
            assertEquals(ResponseMessage.EMAIL_IS_AVAILABLE.getMessage(), httpResponse.getBody().getMessage());
            assertSame(serviceResponse, httpResponse.getBody());

            verify(userService, times(1)).doubleCheckEmail(request);
        }

        @Test
        void doubleCheckEmail_사용불가능한_이메일은_EMIL_IS_NOT_AVAILABLE메시지를_반환() {
            // given
            CheckEmailAvailabilityRequestDto request = mock(CheckEmailAvailabilityRequestDto.class);

            CheckEmailAvailabilityResponseDto response = mock(CheckEmailAvailabilityResponseDto.class);
            BaseResponse<CheckEmailAvailabilityResponseDto> serviceResponse = new BaseResponse<>(ResponseMessage.EMAIL_IS_NOT_AVAILABLE, response);
            when(userService.doubleCheckEmail(request)).thenReturn(serviceResponse);

            // when
            ResponseEntity<BaseResponse> httpResponse = userController.doubleCheckEmail(request);

            // then
            assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
            assertEquals(ResponseMessage.EMAIL_IS_NOT_AVAILABLE.getMessage(), httpResponse.getBody().getMessage());
            assertSame(serviceResponse, httpResponse.getBody());

            verify(userService, times(1)).doubleCheckEmail(request);
        }
    }

    @Nested
    @DisplayName("PATCH /user/{userId}")
    class EditProfile {
        @Test
        void editProfile_성공() {
            // given
            Long userId = 1L;
            EditProfileRequestDto request = mock(EditProfileRequestDto.class);

            EditProfileResponseDto response = mock(EditProfileResponseDto.class);
            BaseResponse<EditProfileResponseDto> serviceResponse = new BaseResponse<>(ResponseMessage.EDIT_PROFILE_SUCCESS, response);
            when(userService.editProfile(userId, request)).thenReturn(serviceResponse);

            // when
            ResponseEntity<BaseResponse> httpResponse = userController.editProfile(userId, request);

            // then
            assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
            assertEquals(ResponseMessage.EDIT_PROFILE_SUCCESS.getMessage(), httpResponse.getBody().getMessage());
            assertSame(serviceResponse, httpResponse.getBody());

            verify(userService, times(1)).editProfile(userId, request);
        }
    }

    @Nested
    @DisplayName("PATCH /user/{userId}/password")
    class ChangePassword {
        @Test
        void changePassword_성공() {
            // given
            Long userId = 1L;
            UpdatePasswordRequestDto request = mock(UpdatePasswordRequestDto.class);

            UpdatePasswordResponseDto response = mock(UpdatePasswordResponseDto.class);
            BaseResponse<UpdatePasswordResponseDto> serviceResponse = new BaseResponse<>(ResponseMessage.PASSWORD_CHANGE_SUCCESS, response);
            when(userService.changePassword(userId, request)).thenReturn(serviceResponse);

            // when
            ResponseEntity<BaseResponse> httpResponse = userController.changePassword(userId, request);

            // then
            assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
            assertEquals(ResponseMessage.PASSWORD_CHANGE_SUCCESS.getMessage(), httpResponse.getBody().getMessage());
            assertSame(serviceResponse, httpResponse.getBody());

            verify(userService, times(1)).changePassword(userId, request);
        }
    }

    @Nested
    @DisplayName("PATCH /user/{userId}/nickanem")
    class EditNickname {
        @Test
        void editNickname_성공() {
            // given
            Long userId = 1L;
            UpdateNicknameRequestDto request = mock(UpdateNicknameRequestDto.class);

            UpdateNicknameResponseDto response = mock(UpdateNicknameResponseDto.class);
            BaseResponse<UpdateNicknameResponseDto> serviceResponse = new BaseResponse<>(ResponseMessage.NICKNAME_EDIT_SUCCESS, response);
            when(userService.editNickname(userId, request)).thenReturn(serviceResponse);

            // when
            ResponseEntity<BaseResponse> httpResponse = userController.editNickName(userId, request);

            // then
            assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
            assertEquals(ResponseMessage.NICKNAME_EDIT_SUCCESS.getMessage(), httpResponse.getBody().getMessage());
            assertSame(serviceResponse, httpResponse.getBody());

            verify(userService, times(1)).editNickname(userId, request);
        }

        @Test
        void editNickname_닉네임_중복_시_NicknameAlreadyRegisteredException_전파() {
            // given
            Long userId = 1L;
            UpdateNicknameRequestDto request = mock(UpdateNicknameRequestDto.class);

            when(userService.editNickname(userId, request)).thenThrow(new NicknameAlreadyRegisteredException());

            // when && then
            assertThrows(NicknameAlreadyRegisteredException.class, () -> {
                userController.editNickName(userId, request);
            });

            verify(userService, times(1)).editNickname(userId, request);
        }
    }

    @Nested
    @DisplayName("DELETE /user/{userId}")
    class DeletePublicUser {
        @Test
        void deleteUserById_성공() {
            // given
            Long userId = 1L;

            User response = mock(User.class);
            BaseResponse serviceResponse = new BaseResponse<>(ResponseMessage.USER_DELETE_SUCCESS, response);
            when(userService.deleteById(userId)).thenReturn(serviceResponse);

            // when
            ResponseEntity<BaseResponse> httpResponse = userController.deleteUserById(userId);

            // then
            assertEquals(HttpStatus.NO_CONTENT, httpResponse.getStatusCode());
            assertEquals(ResponseMessage.USER_DELETE_SUCCESS.getMessage(), httpResponse.getBody().getMessage());
            assertSame(serviceResponse, httpResponse.getBody());

            verify(userService, times(1)).deleteById(userId);
        }

        @Test
        void deleteUserById_프로필이미지_삭제실패시_DeleteProfileImageFailExceptioin_전파() {
            // given
            Long usesId = 1L;
            when(userService.deleteById(usesId)).thenThrow(new DeleteProfileImageFailException());

            // when && then
            assertThrows(DeleteProfileImageFailException.class, () -> {
               userController.deleteUserById(usesId);
            });

            verify(userService, times(1)).deleteById(usesId);
        }
    }
}