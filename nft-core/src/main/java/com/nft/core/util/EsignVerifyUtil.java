package com.nft.core.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * @author caoc
 * @createDate 2021/11/12
 *
 * e签宝认证工具
 */
@Slf4j
public class EsignVerifyUtil {

    /**
     * 校验esign签名
     *
     * @param request
     * @param appSecret
     * @return
     * @throws UnsupportedEncodingException
     */
    public static boolean checkSign(HttpServletRequest request, String notifyBody, String appSecret) {
        String signture = request.getHeader("X-Tsign-Open-SIGNATURE");
        //1. 获取时间戳的字节流
        String timestamp = request.getHeader("X-Tsign-Open-TIMESTAMP");
        //2. 获取query请求字符串
        String requestQuery = getRequestQueryStr(request);
        //3、按照规则进行加密
        String signdata = timestamp + requestQuery + notifyBody;

        log.info("signture: {}, timestamp = {}, requestQuery = {}, rbody = {}, signdata = {}", signture, timestamp, requestQuery, notifyBody, signdata);
        String mySignature = getSignature(signdata, appSecret, "HmacSHA256", "UTF-8");
        log.info("加密出来的签名值：----------->>>>>> {}", mySignature);
        log.info("header里面的签名值：---------->>>>>> {}", signture);
        if (mySignature.equals(signture)) {
            log.info("校验通过");
            return true;

        } else {
            log.info("校验失败");
            return false;
        }

    }

    /**
     * 获取query请求字符串
     *
     * @param request
     * @return
     */
    private static String getRequestQueryStr(HttpServletRequest request) {
        //对 Query 参数按照字典对 Key 进行排序后,按照value1+value2方法拼接
        //转换一下数据类型并排序
        List<String> reqList = new ArrayList();
        Enumeration<String> reqEnu = request.getParameterNames();
        while (reqEnu.hasMoreElements()) {
            reqList.add(reqEnu.nextElement());
        }
        Collections.sort(reqList);
        String requestQuery = "";
        for (String key : reqList) {
            String value = request.getParameter(key);
            requestQuery += value == null ? "" : value;
        }
        log.info("获取的query请求字符串是：------》》》 {}", requestQuery);
        return requestQuery;
    }

    /***
     * 获取请求签名值
     *
     * @param data
     *            加密前数据
     * @param key
     *            密钥
     * @param algorithm
     *            HmacMD5 HmacSHA1 HmacSHA256 HmacSHA384 HmacSHA512
     * @param encoding
     *            编码格式
     * @return HMAC加密后16进制字符串
     * @throws Exception
     */
    public static String getSignature(String data, String key, String algorithm, String encoding) {
        Mac mac = null;
        try {
            mac = Mac.getInstance(algorithm);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(encoding), algorithm);
            mac.init(secretKey);
            mac.update(data.getBytes(encoding));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.info("获取Signature签名信息异常：{}", e.getMessage());
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.info("获取Signature签名信息异常：{}", e.getMessage());
            return null;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            log.info("获取Signature签名信息异常：{}", e.getMessage());
            return null;
        }
        return byte2hex(mac.doFinal());
    }

    /***
     * 将byte[]转成16进制字符串
     *
     * @param data
     *
     * @return 16进制字符串
     */
    public static String byte2hex(byte[] data) {
        StringBuilder hash = new StringBuilder();
        String stmp;
        for (int n = 0; data != null && n < data.length; n++) {
            stmp = Integer.toHexString(data[n] & 0XFF);
            if (stmp.length() == 1){
                hash.append('0');
            }
            hash.append(stmp);
        }
        return hash.toString();
    }
}
