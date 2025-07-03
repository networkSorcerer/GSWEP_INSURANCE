//package com.gswep.insurance.security;
//
//import com.gswep.insurance.jwt.TokenProvider;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Slf4j
//@RequiredArgsConstructor
//public class JwtFilter extends OncePerRequestFilter {
//    private final TokenProvider tokenProvider;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//        throws ServletException, IOException{
//        log.error("JwtFilter called: {}",request);
//        log.error("JWTFilter header called:{}", request.getHeader("Authorization"));
//        String jwt = resolveToken(request);
//        log.error("Extracted JWT: {}",jwt);
//        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
//            Authentication authentication = tokenProvider.getAuthentication(jwt);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        filterChain.doFilter(request, response);
//    }
//    private String resolveToken(HttpServletRequest request){
//        String bearerToken = request.getHeader("Authorization");
//        if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer")){
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}
