package com.nft.core.exception;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public class AppException extends AbstractException {

    public AppException(ExceptionCode exceptionCode, String... args) {
        super(exceptionCode, args);
    }
}