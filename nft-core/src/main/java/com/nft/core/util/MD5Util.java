package com.nft.core.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public class MD5Util {

    /**
     * MD5方法
     *
     * @param text 明文
     * @param key  密钥
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text, String key) {
        //加密后的字符串
        return DigestUtils.md5Hex(text + key);
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param key  密钥
     * @param md5  密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5) {
        //根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if (md5Text.equalsIgnoreCase(md5)) {
            return true;
        }
        return false;
    }

    /**
     * 对字符串md5
     *
     * @param data
     * @return
     */
    public static String md5Str(String data) {

        if (StringUtils.isEmpty(data)){
            return null;
        }

        return DigestUtils.md5Hex(data);
    }

    /**
     * md5文件
     *
     * @param is
     * @return
     */
    public static String md5File(InputStream is) {
        try {
            return DigestUtils.md5Hex(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
