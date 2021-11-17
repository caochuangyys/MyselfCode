package com.nft.config;

import cn.hutool.core.collection.CollectionUtil;
import com.nft.core.interceptor.AbstractInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Configuration
public class SpringWebConfig implements WebMvcConfigurer {

    @Autowired
    private List<AbstractInterceptor> interceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        interceptors.forEach(item -> {
            InterceptorRegistration ir = registry.addInterceptor(item).addPathPatterns(item.getPathPatterns());
            if (CollectionUtil.isNotEmpty(item.getExcludePathPatterns())) {
                ir.excludePathPatterns(item.getExcludePathPatterns());
            }
        });

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true).maxAge(3600);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {

        return new MethodValidationPostProcessor();
    }
}
