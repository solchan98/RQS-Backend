package com.example.rqs.api.jwt;

import com.example.rqs.core.common.redis.RedisDao;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.member.service.dtos.MemberDto;
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

    @Value("${spring.jwt.live.itk}")
    private Long itkLive;

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
    public TokenResponse createTokensByLogin(MemberDto memberDto) {
        Subject atkSubject = Subject.atk(memberDto);
        Subject rtkSubject = Subject.rtk(memberDto);
        try {
            String atk = this.createToken(atkSubject, atkLive);
            String rtk = this.createToken(rtkSubject, rtkLive);
            redisDao.setValues(memberDto.getEmail(), rtk, Duration.ofMillis(rtkLive));
            return TokenResponse.of(atk, rtk);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // Todo: CustomException
        }
    }

    @Override
    public String reissueAtk(MemberDto memberDto) {
        String rtkInRedis = redisDao.getValues(memberDto.getEmail());
        Subject atkSubject = Subject.atk(memberDto);
        if (Objects.isNull(rtkInRedis)) throw new ForbiddenException("인증 정보가 만료되었습니다.");
        try {
            return this.createToken(atkSubject, atkLive);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // Todo: CustomException
        }
    }

    @Override
    public Subject getSubject(String atk) throws JwtException, JsonProcessingException {
        String subjectStr = Jwts.parser().setSigningKey(key).parseClaimsJws(atk).getBody().getSubject();
        return objectMapper.readValue(subjectStr, Subject.class);
    }

    @Override
    public InviteSpaceTokenResponse createInviteToken(InviteSpaceSubject inviteSpaceSubject) {
        try {
            String subjectStr = objectMapper.writeValueAsString(inviteSpaceSubject);
            Claims claims = Jwts.claims()
                    .setSubject(subjectStr);
            Date date = new Date();
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(date)
                    .setExpiration(new Date(date.getTime() + itkLive))
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();
            redisDao.setValues(token, inviteSpaceSubject.getInviterNickname(), Duration.ofMillis(itkLive));
            return InviteSpaceTokenResponse.of(token);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public InviteSpaceSubject getInviteSpaceSubject(String itk) throws JsonProcessingException {
        String subjectStr = Jwts.parser().setSigningKey(key).parseClaimsJws(itk).getBody().getSubject();
        return objectMapper.readValue(subjectStr, InviteSpaceSubject.class);
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
