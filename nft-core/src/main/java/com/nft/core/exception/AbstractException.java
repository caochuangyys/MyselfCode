package com.nft.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractException extends RuntimeException implements Serializable {

    private int code;

    private String msg;

    public AbstractException(ExceptionCode exceptionCode, String... args){
        this.code = exceptionCode.getCode();
        if (args != null && args.length > 0){
            this.msg = MessageFormat.format(exceptionCode.getMsg(), args);
        }else{
            this.msg = exceptionCode.getMsg();
        }

    }

}
