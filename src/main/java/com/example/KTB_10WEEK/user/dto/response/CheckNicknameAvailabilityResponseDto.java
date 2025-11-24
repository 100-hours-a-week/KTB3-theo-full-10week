package com.example.KTB_10WEEK.user.dto.response;

import lombok.Getter;

@Getter
public class CheckNicknameAvailabilityResponseDto {
    private String nickname;
    private boolean isAvailable;

    public CheckNicknameAvailabilityResponseDto() {

    }
    public static CheckNicknameAvailabilityResponseDto toDto(String nickname, boolean isAvailable) {
        CheckNicknameAvailabilityResponseDto dto = new CheckNicknameAvailabilityResponseDto();
        dto.nickname = nickname;
        dto.isAvailable = isAvailable;
        return dto;
    }
}
