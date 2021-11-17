package com.nft.core.exception;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public class SystemException extends AbstractException {

    public SystemException(ExceptionCode exceptionCode, String... args) {
        super(exceptionCode, args);
    }
}