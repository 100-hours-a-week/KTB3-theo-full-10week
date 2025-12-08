package com.example.KTB_10WEEK.user.controller;

import com.example.KTB_10WEEK.swagger.controller.user.UserApiDoc;
import com.example.KTB_10WEEK.app.response.BaseResponse;
import com.example.KTB_10WEEK.user.dto.response.*;
import com.example.KTB_10WEEK.user.service.UserService;
import com.example.KTB_10WEEK.user.dto.request.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class PublicUserController implements UserApiDoc {

    private final UserService publicUserService;

    public PublicUserController(UserService publicUserService) {
        this.publicUserService = publicUserService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse> findByPublicUserId(@PathVariable("userId")
                                                           @NotNull
                                                           @Positive Long userId) {
        BaseResponse<FindUserResponseDto> response = publicUserService.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse> createPublicUser(@Valid
                                                         @ModelAttribute RegistUserRequestDto request) {
        BaseResponse<RegistUserResponseDto> response = publicUserService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/nickname/double-check")
    public ResponseEntity<BaseResponse> doubleCheckNickname(@RequestBody
                                                            @Valid CheckNicknameAvailabilityRequestDto request) {
        BaseResponse<CheckNicknameAvailabilityResponseDto> response = publicUserService.doubleCheckNickname(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/email/double-check")
    public ResponseEntity<BaseResponse> doubleCheckEmail(@RequestBody
                                                         @Valid CheckEmailAvailabilityRequestDto request) {
        BaseResponse<CheckEmailAvailabilityResponseDto> response = publicUserService.doubleCheckEmail(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse> editProfile(@PathVariable("userId")
                                                 @NotNull
                                                 @Positive Long userId,
                                                 @Valid
                                                 @ModelAttribute EditProfileRequestDto request) {
        BaseResponse<EditProfileResponseDto> response = publicUserService.editProfile(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<BaseResponse> changePassword(@PathVariable("userId")
                                                       @NotNull
                                                       @Positive Long userId,
                                                       @RequestBody
                                                       @Valid UpdatePasswordRequestDto request) {
        BaseResponse<UpdatePasswordResponseDto> response = publicUserService.changePassword(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{userId}/nickname")
    public ResponseEntity<BaseResponse> editNickName(@PathVariable("userId")
                                                     @NotNull
                                                     @Positive Long userId,
                                                     @RequestBody
                                                     @Valid UpdateNicknameRequestDto request) {
        BaseResponse response = publicUserService.editNickname(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<BaseResponse> deleteUserById(@PathVariable("userId")
                                                         @NotNull
                                                         @Positive Long userId) {
        BaseResponse response = publicUserService.deleteById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

}