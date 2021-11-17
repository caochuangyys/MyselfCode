package com.nft.core.util;

import java.math.BigDecimal;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public class NumberUtils {

    /**
     * 将数字金额转换为以万为结尾的字符串 小于10000的不转换
     *
     * @param amount
     * @return
     */
    public static String amountConversion(BigDecimal amount) {
        Double defaultValue = 10000.00;
        if (amount.doubleValue() >= defaultValue) {
            BigDecimal tempValue = amount.divide(new BigDecimal(10000)).setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros();
            return tempValue.toPlainString() + "万";
        } else {
            return amount.stripTrailingZeros().toPlainString();
        }
    }
}
