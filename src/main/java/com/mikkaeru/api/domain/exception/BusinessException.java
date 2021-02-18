package com.mikkaeru.api.domain.exception;

import com.mikkaeru.api.domain.exception.enumeration.Error;

import java.util.List;

public abstract class BusinessException extends RuntimeException {

    private final int code;
    private final List<String> fields;

    public BusinessException(Error error) {
        super();
        code = error.getCode();
        fields = error.getFields();
    }

    public int getCode() {
        return code;
    }

    public List<String> getFields() {
        return fields;
    }
}
