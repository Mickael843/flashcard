package com.mikkaeru.api.domain.model.enumeration;

import com.mikkaeru.api.helper.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.mikkaeru.api.domain.model.enumeration.Box.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BoxTest extends TestHelper {

    @ParameterizedTest()
    @MethodSource("provideNextBox")
    void When_CallMethodNextBox_MUST_ReturnNextBox(Box currentBox, Box expectedBox, String currentBoxValue) {

        // When
        Box returnedBox = Box.nextBox(currentBox);

        // Then
        Assertions.assertEquals(expectedBox, returnedBox);
        Assertions.assertEquals(currentBoxValue, currentBox.value);
    }

    @ParameterizedTest()
    @MethodSource("providePreviousBox")
    void When_CallMethodBackOneBox_MUST_BackToPreviousBox(Box currentBox, Box expectedBox, String currentBoxValue) {

        // When
        Box returnedBox = Box.backOneBox(currentBox);

        // Then
        Assertions.assertEquals(expectedBox, returnedBox);
        Assertions.assertEquals(currentBoxValue, currentBox.value);
    }

    private static Stream<Arguments> provideNextBox() {
        return Stream.of(
                arguments(ONE, TWO, "Box one"),
                arguments(TWO, THREE, "Box two"),
                arguments(THREE, FOUR, "Box three"),
                arguments(FOUR, FIVE, "Box four"),
                arguments(FIVE, FIVE, "Box five")
        );
    }

    private static Stream<Arguments> providePreviousBox() {
        return Stream.of(
                arguments(ONE, ONE, "Box one"),
                arguments(TWO, ONE, "Box two"),
                arguments(THREE, TWO, "Box three"),
                arguments(FOUR, THREE, "Box four"),
                arguments(FIVE, FOUR, "Box five")
        );
    }
}
