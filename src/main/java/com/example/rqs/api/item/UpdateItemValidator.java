package com.example.rqs.api.item;

import com.example.rqs.core.common.exception.BadRequestException;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UpdateItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UpdateItemDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UpdateItemDto updateItemDto = (UpdateItemDto) target;

        boolean isEmpty =
                updateItemDto.getQuestion().isEmpty()
                || updateItemDto.getAnswer().isEmpty();

        if (isEmpty) throw new BadRequestException();
    }
}
