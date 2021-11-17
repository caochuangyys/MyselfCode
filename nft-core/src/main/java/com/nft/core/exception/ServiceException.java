package com.nft.core.exception;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public class ServiceException extends AbstractException {

    public ServiceException(ExceptionCode exceptionCode, String... args) {
        super(exceptionCode, args);
    }
}
