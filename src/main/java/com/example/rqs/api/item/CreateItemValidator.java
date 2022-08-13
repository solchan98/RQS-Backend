package com.example.rqs.api.item;

import com.example.rqs.core.common.exception.BadRequestException;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CreateItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(CreateItemDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateItemDto createItemDto = (CreateItemDto) target;

        boolean isEmpty =
                createItemDto.getQuestion().isEmpty()
                || createItemDto.getAnswer().isEmpty();

        if (isEmpty) throw new BadRequestException();
    }
}
