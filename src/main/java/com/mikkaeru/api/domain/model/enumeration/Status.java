package com.mikkaeru.api.domain.model.enumeration;

public enum Status {

    AGAIN("AGAIN"),
    HARD("HARD"),
    GOOD("GOOD"),
    EASY("EASY");

    String status;

    Status(String status) {
        this.status = status;
    }
}
