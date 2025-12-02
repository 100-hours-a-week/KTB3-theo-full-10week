package com.example.KTB_10WEEK.user.dto.response;


import lombok.Getter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckEmailAvailabilityResponseDto that = (CheckEmailAvailabilityResponseDto) o;
        return isAvailable == that.isAvailable && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, isAvailable);
    }
}
