package com.gswep.insurance.user.service;

import com.gswep.insurance.secure.JwtUtil;
import com.gswep.insurance.user.entity.User;
import com.gswep.insurance.user.repository.UserRepository;
import com.gswep.insurance.user.requestDTO.LoginRequestDto;
import com.gswep.insurance.user.requestDTO.UserRequestDTO;
import com.gswep.insurance.user.response.UserResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtil tokenProvider;
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
}
