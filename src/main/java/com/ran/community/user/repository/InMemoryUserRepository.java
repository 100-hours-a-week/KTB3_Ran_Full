package com.ran.community.user.repository;

import com.ran.community.global.UserIdGenerator;
import com.ran.community.user.entity.User;
import com.ran.community.user.dto.request.UserSignupFormDto;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class InMemoryUserRepository implements UserRepository {
    //DB를 대신하는 Users Map도 Index를 보장 받아야되기 때문에 싱글톤으로 구현
    private final Map<Long, User> Users = new ConcurrentHashMap<>(); //유저 객체를 담는 DB
    //아 설마 이거 쓰임새가 DB에서 값을 모두 가져와서 해당 Map에 저장하나? 그럴리가
    private final Map<String, User> UsersByEmail = new ConcurrentHashMap<>();


    @Override
    public Optional<User> getUser(long userId) {
        return Optional.ofNullable(Users.get(userId));//User map에서 userId인 키를 가진 값을 반환
    }

    @Override
    public User addUser(UserSignupFormDto userSignupFormDto) {
        User user = new User();

        user.setUserId(UserIdGenerator.getInstance().nextId());//값 반환 후 index 증가;
        user.setPassword(userSignupFormDto.getPassword());
        user.setUsername(userSignupFormDto.getUsername());
        UsersByEmail.put(userSignupFormDto.getEmail(), user);
        user.setEmail(userSignupFormDto.getEmail());
        Users.put(user.getUserId(), user);
        return user;
    }

    //로직 처리 : false true등은 Service의역할이기 때문에 Repository는 DB에서 값을 가져오는 직접적인 논리만 작성함.
    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(UsersByEmail.get(email));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Users.values().stream().filter(it -> it.getUsername().equals(username)).findFirst();

    }

    //유저 정보 수정하기 업데이트는 set을 써야하지않아?
    @Override
    public User updateUser(User user, UserSignupFormDto userSignupFormDto) {
        user.setEmail(userSignupFormDto.getEmail());
        user.setUsername(userSignupFormDto.getUsername());
        user.setPassword(userSignupFormDto.getPassword());
        return user;
    }


    @Override
    public Optional<User> deleteUser(long userId) {
        return Optional.ofNullable(Users.remove(userId));
    }
}
