package com.nft.core.advice;

import com.nft.core.exception.AbstractException;
import com.nft.core.exception.ExceptionCode;
import com.nft.core.model.response.GatewayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({AbstractException.class})
    public GatewayResponse baseException(HttpServletRequest request, AbstractException ex) throws Exception {
        GatewayResponse response = new GatewayResponse();
        response.setErrno(ex.getCode());
        response.setErrmsg(ex.getMsg());
        this.log(request,ex, response);
        return response;
    }

    /**
     * 请求方式不支持异常
     * @param request
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public GatewayResponse methodNotSupportException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) throws Exception {
        GatewayResponse resultSet = new GatewayResponse();
        resultSet.setErrno(ExceptionCode.METHOD_NOT_SUPPORT.getCode());
        resultSet.setErrmsg(ExceptionCode.METHOD_NOT_SUPPORT.getMsg() + " 支持的方法:" + ex.getSupportedHttpMethods());
        this.log(request, ex, resultSet);
        return resultSet;
    }

    /**
     * 参数校验异常 @valid
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GatewayResponse methodArgumentNotValidHandler(HttpServletRequest request, MethodArgumentNotValidException ex){
        GatewayResponse response = new GatewayResponse();
        response.setErrno(ExceptionCode.METHOD_ARGUMENT_VALID_ERR.getCode());
        ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
        response.setErrmsg(objectError.getDefaultMessage());
        this.log(request, ex, response);
        return response;
    }

    /**
     * 参数校验 @Validated
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public GatewayResponse constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException ex){
        GatewayResponse response = new GatewayResponse();
        response.setErrno(ExceptionCode.METHOD_ARGUMENT_VALID_ERR.getCode());
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        while (iterator.hasNext()){
            ConstraintViolation<?> cv1 = iterator.next();
            response.setErrmsg(cv1.getMessageTemplate());
            break;
        }
        this.log(request, ex, response);
        return response;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public GatewayResponse missingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex){
        GatewayResponse response = new GatewayResponse();
        response.setErrno(ExceptionCode.METHOD_ARGUMENT_VALID_ERR.getCode());
        response.setErrmsg("请传递正确的参数：" + ex.getParameterName());
        this.log(request, ex, response);
        return response;
    }


    @ExceptionHandler(BindException.class)
    public GatewayResponse bindExceptionHandler(HttpServletRequest request, BindException ex){
        GatewayResponse response = new GatewayResponse();
        response.setErrno(ExceptionCode.METHOD_ARGUMENT_VALID_ERR.getCode());
        ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
        response.setErrmsg(objectError.getDefaultMessage());
        this.log(request, ex, response);
        return response;
    }


    /**
     * 参数转化
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public GatewayResponse MethodArgumentTypeMismatchExceptionHandler(HttpServletRequest request, MethodArgumentTypeMismatchException ex){
        GatewayResponse response = new GatewayResponse();
        response.setErrno(ExceptionCode.METHOD_ARGUMENT_TYPE_MISSMATCH.getCode());
        if (ex.getRequiredType().isEnum()) {
            Class<? extends Enum> aClass = ex.getRequiredType().asSubclass(Enum.class);
            String enumValues = Stream.of(aClass.getEnumConstants())
                    .map(em -> em.name())
                    .collect(Collectors.joining(","));
            response.setErrmsg(ex.getName() + " 不在枚举范围内，可选值：" + enumValues);
        } else {
            response.setErrmsg(ex.getName() + " 数据格式转化异常");
        }
        this.log(request, ex, response);
        return response;
    }

    @ExceptionHandler(Exception.class)
    public GatewayResponse defaultExceptionHandler(HttpServletRequest request, Exception ex){
        GatewayResponse response = new GatewayResponse();
        response.setErrno(ExceptionCode.SYSTEM_ERR.getCode());
        response.setErrmsg(ExceptionCode.SYSTEM_ERR.getMsg());
        this.log(request, ex, response);
        return response;
    }


    /**
     * 获取错误信息
     *
     * @param
     * @return
     * @author fangzm
     */
    private String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }

    private void log(HttpServletRequest request, Exception ex, GatewayResponse resultSet) {
        String callApi = request.getRequestURI();
        log.error("请求接口异常：{}, 异常信息：{}", callApi, resultSet.getErrmsg());
        log.error(getTrace(ex));
    }
}
