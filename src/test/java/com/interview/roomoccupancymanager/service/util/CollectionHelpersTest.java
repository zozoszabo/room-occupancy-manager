package com.interview.roomoccupancymanager.service.util;

import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CollectionHelpersTest {
    @MethodSource("provideReverseData")
    @ParameterizedTest(name = "[{index}] reverse(''{0}'') ==>  ''{1}''")
    <E extends Comparable<E>> void shouldReverse(final List<E> input, final List<? extends E> expected) {
        final List<E> actual = CollectionHelpers.reverse(input);
        Assertions.assertThat(actual).containsExactlyElementsOf(expected);
    }

    @MethodSource("provideRemainingElements")
    @ParameterizedTest(name = "[{index}] remainingElements(''{0}'', ''{1}'') ==>  ''{2}''")
    <E> void shouldVerifyRemainingElements(final List<E> collection, final int currentIndex, final int expected) {
        final int actual = CollectionHelpers.remainingElements(collection, currentIndex);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideRemainingElements() {
        final List<Integer> integers = List.of(0, 1, 2, 3);
        return Stream.of(
                Arguments.of(integers, 0, 4),
                Arguments.of(integers, 1, 3),
                Arguments.of(integers, 2, 2),
                Arguments.of(integers, 3, 1),
                Arguments.of(integers, 4, 0)
        );
    }

    private static Stream<Arguments> provideReverseData() {
        return Stream.of(
                Arguments.of(List.of("A", "B", "C"), List.of("C", "B", "A")),
                Arguments.of(List.of(1, 2 ,3), List.of(3, 2, 1))
        );
    }
}

