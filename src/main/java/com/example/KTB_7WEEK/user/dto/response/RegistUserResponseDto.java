package com.example.KTB_7WEEK.user.dto.response;


import com.example.KTB_7WEEK.user.entity.User;
import lombok.Getter;

@Getter
public class RegistUserResponseDto {
    private long id;

    public RegistUserResponseDto() {

    }

    public static RegistUserResponseDto toDto(User user) {
        RegistUserResponseDto dto = new RegistUserResponseDto();
        dto.id = user.getId();
        return dto;
    }

}
