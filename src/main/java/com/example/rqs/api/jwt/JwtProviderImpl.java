package com.example.rqs.api.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProviderImpl implements JwtProvider {

    @Value("${spring.jwt.key}")
    private String key;

    @Value("${spring.jwt.live.atk}")
    private Long atkLive;

    private final ObjectMapper objectMapper;

    public JwtProviderImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    @Override
    public String createAccessToken(String email, String nickname, String role) {
        try {
            Subject subject = new Subject(email, nickname, role);
            return this.createToken(subject, atkLive);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // TODO: 어떻게 예외처리할까..?
        }
    }

    @Override
    public Subject getSubject(String atk) {
        try {
            String subjectStr = Jwts.parser().setSigningKey(key).parseClaimsJws(atk).getBody().getSubject();
            return objectMapper.readValue(subjectStr, Subject.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // TODO: 어떻게 예외처리할까..?
        }
    }

    private String createToken(Subject subject, Long tokenLive) throws JsonProcessingException {
        String subjectStr = objectMapper.writeValueAsString(subject);
        Claims claims = Jwts.claims()
                .setSubject(subjectStr);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenLive))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
