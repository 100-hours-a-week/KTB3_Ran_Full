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

        String email = auth.getName();

        // 2) Access Token 생성
        String accessToken = tokenProvider.createToken(email);

        // 3) Refresh Token 생성
        String refreshToken = tokenProvider.createRefreshToken(email);

        // 4) Refresh Token DB 저장
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        user.toRefreshToken(refreshToken);
        userRepository.save(user);

        // 5) Access + Refresh 반환
        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refresh(String refreshToken) {


        if (!tokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh Token 유효하지 않음");
        }


        String email = tokenProvider.getEmail(refreshToken);

        // 3) DB에 저장된 refresh token과 일치하는지 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 불일치");
        }

        // 4) 새 access token 발급
        String newAccessToken = tokenProvider.createToken(email);

        // 5) 새 refresh token 발급
        String newRefreshToken = tokenProvider.createRefreshToken(email);

        // DB 업데이트
        user.toRefreshToken(newRefreshToken);
        userRepository.save(user);

        // 6) 리턴 DTO 생성
        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
