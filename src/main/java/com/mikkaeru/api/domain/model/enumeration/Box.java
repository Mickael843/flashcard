package com.mikkaeru.api.domain.model.enumeration;

import lombok.Getter;

@Getter
public enum Box {

    ONE("Box one"),
    TWO("Box two"),
    THREE("Box three"),
    FOUR("Box four"),
    FIVE("Box five");

    String value;

    Box(String value) {
        this.value = value;
    }

    public static Box nextBox(Box currentBox) {

        return switch (currentBox) {
            case ONE -> TWO;
            case TWO -> THREE;
            case THREE -> FOUR;
            default -> FIVE;
        };
    }

    public static Box backOneBox(Box currentBox) {

        return switch (currentBox) {
            case FIVE -> FOUR;
            case FOUR -> THREE;
            case THREE -> TWO;
            default -> ONE;
        };
    }
}
