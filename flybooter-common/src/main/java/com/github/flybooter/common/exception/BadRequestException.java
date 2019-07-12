package com.github.flybooter.common.exception;

public class BadRequestException extends CustomException {

    private static final long serialVersionUID = 1L;


    public BadRequestException(String errCode, String errorMessage) {
        super(errCode, errorMessage);
    }

}
