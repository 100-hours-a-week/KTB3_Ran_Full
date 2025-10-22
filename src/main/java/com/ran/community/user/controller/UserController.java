package com.ran.community.user.controller;

import com.ran.community.global.ApiResponse;
import com.ran.community.user.entity.User;
import com.ran.community.user.dto.request.UserLoginDto;
import com.ran.community.user.dto.request.UserSignupFormDto;
import com.ran.community.user.dto.response.UserDataResponseDTO;
import com.ran.community.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //DI
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    //회원 가입 //✅
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignupFormDto userSignupFormDto) {
        userService.registerUser(userSignupFormDto);
        return ApiResponse.created(userSignupFormDto);
    }

    //로그인 //✅
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto, HttpSession httpSession){
        UserDataResponseDTO userDataResponseDTO = userService.login(userLoginDto);
        //로그인 성공 시 세션에 유저 정보 저장해야됨!!
        httpSession.setAttribute("userId", userDataResponseDTO.getUserId());
        return ApiResponse.success(userLoginDto);
    }

    //회원 정보 조회 //✅
    @GetMapping("/{userId}")
    public ResponseEntity<?> userInfo(@PathVariable Long userId){
        UserDataResponseDTO userDataResponseDTO = userService.getUserData(userId);
        return ApiResponse.success(userDataResponseDTO);
    }

    //회원 정보 수정 //✅
    @PatchMapping("/{userId}")
    public ResponseEntity<?> userPatchInfo(@Valid @PathVariable Long userId,@RequestBody UserSignupFormDto userSignupFormDto){
        userService.updateUser(userId,userSignupFormDto);
        return ApiResponse.success(userSignupFormDto);
    }

    //회원 탈퇴 //✅
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> userDelete(@PathVariable Long userId){
        userService.deletedUser(userId);
        return ApiResponse.success(userId);
    }

}
