package com.example.autoquizbox.infrastructure;

import com.example.autoquizbox.domain.AutoTaskRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Converter
@Component
@RequiredArgsConstructor
public class AutoTaskRequestConverter implements AttributeConverter<AutoTaskRequest, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(AutoTaskRequest attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public AutoTaskRequest convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, AutoTaskRequest.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}