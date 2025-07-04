package com.gswep.insurance.secure;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gswep.insurance.user.entity.UserRoleEnum;
import com.gswep.insurance.user.requestDTO.LoginRequestDto;
import com.gswep.insurance.user.service.UserDetailsImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        // 로그인 처리 URL 설정
        setFilterProcessesUrl("/auth/login");
    }


    // 로그인 시도 처리
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 요청에서 로그인 정보를 읽어와 DTO에 매핑
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            // 인증 매니저를 통해 사용자 인증 시도
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),  // 사용자명
                            requestDto.getPassword(),  // 비밀번호
                            null  // 권한 목록은 null로 전달
                    )
            );
        } catch (IOException e) {
            // 예외 발생 시 로그 출력 및 RuntimeException 던지기
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    // 로그인 성공 시 처리
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        // 사용자명과 역할 정보를 가져옴
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        // JWT 토큰 생성
        String token = jwtUtil.createToken(username, role);
        // 응답 헤더에 JWT 토큰 추가
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
    }

    // 로그인 실패 시 처리
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

}
