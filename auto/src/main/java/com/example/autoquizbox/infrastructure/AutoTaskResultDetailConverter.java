package com.example.autoquizbox.infrastructure;

import com.example.autoquizbox.domain.AutoTaskDetailResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Converter
@Component
@RequiredArgsConstructor
public class AutoTaskResultDetailConverter implements AttributeConverter<AutoTaskDetailResult, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(AutoTaskDetailResult attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public AutoTaskDetailResult convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, AutoTaskDetailResult.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}