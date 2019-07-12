package com.github.flybooter.common.exception;

public class UnAuthorizedException extends CustomException {

    public UnAuthorizedException(String errorMessage) {
        super(errorMessage);
    }

    public UnAuthorizedException(String errorMessage, Throwable t) {
        super(errorMessage, t);
    }

}
