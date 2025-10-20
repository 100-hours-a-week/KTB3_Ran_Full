package com.ran.community.user.service;

import com.ran.community.user.dto.UserDto;
import com.ran.community.user.dto.UserLoginDto;
import com.ran.community.user.dto.UserSignupFormDto;
import com.ran.community.user.dto.response.UserSignupFormResponseDTO;
import com.ran.community.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //DI
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //식별자로 유저 찾기
    public UserDto getUser(long userId){
        //빈값이면 Exception
        return userRepository.getUser(userId).orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }

    //유저 생성
    public UserDto createUser(UserSignupFormDto userSignupFormDto){
        return userRepository.addUser(userSignupFormDto);
    }

    //닉네임 중복
    public void duplicateUsername(UserSignupFormDto userSignupFormDto){
        userRepository.findByUsername(userSignupFormDto.getUsername()).ifPresent(user -> {
            throw new IllegalArgumentException("중복된 닉네임 입니다.");
        });
    }

    //이메일 중복
    public void duplicateEmail(UserSignupFormDto userSignupFormDto){
        userRepository.findByEmail(userSignupFormDto.getEmail()).ifPresent(user ->{
            throw new IllegalArgumentException("중복된 이메일 입니다.");
        });
    }

    //유저 등록 //DTO -> Entity로 변환
    public UserSignupFormResponseDTO registerUser(UserSignupFormDto userSignupFormDto){
        duplicateUsername(userSignupFormDto);//중복 닉네임
        duplicateEmail(userSignupFormDto);//중복 이메일
        passwordConfirm(userSignupFormDto);//비밀번호 재 확인

        UserDto userDto = createUser(userSignupFormDto);//유저 저장

        //위에서 저장한 후 응답 DTO를 만들기 위해 userResponse 양식에 맞게 아래에 생성
        return new UserSignupFormResponseDTO(userDto.getUserId(),userDto.getUsername(),userDto.getEmail());//어떤 유저를 등록했는지
    }

    //비밀번호 재 확인
    public void passwordConfirm(UserSignupFormDto userSignupFormDto){
        if(!userSignupFormDto.getPassword().equals(userSignupFormDto.getConfirmPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    //유저가 존재하는지 확인
    //로그인
    public UserDto userExists(UserLoginDto userLoginDto) {
        UserDto user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호를 확인해주세요."));

        if (!userRepository.equalUserInfo(user, userLoginDto)) {
            throw new IllegalArgumentException("아이디 또는 비밀번호를 확인해주세요.");
        }

        return user;
    }

    //로그인
    //userId 반환
    public UserSignupFormResponseDTO login(UserLoginDto userLoginDto) {
        UserDto userDto = userExists(userLoginDto);
        //로그인 성공 시 유저 정보 반환

        return new UserSignupFormResponseDTO(userDto.getUserId(),userDto.getUsername(),userDto.getEmail());
    }

    //유저 정보 수정
    public UserDto updateUser(Long userId,UserSignupFormDto userSignupFormDto) {
        //유저 찾기
        return userRepository.updateUser(getUser(userId),userSignupFormDto);
    }

    //유저 정보 삭제
    public UserDto deletedUser(Long userId) {
        return userRepository.deleteUser(userId).orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
    }

}
