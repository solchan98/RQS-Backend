package com.example.rqs.api.space;

import com.example.rqs.api.jwt.JwtProvider;
import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.redis.RedisDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JoinSpaceValidator{

    private final JwtProvider jwtProvider;
    private final RedisDao redisDao;

    public JoinSpaceValidator(JwtProvider jwtProvider, RedisDao redisDao) {
        this.jwtProvider = jwtProvider;
        this.redisDao = redisDao;
    }

    public void validate(Object target) {
        String itk = (String) target;
        String values = redisDao.getValues(itk);
        String errorMessage = "링크가 올바르지 않습니다.";
        if (Objects.isNull(values)) {
            throw new BadRequestException(errorMessage);
        }

        try {
            jwtProvider.getInviteSpaceSubject(itk);
        } catch (JsonProcessingException e) {
            throw new BadRequestException(errorMessage);
        }

    }
}
