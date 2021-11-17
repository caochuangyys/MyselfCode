package com.nft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author caoc
 * @createDate 2021/11/11
 */
@SpringBootApplication(scanBasePackages = {"com.nft"})
@MapperScan(basePackages = "com.nft.data.mapper")
public class NftAppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NftAppApiApplication.class,args);
    }
}
