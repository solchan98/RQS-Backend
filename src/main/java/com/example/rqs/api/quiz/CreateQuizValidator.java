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
                || createQuizDto.getCreateAnswers().isEmpty();

        if (createQuizDto.getType().equals("form")) {
            checkIsValidToForm(createQuizDto);
        } else {
            checkIsValidMuti(createQuizDto);
        }

        if (isEmpty) throw new BadRequestException();
    }

    private void checkIsValidToForm(CreateQuizDto createQuizDto) {
        if (createQuizDto.getCreateAnswers().size() > 1) {
            throw new BadRequestException("정답을 2개 이상 작성할 수 없습니다.");
        }
    }

    private void checkIsValidMuti(CreateQuizDto createQuizDto){
        if (createQuizDto.getCreateAnswers().size() < 2) {
            throw new BadRequestException("정답을 최소 2개 이상 작성해야 합니다.");
        }
    }
}
