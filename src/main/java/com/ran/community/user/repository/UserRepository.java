package com.ran.community.user.repository;

import com.ran.community.user.dto.request.UserSignupFormDto;
import com.ran.community.user.entity.User;

import java.util.Optional;

public interface UserRepository {
    //특정 유저 가져오기
    Optional<User> getUser(long userId);

    //유저 생성
    User addUser(UserSignupFormDto userSignupFormDto);

    //이메일 찾기
    Optional<User> findByEmail(String email);

    //닉네임 찾기
    Optional<User> findByUsername(String username);

    //유저 정보 넣기
    User updateUser(User user, UserSignupFormDto userSignupFormDto);

    //유저 삭제하기
    Optional<User> deleteUser(long userId);
}
