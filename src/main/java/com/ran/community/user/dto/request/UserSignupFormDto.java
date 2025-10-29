package com.ran.community.user.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserSignupFormDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$",
            message = "올바른 이메일 주소 형식을 입력해주세요."
    )
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern( //정규식
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$",
            message = "비밀번호는 8~20자이며, 대문자/소문자/숫자/특수문자를 모두 포함해야 합니다."
    )
    private String password;

    @NotBlank(message = "비밀번호를 다시 입력해주세요.")
    private String confirmPassword;

    public UserSignupFormDto(String email, String username, String password, String confirmPassword) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    //실제로 get밖에 사용하지 않음 .-UserResponse
    public String getEmail(){
        return email;
    }


    public String getUsername(){
        return username;
    }


    public String getPassword(){
        return password;
    }


    public String getConfirmPassword(){
        return confirmPassword;
    }


}
