package com.gswep.insurance.user.service;

import com.gswep.insurance.jwt.entity.RefreshToken;
import com.gswep.insurance.jwt.repository.RefreshTokenRepository;
import com.gswep.insurance.oauth.dto.OAuth2LoginRequestDTO;
import com.gswep.insurance.secure.JwtUtil;
import com.gswep.insurance.secure.OAuthJwtUtil;
import com.gswep.insurance.user.entity.User;
import com.gswep.insurance.user.entity.UserRoleEnum;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    private final OAuthJwtUtil oAuthJwtUtil;
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

        if(!oAuthJwtUtil.OAuthValidateToken(refreshToken)){
            log.error("유효하지 않은 리프레시 토큰입니다.");
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰 ");
        }

        RefreshToken storedToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("리프레시 토큰이 존재하지 않습니다."));
        User user = storedToken.getUser();
        log.info("리프레시 토큰에 해당하는 사용자 ID : {}", user.getUser_id());

        Authentication authentication = oAuthJwtUtil.OAuthgetAuthentication(refreshToken);
        TokenDTO newToken = oAuthJwtUtil.generateTokenDto(authentication);

        oAuthJwtUtil.saveRefreshToken(user , newToken.getRefreshToken(),
                LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusDays(7));
        return newToken;
    };

    public TokenDTO OAuth2Login(OAuth2LoginRequestDTO loginRequestDTO) {
        Optional<User> existingUser = userRepository.findByProviderAndProviderId(loginRequestDTO.getProvider(), loginRequestDTO.getProviderId());
        User user;
        if(existingUser.isPresent()){
            user = existingUser.get();
        } else {
            user = new User();
            user.setEmail(loginRequestDTO.getEmail());
            user.setUsername(loginRequestDTO.getName());
            user.setProvider(loginRequestDTO.getProvider());
            user.setProviderId(loginRequestDTO.getProviderId());
            user.setRole(UserRoleEnum.USER);
            userRepository.save(user);
        }

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUser_id(), null, authorities);

        TokenDTO loginToken = oAuthJwtUtil.generateTokenDto(authentication);

        try {
            oAuthJwtUtil.saveRefreshToken(
                    user, loginToken.getRefreshToken(),
                    LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusDays(7)
            );
        } catch (Exception e ){
            throw new RuntimeException("리프레시 토큰 저장에 실패했습니다.");
        }
        return loginToken;
    }
}
