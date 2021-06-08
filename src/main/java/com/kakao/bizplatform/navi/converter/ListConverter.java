package com.kakao.bizplatform.navi.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.bizplatform.common.exception.ApiCommonException;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Slf4j
@Converter
public class ListConverter implements AttributeConverter<List<Object>, String> {
    @Override
    public String convertToDatabaseColumn(List<Object> attribute) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new ApiCommonException(e.getMessage());
        }
    }

    @Override
    public List<Object> convertToEntityAttribute(String dbData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(dbData, List.class);
        } catch (JsonProcessingException e) {
            throw new ApiCommonException(e.getMessage());
        }
    }
}
