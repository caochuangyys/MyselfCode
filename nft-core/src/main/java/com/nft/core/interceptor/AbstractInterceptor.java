package com.nft.core.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public abstract class AbstractInterceptor extends HandlerInterceptorAdapter {

    /**
     * 获取匹配路径
     * @return
     */
    public abstract List<String> getPathPatterns();

    /**
     * 剔除的路径
     * @return
     */
    public List<String> getExcludePathPatterns(){
        return new ArrayList<>();
    }
}
