package com.qiyei.common.enums;

/**
 * @Desc: 是否 枚举
 */
public enum BooleanEnum {
    NO(0, "否"),
    YES(1, "是");

    public final Integer type;
    public final String value;

    BooleanEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
