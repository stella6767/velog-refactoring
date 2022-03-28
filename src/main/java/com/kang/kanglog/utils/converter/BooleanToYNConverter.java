package com.kang.kanglog.utils.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true) //true 면 모든 boolean 타입에 적용 @Convert를 붙이지 않더라도.
public class BooleanToYNConverter implements AttributeConverter<Boolean,String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData);
    }
}