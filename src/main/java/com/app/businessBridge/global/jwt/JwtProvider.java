package com.app.businessBridge.global.jwt;

import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.util.Util;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    @Value("${custom.jwt.secretKey}")
    private String secretKeyOrigin;

    private SecretKey cachedSecretKey;

    // 캐시된 비밀 키를 리턴하고, 캐시된 키가 없는 경우,
    // _getSecretKey() 메서드를 호출하여 새로운 비밀 키를 생성하고 리턴합니다.
    public SecretKey getSecretKey() {
        if (cachedSecretKey == null) cachedSecretKey = _getSecretKey();

        return cachedSecretKey;
    }

    // secretKeyOrigin을 기반으로 비밀 키를 생성.
    private SecretKey _getSecretKey() {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyOrigin.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    public String genRefreshToken(Member member) {
        return genToken(member, 60 * 60 * 24 * 365 * 1);
    }

    public String genAccessToken(Member member) {
        return genToken(member, 60 * 10);
    }

    // 주어진 클레임과 만료 시간을 사용하여 JWT를 생성.
    // 생성된 토큰은 주어진 클레임을 포함하고, 주어진 만료 시간 후에 만료됨.
    public String genToken(Member member, int seconds) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", member.getId());
        claims.put("username", member.getUsername());

        long now = new Date().getTime();
        Date accessTokenExpiresIn = new Date(now + 1000L * seconds);

        return Jwts.builder()
                .claim("body", Util.json.toStr(claims))
                .setExpiration(accessTokenExpiresIn)
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // 주어진 토큰이 유효한지를 검증.
    public boolean verify(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    // 주어진 토큰에서 클레임을 추출하여 반환합니다.
    public Map<String, Object> getClaims(String token) {
        String body = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("body", String.class);

        return Util.toMap(body);
    }

}