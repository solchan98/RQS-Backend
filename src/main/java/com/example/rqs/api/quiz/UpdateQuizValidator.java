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
                || updateQuizDto.getAnswers().isEmpty();

        if (isEmpty) {
            throw new BadRequestException();
        }

        if (updateQuizDto.getType().equals("form")) {
            checkIsValidToForm(updateQuizDto);
        } else {
            checkIsValidMutli(updateQuizDto);
        }
    }

    private void checkIsValidToForm(UpdateQuizDto updateQuizDto) {
        if (updateQuizDto.getAnswers().size() > 1) {
            throw new BadRequestException("정답을 2개 이상 작성할 수 없습니다.");
        }
    }

    private void checkIsValidMutli(UpdateQuizDto updateQuizDto){
        if (updateQuizDto.getAnswers().size() < 2 || updateQuizDto.getAnswers().size() > 4) {
            throw new BadRequestException("정답을 최소 2개 이상, 최대 4개 이하로 작성해야 합니다.");
        }
    }
}
