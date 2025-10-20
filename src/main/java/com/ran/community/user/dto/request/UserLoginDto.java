package com.ran.community.user.dto.request;


import jakarta.validation.constraints.Pattern;

public class UserLoginDto {

    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$",
            message = "올바른 이메일 주소 형식을 입력해주세요."
    )
    private String email;

    @Pattern( //정규식
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$",
            message = "비밀번호는 8~20자이며, 대문자/소문자/숫자/특수문자를 모두 포함해야 합니다."
    )
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
