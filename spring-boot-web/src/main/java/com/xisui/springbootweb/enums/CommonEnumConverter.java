//package com.xisui.springbootweb.enums;
//
//import com.fasterxml.jackson.core.JacksonException;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.TypeDescriptor;
//import org.springframework.core.convert.converter.ConditionalGenericConverter;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component
//public class CommonEnumConverter implements ConditionalGenericConverter {
//    @Autowired
//    private CommonEnumRegistry enumRegistry;
//
//    @Override
//    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
//        Class<?> type = targetType.getType();
//        return enumRegistry.getClassDict().containsKey(type);
//    }
//
//    @Override
//    public Set<ConvertiblePair> getConvertibleTypes() {
//        return enumRegistry.getClassDict().keySet().stream()
//                .map(cls -> new ConvertiblePair(String.class, cls))
//                .collect(Collectors.toSet());
//    }
//
//    @Override
//    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
//        String value = (String) source;
//        List<CommonEnum> commonEnums = this.enumRegistry.getClassDict().get(targetType.getType());
//        return commonEnums.stream()
//                .filter(commonEnum -> commonEnum.match(value))
//                .findFirst()
//                .orElse(null);
//    }
//}
//
//static class CommonEnumJsonDeserializer extends JsonDeserializer {
//        private final List<CommonEnum> commonEnums;
//
//        CommonEnumJsonDeserializer(List<CommonEnum> commonEnums) {
//            this.commonEnums = commonEnums;
//        }
//
//        @Override
//        public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
//            String value = jsonParser.readValueAs(String.class);
//            return commonEnums.stream()
//                    .filter(commonEnum -> commonEnum.match(value))
//                    .findFirst()
//                    .orElse(null);
//        }
//    }
