package com.nft.core.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nft.core.annotation.IgnoreResponse;
import com.nft.core.model.response.GatewayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Slf4j
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {

    /**
     * 不需要增强的路径
     */
    public static final String[] FILTER_PATH_ARRAY = new String[]{
            ".*swagger.*",
            ".*api-docs.*"
    };

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if (haveOriginResponseAnnotation(methodParameter)) {
            return false;
        }
        return true;
    }

    private Boolean haveOriginResponseAnnotation(MethodParameter methodParameter) {
        Method method = methodParameter.getMethod();
        IgnoreResponse annotation = method.getAnnotation(IgnoreResponse.class);
        return Objects.nonNull(annotation);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.info("请求路径：{}", serverHttpRequest.getURI().getPath());
        String path = serverHttpRequest.getURI().getPath();
        if (isFilter(path)) {
            log.info("请求接口返回 model: {}", JSONObject.toJSONString(o));
            return o;
        }
        if (o instanceof GatewayResponse) {
            log.info("请求接口返回 model: {}", JSONObject.toJSONString(o));
            return o;
        }
        GatewayResponse response = new GatewayResponse();
        response.setErrno(200);
        response.setTimestamp(System.currentTimeMillis());
        response.setErrmsg("成功");
        response.setData(o);

        log.info("请求接口返回 model: {}", JSONObject.toJSONString(response));

        if (o instanceof String) {
            //特殊处理
            return JSON.toJSON(response).toString();
        } else {
            return response;
        }
    }

    /**
     * 过滤不需要拦截的方法
     *
     * @param path
     * @return
     */
    private Boolean isFilter(String path) {
        for (String s : FILTER_PATH_ARRAY) {
            if (path.matches(s)) {
                return true;
            }
        }
        return false;
    }
}
