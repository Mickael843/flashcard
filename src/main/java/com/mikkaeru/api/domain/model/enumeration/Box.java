package com.mikkaeru.api.domain.model.enumeration;

import org.modelmapper.internal.util.Assert;

public enum Box {

    BOX_ONE(1),
    BOX_TWO(2),
    BOX_THREE(3),
    BOX_FOUR(4),
    BOX_FIVE(5);

    final int number;

    Box(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public static Box nextBox(Box currentBox) {

        return switch (currentBox) {
            case BOX_ONE -> BOX_TWO;
            case BOX_TWO -> BOX_THREE;
            case BOX_THREE -> BOX_FOUR;
            default -> BOX_FIVE;
        };
    }

    public static Box backOneBox(Box currentBox) {

        return switch (currentBox) {
            case BOX_FIVE -> BOX_FOUR;
            case BOX_FOUR -> BOX_THREE;
            case BOX_THREE -> BOX_TWO;
            default -> BOX_ONE;
        };
    }
}
