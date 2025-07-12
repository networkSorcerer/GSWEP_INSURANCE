package com.gswep.insurance.secure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Base64;
@Component
public class GoogleTokenVerifier {

    private final GooglePublicKeyUtil keyUtil = new GooglePublicKeyUtil();

    // 검증 대상: 구글 ID 토큰, 그리고 내 클라이언트 ID
    public Claims verifyGoogleToken(String idToken, String expectedClientId) throws Exception {
        // 1. 헤더 파싱
        String[] parts = idToken.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token structure");
        }

        String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
        JSONObject header = new JSONObject(headerJson);

        // 2. kid 추출
        String kid = header.getString("kid");

        // 3. 공개키 로드
        PublicKey publicKey = keyUtil.getGooglePublicKey(kid);

        // 4. JWT 검증
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build();

        Jws<Claims> jws = parser.parseClaimsJws(idToken);
        Claims claims = jws.getBody();

        // 5. 클레임 검증
        String issuer = claims.getIssuer();
        String audience = claims.getAudience();
        if (!"https://accounts.google.com".equals(issuer) && !"accounts.google.com".equals(issuer)) {
            throw new SecurityException("Invalid issuer: " + issuer);
        }
        if (!expectedClientId.equals(audience)) {
            throw new SecurityException("Invalid audience: " + audience);
        }

        // 6. 만료 확인은 JJWT가 자동 처리해줌 (exp claim 체크됨)

        return claims; // sub (user ID), email 등 추출 가능
    }
}
