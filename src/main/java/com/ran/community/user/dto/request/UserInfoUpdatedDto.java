package com.ran.community.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserInfoUpdatedDto {
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String username;
}
