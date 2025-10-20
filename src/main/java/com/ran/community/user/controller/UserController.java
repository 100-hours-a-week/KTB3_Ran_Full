package com.ran.community.user.controller;

import com.ran.community.user.dto.UserDto;
import com.ran.community.user.dto.UserLoginDto;
import com.ran.community.user.dto.UserSignupFormDto;
import com.ran.community.user.dto.response.UserSignupFormResponseDTO;
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

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignupFormDto userSignupFormDto) {
        UserSignupFormResponseDTO userSignupFormResponseDTO = userService.registerUser(userSignupFormDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","signup_success","userResponse", userSignupFormResponseDTO));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto, HttpSession httpSession){
        UserSignupFormResponseDTO userSignupFormResponseDTO = userService.login(userLoginDto);
        //로그인 성공 시 세션에 유저 정보 저장해야됨!!
        httpSession.setAttribute("userId", userSignupFormResponseDTO.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","login_success","userResponse", userSignupFormResponseDTO));
    }

    //회원 정보 조회
    @GetMapping("/{userId}")
    public ResponseEntity<?> userInfo(@PathVariable Long userId){
        UserDto userDto = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","user_confirm","data",Map.of("userId",userDto.getUserId(),"username",userDto.getUsername(),"email",userDto.getEmail(),"password",userDto.getPassword())));
    }

    //회원 정보 수정
    @PatchMapping("/{userId}")
    public ResponseEntity<?> userPatchInfo(@Valid @PathVariable Long userId,@RequestBody UserSignupFormDto userSignupFormDto){
        UserDto userDto = userService.updateUser(userId,userSignupFormDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Patch_user","data",Map.of("userId",userDto.getUserId(),"username",userDto.getUsername(),"email",userDto.getEmail(),"password",userDto.getPassword())));
    }

    //회원 탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> userDelete(@PathVariable Long userId){
        UserDto userDto = userService.deletedUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","user_delete","data",Map.of("userId",userDto.getUserId(),"username",userDto.getUsername(),"email",userDto.getEmail(),"password",userDto.getPassword())));
    }

}
