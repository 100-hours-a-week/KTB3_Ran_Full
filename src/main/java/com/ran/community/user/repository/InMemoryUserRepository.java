package com.ran.community.user.repository;

import com.ran.community.user.entity.User;
import com.ran.community.user.dto.request.UserSignupFormDto;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserRepository implements UserRepository {
    //db를 대신하여 dto를 repository로 map으로 users db를 만드는 거구나.
    private AtomicLong index = new AtomicLong(0); //인덱스를 담당
    private Map<Long, User> Users = new ConcurrentHashMap<>(); //유저 객체를 담는 DB


    @Override
    public Optional<User> getUser(long userId) {
        return Optional.ofNullable(Users.get(userId));//User map에서 userId인 키를 가진 값을 반환
    }

    @Override
    public User addUser(UserSignupFormDto userSignupFormDto) {
        User user = new User();

        user.setUserId(index.getAndIncrement());//값 반환 후 index 증가;
        user.setPassword(userSignupFormDto.getPassword());
        user.setUsername(userSignupFormDto.getUsername());
        user.setEmail(userSignupFormDto.getEmail());
        Users.put(user.getUserId(), user);
        return user;
    }

    //로직 처리 : false true등은 Service의역할이기 때문에 Repository는 DB에서 값을 가져오는 직접적인 논리만 작성함.
    @Override
    public Optional<User> findByEmail(String email) {
        return Users.values().stream().filter(it -> it.getEmail().equals(email)).findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Users.values().stream().filter(it -> it.getUsername().equals(username)).findFirst();

    }

    //유저 정보 넣기
    @Override
    public User updateUser(User user, UserSignupFormDto userSignupFormDto) {
        return new User(user.getUserId(),userSignupFormDto.getEmail(),userSignupFormDto.getUsername(),userSignupFormDto.getPassword()
        );
    }

    @Override
    public Optional<User> deleteUser(long userId) {
        return Optional.ofNullable(Users.remove(userId));
    }
}
