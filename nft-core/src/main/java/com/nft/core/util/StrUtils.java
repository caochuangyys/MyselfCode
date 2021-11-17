package com.nft.core.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author caoc
 * @createDate 2021/11/12
 *
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 */
@Slf4j
public class StrUtils extends StringUtils {

    private static final char SEPARATOR = '_';

    private static final String UNKNOWN = "unknown";

    private static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";

    private static final List<String> weeks = Arrays.asList("周一", "周二", "周三", "周四", "周五", "周六", "周日");

    /**
     * 随机名称前缀
     */
    private static final String USERNAME_PREFIX = "dgj_";

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    /**
     * 根据ip获取详细地址
     */
    public static String getCityInfo(String ip) {
        String api = String.format(IP_URL, ip);
        JSONObject object = JSONUtil.parseObj(HttpUtil.get(api));
        return object.get("addr", String.class);
    }


    /**
     * 获得当天是周几
     */
    public static String getWeekDay() {
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 生成accesstoken
     *
     * @return
     */
    public static String generateAccessToken() {
        return (UUID.randomUUID().toString().replace("-", ""));
    }


    /**
     * 生成6位验证码
     *
     * @return
     */
    public static String genSixVerifyCode() {
        String time = System.nanoTime() + "";
        return time.substring(time.length() - 6);
    }

    /**
     * 获取汉字拼音首字母
     *
     * @param str
     * @return
     */
    public static String getPinyinHeader(String str) {

        if (isEmpty(str)) {
            return "";
        }
        StringBuilder convert = new StringBuilder();
        str = str.replaceAll("[^\u4E00-\u9FA5]", "");
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString().toUpperCase();

    }

    /**
     * 获取订单号/流水号
     * @return
     */
    public static String getOrderNo(){
        String orderNo = System.currentTimeMillis() + RandomStringUtils.random(4, false, true);
        return orderNo;
    }

    /**
     * 获取拼音首字母的第一个字母
     *
     * @param str
     * @return
     */
    public static String getPinyinHeaderFirst(String str) {
        if (isEmpty(str)) {
            return "";
        }
        String header = getPinyinHeader(str);
        return substring(header, 0, 1);
    }


    /**
     * md5加密
     *
     * @param src
     * @return
     */
    public static String md5(String src) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(StandardCharsets.UTF_8.encode(src));
        return String.format("%032x", new BigInteger(1, md5.digest()));
    }

    /**
     * 格式化计数单位
     *
     * @param count
     * @return
     */
    public static String formatCountUnit(Integer count) {
        if (count == null || count <= 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#.0");
        if (count < 1000) {
            return String.valueOf(count);
        } else if (count < 10000) {
            BigDecimal result = new BigDecimal(count).divide(new BigDecimal(1000)).setScale(1, BigDecimal.ROUND_DOWN);
            return df.format(result) + "K";
        } else {
            BigDecimal result = new BigDecimal(count).divide(new BigDecimal(10000)).setScale(1, BigDecimal.ROUND_DOWN);
            return df.format(result) + "W";
        }
    }

    /**
     * 获取周几
     *
     * @param localDateTime
     * @return
     */
    public static String getDayOfWeek(LocalDateTime localDateTime) {

        if (localDateTime == null) {
            return "";
        }

        int dayOfWeek = localDateTime.getDayOfWeek().getValue();
        return weeks.get(dayOfWeek - 1);

    }

    /**
     * 比较版本大小
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {

        String[] verList1 = split(version1, ".");
        String[] verList2 = split(version2, ".");
        if (verList1 == null || verList1.length != 3 || verList2 == null || verList2.length != 3) {
            log.error("版本号格式错误：version1 = {}, version2 = {}", verList1, version2);
            return 0;
        }
        for (int i = 0; i < verList1.length; i++) {
            if (verList1[i].equalsIgnoreCase(verList2[i])) {
                continue;//比较下一位
            } else {
                return Integer.parseInt(verList1[i]) > Integer.parseInt(verList2[i]) ? 1 : -1;
            }
        }
        return 0;
    }

    public static String getRandomUsername(){
        String uuid = UUID.randomUUID().toString().replace("-", "");

        return String.format("%s%s", USERNAME_PREFIX, uuid.substring(0, 10));
    }

}
