package com.nft.data.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Getter
public enum GenderEnum {

    /**
     * 性别
     */
    FEMALE(0, "女"),
    MALE(1, "男");

    private int id;
    private String name;

    GenderEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Map<Integer, GenderEnum> toMap() {
        return Arrays.stream(values()).collect(Collectors.toMap(GenderEnum::getId, Function.identity(), (k, v) -> v));
    }

    public static Map<Integer, String> toValueMap() {
        return Arrays.stream(values()).collect(Collectors.toMap(GenderEnum::getId, GenderEnum::getName));
    }
}
