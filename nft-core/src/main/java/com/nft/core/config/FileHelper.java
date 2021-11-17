package com.nft.core.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Component
public class FileHelper {

    private static String baseUrl;

    /**
     * @param url 来源 url
     * @return 全路径url
     */
    public static String toFullUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        if (url.contains("http")) {
            return url;
        }
        url = baseUrl + url;
        return url;
    }

    @Value("${aliyun.oss.baseUrl}")
    public void setOssFileBucketUrl(String value) {
        baseUrl = value;
    }
}
