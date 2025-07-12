package com.gswep.insurance.secure;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

// JWT 추출
// 토큰의 유효성을 검증

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final OAuthJwtUtil tokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.error("JwtFilter called: {}", request);
        log.error("JWTFilter header called: {}", request.getHeader("Authorization"));
        String jwt = OAuthResolveToken(request); // 헤더에서 JWT 추출
        log.error("Extracted JWT: {}", jwt);
        if (StringUtils.hasText(jwt) && tokenProvider.OAuthValidateToken(jwt)) {
            Authentication authentication = tokenProvider.OAuthgetAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication); // 인증 객체 설정
        }

        filterChain.doFilter(request, response); // 다음 필터로 요청 전달
    }

    // Authorization 헤더에서 토큰 추출
    private String OAuthResolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 부분 제거
        }
        return null;
    }

}