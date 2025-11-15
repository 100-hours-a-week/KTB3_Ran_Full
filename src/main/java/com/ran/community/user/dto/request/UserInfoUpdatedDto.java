package com.ran.community.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserInfoUpdatedDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$",
            message = "올바른 이메일 주소 형식을 입력해주세요."
    )
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String username;
}
