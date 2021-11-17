package com.nft.core.util;

import cn.hutool.core.collection.CollectionUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public class MbtiUtils {

    public final static List<String> LETTER_LIST = Arrays.asList("E", "I", "S", "N", "T", "F", "J", "P");


    /**
     * 计算测试结果
     *
     * @param data
     * @return
     */
    public static String calculateResult(Map<String, Integer> data) {

        if (CollectionUtil.isEmpty(data)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 7; i = i + 2) {
            String keyA = LETTER_LIST.get(i);
            String keyB = LETTER_LIST.get(i + 1);
            int countA = data.get(keyA) == null ? 0 : data.get(keyA);
            int countB = data.get(keyB) == null ? 0 : data.get(keyB);
            if (countA > countB) {
                sb.append(keyA);
            } else {
                sb.append(keyB);
            }
        }

        return sb.toString();

    }
}
