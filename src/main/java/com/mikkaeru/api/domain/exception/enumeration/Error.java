package com.mikkaeru.api.domain.exception.enumeration;

import java.util.List;

public enum Error {

    INVALID_DUPLICATED_DATA("Dados duplicados!", 1000);

    private final int code;
    private final String message;
    private List<String> fields;

    Error(String message, int code) {
        this.code = code;
        this.message = message;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public int getCode() {
        return code;
    }

    public List<String> getFields() {
        return fields;
    }
}
