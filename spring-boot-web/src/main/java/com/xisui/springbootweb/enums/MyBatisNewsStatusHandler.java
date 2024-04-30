package com.xisui.springbootweb.enums;

import org.apache.ibatis.type.MappedTypes;

@MappedTypes(NewsStatus.class)
public class MyBatisNewsStatusHandler extends CommonEnumTypeHandler<NewsStatus> {
    public MyBatisNewsStatusHandler() {
        super(NewsStatus.values());
    }
}
