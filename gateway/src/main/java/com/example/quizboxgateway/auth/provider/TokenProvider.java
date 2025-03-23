package com.example.quizboxgateway.auth.provider;

import com.example.quizboxgateway.auth.token.BearerAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class TokenProvider {

    private SecretKey key = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode("secretKeysecretKeysecretKeysecretKeysecretKeysecretKeysecretKey"));

    public BearerAuthenticationToken createToken(String subject, Map<String, Object> payload, long ttl) {
        Claims claims = Jwts.claims()
                .subject(subject)
                .add(payload)
                .build();
        Date date = new Date();

        String token = Jwts.builder()
                .claims(claims)
                .issuedAt(date)
                .expiration(new Date(date.getTime() + ttl))
                .signWith(key)
                .compact();

        return new BearerAuthenticationToken(token, true);
    }

    public Claims verify(String token, String subject) {
        try {
            Claims payload = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            checkTokenType(payload, subject);

            return payload;
        } catch (JwtException e) {
            throw new BadCredentialsException("인증 정보를 확인하세요.", e);
        }
    }

    private void checkTokenType(Claims claims, String subject) {
        if (!subject.equals(claims.getSubject())) {
            throw new BadCredentialsException("인증 정보를 확인하세요.");
        }
    }
}
