package com.nft.core.exception;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public class AdminException extends AbstractException {

    public AdminException(ExceptionCode exceptionCode, String... args) {
        super(exceptionCode, args);
    }
}