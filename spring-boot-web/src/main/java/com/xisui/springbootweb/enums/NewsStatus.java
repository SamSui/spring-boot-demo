package com.xisui.springbootweb.enums;

public enum NewsStatus implements CommonEnum {
    DELETE(1, "删除"),
    ONLINE(10, "上线"),
    OFFLINE(20, "下线");
    private final int code;
    private final String desc;

    NewsStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public boolean match(String s) {
        for (NewsStatus status : NewsStatus.values()) {
            if(String.valueOf(status.getCode()).equals(s)){
                return true;
            }
        }

        return false;
    }
}
