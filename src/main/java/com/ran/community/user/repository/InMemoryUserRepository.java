package com.ran.community.user.repository;

import com.ran.community.global.IdGeneratorFactory;
import com.ran.community.global.UserIdGenerator;
import com.ran.community.user.entity.User;
import com.ran.community.user.dto.request.UserSignupFormDto;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final IdGeneratorFactory idGeneratorFactory;

    @Autowired
    private InMemoryUserRepository(IdGeneratorFactory idGeneratorFactory) {
        this.idGeneratorFactory = idGeneratorFactory;
    };


    @Override
    public Optional<User> getUser(long userId) {
        return Optional.ofNullable(Users.get(userId));//User map에서 userId인 키를 가진 값을 반환
    }

    @Override
    public User addUser(UserSignupFormDto userSignupFormDto) {
        long id = idGeneratorFactory.nextId(User.class); //해당 Id가 idMap에 저장 또는 숫자 증가.
        User user = new User(id,userSignupFormDto.getEmail(),userSignupFormDto.getUsername(),userSignupFormDto.getPassword());
        UsersByEmail.put(userSignupFormDto.getEmail(), user);
        Users.put(user.getUserId(), user);
        return user;
    }

    //로직 처리 : false true등은 Service의역할이기 때문에 Repository는 DB에서 값을 가져오는 직접적인 논리만 작성함.
    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(UsersByEmail.get(email));
    }

    //얘도 Map으로 바꾸면 어때
    @Override
    public Optional<User> findByUsername(String username) {
        return Users.values().stream().filter(it -> it.getUsername().equals(username)).findFirst();

    }

    @Override
    public User updateUser(User user, UserSignupFormDto userSignupFormDto) {
        deleteUser(user.getUserId());
        User updateUser = new User(user.getUserId(),userSignupFormDto.getEmail(),userSignupFormDto.getUsername(),userSignupFormDto.getPassword());
        Users.put(user.getUserId(),updateUser);
        return updateUser;
    }

    @Override
    public Optional<User> deleteUser(long userId) {
        return Optional.ofNullable(Users.remove(userId));
    }
}
