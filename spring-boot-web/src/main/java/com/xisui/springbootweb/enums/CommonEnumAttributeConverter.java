package com.xisui.springbootweb.enums;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.List;

public abstract class CommonEnumAttributeConverter<E extends Enum<E> & CommonEnum>
        implements AttributeConverter<E, Integer> {
    private final List<E> commonEnums;

    public CommonEnumAttributeConverter(E[] commonEnums){
        this(Arrays.asList(commonEnums));
    }

    public CommonEnumAttributeConverter(List<E> commonEnums) {
        this.commonEnums = commonEnums;
    }

    @Override
    public Integer convertToDatabaseColumn(E e) {
        return e.getCode();
    }

    @Override
    public E convertToEntityAttribute(Integer code) {
        return (E) commonEnums.stream()
                .filter(commonEnum -> commonEnum.match(String.valueOf(code)))
                .findFirst()
                .orElse(null);
    }
}
