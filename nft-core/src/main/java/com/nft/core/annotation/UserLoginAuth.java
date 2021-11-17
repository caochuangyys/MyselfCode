package com.nft.core.annotation;

import java.lang.annotation.*;

/**
 * @author caoc
 * @createDate 2021/11/12
 * 用户登录认证
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserLoginAuth {
}
