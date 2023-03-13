package com.example.rqs.api.member;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.member.service.dtos.SignUpDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class SignUpValidator implements Validator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$");

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpDto signUpDto = (SignUpDto) target;

        boolean isEmpty =
                signUpDto.getEmail().isEmpty()
                || signUpDto.getPassword().isEmpty()
                || signUpDto.getNickname().isEmpty();
        if (isEmpty) throw new BadRequestException();

        boolean emailMatches = EMAIL_PATTERN.matcher(signUpDto.getEmail()).matches();
        boolean passwordMatches = PASSWORD_PATTERN.matcher(signUpDto.getPassword()).matches();
        if (!emailMatches) throw new BadRequestException("이메일 형식에 맞지 않습니다.");
        if (!passwordMatches) throw new BadRequestException("비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.");
    }
}
