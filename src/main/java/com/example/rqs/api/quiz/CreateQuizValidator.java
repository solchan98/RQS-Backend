package com.example.rqs.api.quiz;

import com.example.rqs.core.common.exception.BadRequestException;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CreateQuizValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(CreateQuizDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateQuizDto createQuizDto = (CreateQuizDto) target;

        boolean isEmpty =
                createQuizDto.getQuestion().isEmpty()
                || createQuizDto.getAnswer().isEmpty();

        if (isEmpty) throw new BadRequestException();
    }
}
