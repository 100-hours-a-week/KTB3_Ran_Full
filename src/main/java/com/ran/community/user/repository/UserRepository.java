package com.ran.community.user.repository;

import com.ran.community.user.entity.User;
import com.ran.community.user.dto.request.UserLoginDto;
import com.ran.community.user.dto.request.UserSignupFormDto;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    //db를 대신하여 dto를 repository로 map으로 users db를 만드는 거구나.
    private AtomicLong index = new AtomicLong(0); //인덱스를 담당
    private Map<Long, User> Users = new ConcurrentHashMap<>(); //유저 객체를 담는 DB



    //특정 유저 가져오기
    public Optional<User> getUser(long userId) {
        return Optional.ofNullable(Users.get(userId));//User map에서 userId인 키를 가진 값을 반환
    }

    //유저 생성
    public User addUser(UserSignupFormDto userSignupFormDto) {
        User user = new User();
        //DI에 해치지 않을까?
        // -> 용도는 폼에서 생성된 객체와 분리되기 때문에, 실제 user entity는 만들어지지 않음

        user.setUserId(index.getAndIncrement());//값 반환 후 index 증가;
        user.setPassword(userSignupFormDto.getPassword());
        user.setUsername(userSignupFormDto.getUsername());
        user.setEmail(userSignupFormDto.getEmail());
        Users.put(user.getUserId(), user);
        return user;
    }

    //로직 처리 : false true등은 Service의역할이기 때문에 Repository는 DB에서 값을 가져오는 직접적인 논리만 작성함.
    //이메일 찾기
    public Optional<User> findByEmail(String email) {
        return Users.values().stream().filter(it -> it.getEmail().equals(email)).findFirst();
    }
    //닉네임 찾기
    public Optional<User> findByUsername(String username) {
        return Users.values().stream().filter(it -> it.getUsername().equals(username)).findFirst();

    }

    //이메일과 닉네임을 비교
    public boolean equalUserInfo(User user, UserLoginDto userLoginDto) {
        return user.getPassword().equals(userLoginDto.getPassword());
    }

    //유저 정보 넣기
    public User updateUser(User user, UserSignupFormDto userSignupFormDto) {
        user.setPassword(userSignupFormDto.getPassword());
        user.setUsername(userSignupFormDto.getUsername());
        user.setEmail(userSignupFormDto.getEmail());
        return user;
    }

    //유저 삭제하기
    public Optional<User> deleteUser(long userId) {
        return Optional.ofNullable(Users.remove(userId));
    }
}
