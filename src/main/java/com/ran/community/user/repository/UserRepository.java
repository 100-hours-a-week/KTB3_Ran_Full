package com.ran.community.user.repository;

import com.ran.community.user.dto.UserDto;
import com.ran.community.user.dto.UserLoginDto;
import com.ran.community.user.dto.UserSignupFormDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    //db를 대신하여 dto를 repository로 map으로 users db를 만드는 거구나.
    private long index; //인덱스를 담당
    private Map<Long, UserDto> Users = new LinkedHashMap<>(); //유저 객체를 담는 DB

    //특정 유저 가져오기
    public Optional<UserDto> getUser(long userId) {
        return Optional.ofNullable(Users.get(userId));//User map에서 userId인 키를 가진 값을 반환
    }

    //유저 생성
    public UserDto addUser(UserSignupFormDto userSignupFormDto) {
        index++;//행 추가

        UserDto userDto = new UserDto();
        //DI에 해치지 않을까?
        // -> 용도는 폼에서 생성된 객체와 분리되기 때문에, 실제 user entity는 만들어지지 않음

        userDto.setUserId(index);
        userDto.setPassword(userSignupFormDto.getPassword());
        userDto.setUsername(userSignupFormDto.getUsername());
        userDto.setEmail(userSignupFormDto.getEmail());
        Users.put(userDto.getUserId(), userDto);
        return userDto;
    }

    //로직 처리 : false true등은 Service의역할이기 때문에 Repository는 DB에서 값을 가져오는 직접적인 논리만 작성함.
    //이메일 찾기
    public Optional<UserDto> findByEmail(String email) {
        return Users.values().stream().filter(it -> it.getEmail().equals(email)).findFirst();
    }
    //닉네임 찾기
    public Optional<UserDto> findByUsername(String username) {
        return Users.values().stream().filter(it -> it.getUsername().equals(username)).findFirst();

    }

    //이메일과 닉네임을 비교
    public boolean equalUserInfo(UserDto userDto, UserLoginDto userLoginDto) {
        return userDto.getPassword().equals(userLoginDto.getPassword());
    }

    //유저 정보 넣기
    public UserDto updateUser(UserDto userDto, UserSignupFormDto userSignupFormDto) {
        userDto.setPassword(userSignupFormDto.getPassword());
        userDto.setUsername(userSignupFormDto.getUsername());
        userDto.setEmail(userSignupFormDto.getEmail());
        return userDto;
    }

    //유저 삭제하기
    public Optional<UserDto> deleteUser(long userId) {
        return Optional.ofNullable(Users.remove(userId));
    }
}
