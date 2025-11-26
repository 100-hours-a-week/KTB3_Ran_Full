package com.ran.community.user.controller;

import com.ran.community.global.ApiResponse;
import com.ran.community.security.LoginService;
import com.ran.community.security.TokenProvider;
import com.ran.community.user.dto.request.UserLoginDto;
import com.ran.community.user.dto.request.UserPWUpdateDto;
import com.ran.community.user.dto.request.UserSignupFormDto;
import com.ran.community.user.dto.request.UserInfoUpdatedDto;
import com.ran.community.user.dto.response.TokenResponse;
import com.ran.community.user.dto.response.UserDataResponseDTO;
import com.ran.community.user.entity.User;
import com.ran.community.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.antlr.v4.runtime.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private LoginService loginService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //DI
    @Autowired
    public UserController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }


    //회원 가입 //
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignupFormDto userSignupFormDto) {
        UserDataResponseDTO userDataResponseDTO = userService.registerUser(userSignupFormDto);
        return ApiResponse.created(userDataResponseDTO,"user_signup_success");
    }

    //로그인 //
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody UserLoginDto userLoginDto){
        TokenResponse response = loginService.login(userLoginDto);
        return ApiResponse.success(response,"login_success");
    }

    //회원 정보 조회 //
    @GetMapping()
    public ResponseEntity<?> userInfo(HttpSession session){
        long userId = (long) session.getAttribute("id");
        UserDataResponseDTO userDataResponseDTO = userService.getUserData(userId);
        return ApiResponse.success(userDataResponseDTO,"read_user");
    }

    //회원 정보 수정 //이메일, 닉네임 수정 페이지
    @PatchMapping("/userInfo")
    public ResponseEntity<?> userPatchInfo(HttpSession session,@RequestBody UserInfoUpdatedDto userInfoUpdatedDto){
        long userId = (long) session.getAttribute("id");
        UserDataResponseDTO userDataResponseDTO = userService.updateUser(userId, userInfoUpdatedDto);
        return ApiResponse.success(userDataResponseDTO,"user_update");
    }

    //회원 정보 수정 //비밀 번호
    @PatchMapping("/userPassword")
    public ResponseEntity<?> userPatchPassword(HttpSession session,@RequestBody UserPWUpdateDto userPWUpdateDto){
        long userId = (long) session.getAttribute("id");
        userService.updateUserPassword(userId, userPWUpdateDto);
        return ApiResponse.success("비밀번호가 변경되었습니다.","user_update");
    }

    //회원 탈퇴
    @DeleteMapping()
    public ResponseEntity<?> userDelete(HttpSession session){
        long userId = (long) session.getAttribute("id");
        UserDataResponseDTO userDataResponseDTO = userService.deletedUser(userId);
        return ApiResponse.success(userDataResponseDTO,"user_delete");
    }

}
