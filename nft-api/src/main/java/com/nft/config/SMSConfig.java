package com.nft.config;

import com.nft.core.sms.AliyunSMSClient;
import com.nft.core.sms.ISmsClient;
import com.nft.core.sms.MockSMSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Configuration
public class SMSConfig {

    @Value("${sms.type:mock}")
    private String type;

    @Bean
    public ISmsClient smsClient() {
        String aliyun="aliyun";
        String mock="mock";
        if (aliyun.equalsIgnoreCase(type)) {
            return new AliyunSMSClient();
        } else if (mock.equalsIgnoreCase(type)) {
            return new MockSMSClient();
        } else {
            return new MockSMSClient();
        }
    }
}
