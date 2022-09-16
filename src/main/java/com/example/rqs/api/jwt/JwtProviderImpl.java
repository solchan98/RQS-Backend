package com.example.rqs.api.jwt;

import com.example.rqs.core.common.redis.RedisDao;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtProviderImpl implements JwtProvider {

    @Value("${spring.jwt.key}")
    private String key;

    @Value("${spring.jwt.live.atk}")
    private Long atkLive;

    @Value("${spring.jwt.live.rtk}")
    private Long rtkLive;

    private final ObjectMapper objectMapper;

    private final RedisDao redisDao;

    public JwtProviderImpl(ObjectMapper objectMapper, RedisDao redisDao) {
        this.objectMapper = objectMapper;
        this.redisDao = redisDao;
    }

    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    @Override
    public TokenResponse createTokenList(String email, String nickname, String role) {
        try {
            Subject subject = new Subject(email, nickname, role);
            String atk = this.createToken(subject, atkLive);
            String rtk = this.createToken(subject, rtkLive);
            redisDao.setValues(email, rtk, Duration.ofMillis(rtkLive));
            return TokenResponse.of(atk, rtk);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String reissueAtk(String email, String nickname, String role) {
        String rtkInRedis = redisDao.getValues(email);
        if (Objects.isNull(rtkInRedis)) throw new ForbiddenException("인증 정보가 만료되었습니다.");
        TokenResponse tokenList = this.createTokenList(email, nickname, role);
        return tokenList.getAtk();
    }

    @Override
    public Subject getSubject(String atk) throws JwtException, JsonProcessingException {
        String subjectStr = Jwts.parser().setSigningKey(key).parseClaimsJws(atk).getBody().getSubject();
        return objectMapper.readValue(subjectStr, Subject.class);
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
