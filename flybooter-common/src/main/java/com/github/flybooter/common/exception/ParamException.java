package com.github.flybooter.common.exception;

public class ParamException extends BadRequestException {

    private static final long serialVersionUID = 1L;
    private static final String code = "invalid_param";

    public ParamException(String errorMessage) {
        super(code, errorMessage);
    }

}
