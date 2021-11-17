package com.nft.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "esign.config")
public class EsignConfig {

    private String host;

    private String appId;

    private String appSecret;

    private String notifyUrl;

    private String contractTemplateId;

    private String appScheme;
}
