package com.nft.core.annotation;

import java.lang.annotation.*;

/**
 * @author caoc
 * @createDate 2021/11/12
 * 标记方法不需要封装统一响应结果 #{@link}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreResponse {
}
