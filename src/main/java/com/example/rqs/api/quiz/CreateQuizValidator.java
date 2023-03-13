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

        if (isEmpty) {
            throw new BadRequestException();
        }

        if (createQuizDto.getType().equals("form")) {
            checkIsValidToForm(createQuizDto);
        } else {
            checkIsValidMutli(createQuizDto);
        }
    }

    private void checkIsValidToForm(CreateQuizDto createQuizDto) {
        if (createQuizDto.getCreateAnswers().size() > 1) {
            throw new BadRequestException("정답을 2개 이상 작성할 수 없습니다.");
        }
    }

    private void checkIsValidMutli(CreateQuizDto createQuizDto){
        if (createQuizDto.getCreateAnswers().size() < 2 || createQuizDto.getCreateAnswers().size() > 4) {
            throw new BadRequestException("정답을 최소 2개 이상, 최대 4개 이하로 작성해야 합니다.");
        }
    }
}
