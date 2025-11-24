package com.example.KTB_10WEEK.user.dto.response;


import lombok.Getter;

@Getter
public class CheckEmailAvailabilityResponseDto {
    private String email;
    private boolean isAvailable;

    public CheckEmailAvailabilityResponseDto() {

    }
    public static CheckEmailAvailabilityResponseDto toDto(String email, boolean isAvailable) {
        CheckEmailAvailabilityResponseDto dto = new CheckEmailAvailabilityResponseDto();
        dto.email = email;
        dto.isAvailable = isAvailable;
        return dto;
    }
}
