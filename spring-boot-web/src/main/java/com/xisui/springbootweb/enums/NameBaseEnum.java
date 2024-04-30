package com.xisui.springbootweb.enums;

public interface NameBaseEnum {
    default String getName(){
        return name();
    }

    String name();
    String getDesc();
}
