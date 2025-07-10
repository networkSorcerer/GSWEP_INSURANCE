package com.gswep.insurance.user.service;

import com.gswep.insurance.jwt.entity.RefreshToken;
import com.gswep.insurance.jwt.repository.RefreshTokenRepository;
import com.gswep.insurance.secure.JwtUtil;
import com.gswep.insurance.user.entity.User;
import com.gswep.insurance.user.repository.UserRepository;
import com.gswep.insurance.user.requestDTO.LoginRequestDto;
import com.gswep.insurance.user.requestDTO.TokenDTO;
import com.gswep.insurance.user.requestDTO.UserRequestDTO;
import com.gswep.insurance.user.response.UserResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtil tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserResponseDTO signUp(UserRequestDTO requestDTO) {
        if(userRepository.existsByEmail(requestDTO.getEmail())){
            throw new IllegalStateException("이미 등록된 Email입니다.");
        }

        User user = User.builder()
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .username(requestDTO.getUsername())
                .phoneNumber(requestDTO.getPhoneNumber())
                .role(requestDTO.getRoleEnum())
                .build();

        User saveUser = userRepository.save(user);
        return new UserResponseDTO(saveUser);
    }

    public String login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        log.info("로그인 요청 - 이메일: {}", loginRequestDto.getEmail());

        User useremail = userRepository.findByEmail(loginRequestDto.getEmail());
        User userrole = userRepository.findByEmail(loginRequestDto.getEmail());
        String loginToken = tokenProvider.createToken(useremail.getEmail(),userrole.getRole() );

        return loginToken;
    }

    public TokenDTO reissueAccessToken(String refreshToken) {
        log.info("토큰 재발급 요청 - 리프레시 토큰 : {}", refreshToken);

        if(!tokenProvider.validateToken(refreshToken)){
            log.error("유효하지 않은 리프레시 토큰입니다.");
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰 ");
        }

        RefreshToken storedToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("리프레시 토큰이 존재하지 않습니다."));
        User user = storedToken.getUser();
        log.info("리프레시 토큰에 해당하는 사용자 ID : {}", user.getUser_id());

        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        TokenDTO newToken = tokenProvider.generateTokenDto(authentication);

        tokenProvider.saveRefreshToken(user , newToken.getRefreshToken(),
                LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusDays(7));
        return newToken;
    };
}
