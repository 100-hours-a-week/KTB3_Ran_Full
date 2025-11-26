package com.ran.community.security;

import com.ran.community.user.dto.request.UserLoginDto;
import com.ran.community.user.dto.response.TokenResponse;
import com.ran.community.user.entity.User;
import com.ran.community.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public TokenResponse login(UserLoginDto dto) {

        // 1) 이메일/비밀번호 인증
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        String username = auth.getName();

        // 2) Access Token 생성
        String accessToken = tokenProvider.createToken(username);

        // 3) Refresh Token 생성
        String refreshToken = tokenProvider.createRefreshToken(username);

        // 4) Refresh Token DB 저장
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        user.toRefreshToken(refreshToken);
        userRepository.save(user);

        // 5) Access + Refresh 반환
        return new TokenResponse(accessToken, refreshToken);
    }
}
