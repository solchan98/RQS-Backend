package com.example.rqs.api.quiz;

import com.example.rqs.core.common.exception.BadRequestException;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UpdateQuizValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UpdateQuizDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UpdateQuizDto updateQuizDto = (UpdateQuizDto) target;

        boolean isEmpty =
                updateQuizDto.getQuestion().isEmpty()
                || updateQuizDto.getAnswer().isEmpty();

        if (isEmpty) throw new BadRequestException();
    }
}
