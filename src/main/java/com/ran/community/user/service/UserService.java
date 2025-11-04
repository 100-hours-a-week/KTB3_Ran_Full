package com.ran.community.user.service;

import com.ran.community.user.dto.request.UserUpdatedDto;
import com.ran.community.user.entity.User;
import com.ran.community.user.dto.request.UserLoginDto;
import com.ran.community.user.dto.request.UserSignupFormDto;
import com.ran.community.user.dto.response.UserDataResponseDTO;
import com.ran.community.user.repository.UserRepository;
import jakarta.transaction.Transactional;
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

    }

    //이메일과 닉네임을 비교
    private boolean equalUserInfo(User user, UserLoginDto userLoginDto) {
        return user.getPassword().equals(userLoginDto.getPassword());
    }

    //식별자로 유저 찾기
    private User findById(long id){
        return userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }

//    //유저 객체 반환
//    public User findByUser(long id){
//        return findById(id);
//    }

    //유저 생성
    private User createUser(UserSignupFormDto userSignupFormDto){
        User user = new User(userSignupFormDto.getEmail(),userSignupFormDto.getUsername(),userSignupFormDto.getPassword());
        return userRepository.save(user);
    }

    //닉네임 중복
    private void duplicateUsername(UserSignupFormDto userSignupFormDto){
        userRepository.findByUsername(userSignupFormDto.getUsername()).ifPresent(user -> {
            throw new IllegalArgumentException("중복된 닉네임 입니다.");
        });
    }

    //이메일 중복
    private void duplicateEmail(UserSignupFormDto userSignupFormDto){
        userRepository.findByEmail(userSignupFormDto.getEmail()).ifPresent(user ->{
            throw new IllegalArgumentException("중복된 이메일 입니다.");
        });
    }

    //비밀번호과 재확인 일치하는지 확인하는 로직

    //유저 등록 //DTO -> Entity로 변환
    @Transactional
    public UserDataResponseDTO registerUser(UserSignupFormDto userSignupFormDto){
        duplicateUsername(userSignupFormDto);//중복 닉네임
        duplicateEmail(userSignupFormDto);//중복 이메일
        passwordConfirm(userSignupFormDto);//비밀번호 재 확인

        User user = createUser(userSignupFormDto);//유저 저장
        logger.info(user.toString());
        return new UserDataResponseDTO(user);
    }

    //비밀번호 재 확인
    private void passwordConfirm(UserSignupFormDto userSignupFormDto){
        if(!userSignupFormDto.getPassword().equals(userSignupFormDto.getConfirmPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    //유저가 존재하는지 확인
    //로그인
    private User userExists(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호를 확인해주세요."));

        if (!equalUserInfo(user, userLoginDto)) {
            throw new IllegalArgumentException("아이디 또는 비밀번호를 확인해주세요.");
        }

        return user;
    }

    //로그인
    //id 반환
    public UserDataResponseDTO login(UserLoginDto userLoginDto) {
        User user = userExists(userLoginDto);
        return new UserDataResponseDTO(user);
    }

    //유저 정보 조회
    public UserDataResponseDTO getUserData(long id){
        User user = findById(id);
        return new UserDataResponseDTO(user);
    }

    //유저 정보 수정
    @Transactional
    public UserDataResponseDTO updateUser(Long id, UserUpdatedDto userUpdatedDto) {
        User user = findById(id);
        user.updatedUser(userUpdatedDto);
        return new UserDataResponseDTO(user);
    }

    //유저 정보 삭제
    @Transactional
    public UserDataResponseDTO deletedUser(Long id) {
        User user = findById(id);
        userRepository.deleteById(id);
        return new UserDataResponseDTO(user);
    }


}
