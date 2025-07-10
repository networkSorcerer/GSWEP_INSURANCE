package com.gswep.insurance.secure;

import com.gswep.insurance.jwt.entity.RefreshToken;
import com.gswep.insurance.jwt.repository.RefreshTokenRepository;
import com.gswep.insurance.user.entity.UserRoleEnum;
import com.gswep.insurance.user.requestDTO.TokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분
    // 리프레시 토큰 시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7L;

    private static final String BEARER_TYPE = "Bearer"; // 토큰의 타입

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Autowired
    public JwtUtil(@Value("${jwt.secret}") String secretKey, RefreshTokenRepository refreshTokenRepository) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // 객체 초기화
    @PostConstruct
    public void init() {
        // SecretKey를 Base64 디코딩하여 키 초기화
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }
    public TokenDTO generateTokenDto(Authentication authentication) {
        // 인증 객체에서 권한 정보 추출
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime(); // 현재 시간
        // 토큰 만료 시간 설정
        Date accessTokenExpiresIn = new Date(now + TOKEN_TIME);
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // 사용자명 설정
                .claim(AUTHORIZATION_KEY, authorities)  // 권한 정보 저장
                .setExpiration(accessTokenExpiresIn)  // 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS512) // 서명 방식 설정
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName()) // 사용자명 설정
                .claim(AUTHORIZATION_KEY, authorities)  // 권한 정보 저장
                .setExpiration(refreshTokenExpiresIn)  // 만료 시간 설정 (Date로 변환)
                .signWith(key, SignatureAlgorithm.HS512) // 서명 방식 설정
                .compact();


        // 결과를 DTO로 반환
        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)  // 리플레시 토큰
                .refreshTokenExpiresIn(refreshTokenExpiresIn.getTime()) // 리프레시 토큰 만료 시간
                .build();
    }

    // JwtAuthenticationFilter의 successfulAuthentication()에서 사용됨 (로그인 성공 시)
    // 토큰 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact(); // 토큰 생성
    }


    // JwtAuthorizationFilter의 doFilterInternal()에서 사용됨
    // HTTP 요청 헤더에서 JWT 추출 메서드
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        // JWT 토큰이 존재하고 "Bearer " 접두사로 시작할 경우 토큰 반환
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);  // "Bearer " 접두사 제거 후 토큰 반환
        }
        return null;  // 토큰 없을 경우 null 반환
    }


    // JwtAuthorizationFilter의 doFilterInternal()에서 사용됨
    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            // 주어진 토큰의 유효성을 검증하고 유효할 경우 true 반환
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;  // 유효하지 않은 경우 false 반환
    }


    // JwtAuthorizationFilter의 doFilterInternal()에서 사용됨
    // 토큰에서 사용자 정보 추출 메서드
    public Claims getUserInfoFromToken(String token) {
        // 주어진 토큰에서 클레임 정보(사용자 정보)를 파싱하여 반환
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Authentication getAuthentication(String accessToken){
        Claims claims = parseClaims(accessToken);
        if(claims.get(AUTHORIZATION_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORIZATION_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
    }

    private Claims parseClaims(String token){
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }
    public void saveRefreshToken(com.gswep.insurance.user.entity.User user, String refreshToken, LocalDateTime expirationDate) {
        // 기존 리프레시 토큰 조회
        RefreshToken existingRefreshToken = refreshTokenRepository.findByUser(user)
                .orElse(null); // 없으면 null 반환

        if (existingRefreshToken != null) {
            // 기존 리프레시 토큰이 있으면 갱신
            existingRefreshToken.setRefreshToken(refreshToken);  // 리프레시 토큰 업데이트
            existingRefreshToken.setExpirationDate(expirationDate);  // 만료일 업데이트
            refreshTokenRepository.save(existingRefreshToken);  // 갱신된 리프레시 토큰 저장
        } else {
            // 기존 리프레시 토큰이 없으면 새로 생성하여 저장
            RefreshToken refreshTokenEntity = RefreshToken.builder()
                    .user(user)
                    .refreshToken(refreshToken)
                    .expirationDate(expirationDate)
                    .build();

            refreshTokenRepository.save(refreshTokenEntity);  // 새로 저장
        }
    }
}