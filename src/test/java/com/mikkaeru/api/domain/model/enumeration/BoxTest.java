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
    void When_CallMethodNextBox_MUST_ReturnNextBox(Box currentBox, Box expectedBox, int currentBoxNumber) {

        // When
        Box returnedBox = Box.nextBox(currentBox);

        // Then
        Assertions.assertEquals(expectedBox, returnedBox);
        Assertions.assertEquals(currentBoxNumber, currentBox.number);
    }

    @ParameterizedTest()
    @MethodSource("providePreviousBox")
    void When_CallMethodBackOneBox_MUST_BackToPreviousBox(Box currentBox, Box expectedBox, int currentBoxNumber) {

        // When
        Box returnedBox = Box.backOneBox(currentBox);

        // Then
        Assertions.assertEquals(expectedBox, returnedBox);
        Assertions.assertEquals(currentBoxNumber, currentBox.number);
    }

    private static Stream<Arguments> provideNextBox() {
        return Stream.of(
                arguments(BOX_ONE, BOX_TWO, 1),
                arguments(BOX_TWO, BOX_THREE, 2),
                arguments(BOX_THREE, BOX_FOUR, 3),
                arguments(BOX_FOUR, BOX_FIVE, 4),
                arguments(BOX_FIVE, BOX_FIVE, 5)
        );
    }

    private static Stream<Arguments> providePreviousBox() {
        return Stream.of(
                arguments(BOX_ONE, BOX_ONE, 1),
                arguments(BOX_TWO, BOX_ONE, 2),
                arguments(BOX_THREE, BOX_TWO, 3),
                arguments(BOX_FOUR, BOX_THREE, 4),
                arguments(BOX_FIVE, BOX_FOUR, 5)
        );
    }
}
