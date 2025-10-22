package com.ran.community.user.service;

import com.ran.community.user.entity.User;
import com.ran.community.user.dto.request.UserLoginDto;
import com.ran.community.user.dto.request.UserSignupFormDto;
import com.ran.community.user.dto.response.UserDataResponseDTO;
import com.ran.community.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //DI
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;//UserRepository를 인터페이스로 상속받고 있는 구현체들 중 필요한 클래스를 자동으로 생성자에 주입
        //때문에, 인터페이스로 분리해야만 spring의 DI를 활용할수있는 것.
        //기존 코드에서는 인터페이스가 아닌 구현체로 받고 있었기 때문에 autowired가 있으나 마나였던 것임.
        //wow
    }

    //이메일과 닉네임을 비교
    public boolean equalUserInfo(User user, UserLoginDto userLoginDto) {
        return user.getPassword().equals(userLoginDto.getPassword());
    }

    //식별자로 유저 찾기
    public User getUser(long userId){
        return userRepository.getUser(userId).orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }

    //유저 생성
    public User createUser(UserSignupFormDto userSignupFormDto){
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
    public UserDataResponseDTO registerUser(UserSignupFormDto userSignupFormDto){
        duplicateUsername(userSignupFormDto);//중복 닉네임
        duplicateEmail(userSignupFormDto);//중복 이메일
        passwordConfirm(userSignupFormDto);//비밀번호 재 확인

        User user = createUser(userSignupFormDto);//유저 저장
        logger.info(user.toString());
        return new UserDataResponseDTO(user);
    }

    //비밀번호 재 확인
    public void passwordConfirm(UserSignupFormDto userSignupFormDto){
        if(!userSignupFormDto.getPassword().equals(userSignupFormDto.getConfirmPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    //유저가 존재하는지 확인
    //로그인
    public User userExists(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호를 확인해주세요."));

        if (!equalUserInfo(user, userLoginDto)) {
            throw new IllegalArgumentException("아이디 또는 비밀번호를 확인해주세요.");
        }

        return user;
    }

    //로그인
    //userId 반환
    public UserDataResponseDTO login(UserLoginDto userLoginDto) {
        User user = userExists(userLoginDto);
        //로그인 성공 시 유저 정보 반환

        return new UserDataResponseDTO(user.getUserId(), user.getUsername(), user.getEmail());
    }

    //유저 정보 조회
    public UserDataResponseDTO getUserData(long userId){
        User user = getUser(userId);
        return new UserDataResponseDTO(user.getUserId(), user.getUsername(), user.getPassword());
    }

    //유저 정보 수정
    public UserDataResponseDTO updateUser(Long userId, UserSignupFormDto userSignupFormDto) {
        //유저 찾기
        User user = userRepository.updateUser(getUser(userId), userSignupFormDto);
        return new UserDataResponseDTO(user);
    }

    //유저 정보 삭제
    public UserDataResponseDTO deletedUser(Long userId) {
        User user = userRepository.deleteUser(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        return new UserDataResponseDTO(user);
    }


}
