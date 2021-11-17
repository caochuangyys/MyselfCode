package com.nft.core.config;

import com.aliyun.oss.OSSClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author caoc
 * @createDate 2021/11/12
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class OSSClientConfig {

    private String accessId;
    private String accessKey;
    private String endpoint;
    private String bucket;
    private String dir;
    private String callbackUrl;
    private String baseUrl;

    @Bean
    public OSSClient ossClient() {
        return new OSSClient(endpoint, accessId, accessKey);
    }

}

