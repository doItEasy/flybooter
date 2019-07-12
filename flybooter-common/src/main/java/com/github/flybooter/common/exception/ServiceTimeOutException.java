package com.github.flybooter.common.exception;

public class ServiceTimeOutException extends CustomException {

    private static final long serialVersionUID = 1L;

    public ServiceTimeOutException(String errMessage) {
        super(errMessage);

    }

    public ServiceTimeOutException(String errorMessage, Throwable t) {
        super(errorMessage, t);
    }

}
