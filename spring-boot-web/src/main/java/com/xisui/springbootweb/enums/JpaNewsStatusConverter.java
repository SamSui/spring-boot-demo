package com.xisui.springbootweb.enums;

public class JpaNewsStatusConverter extends CommonEnumAttributeConverter<NewsStatus> {
    public JpaNewsStatusConverter() {
        super(NewsStatus.values());
    }
}
