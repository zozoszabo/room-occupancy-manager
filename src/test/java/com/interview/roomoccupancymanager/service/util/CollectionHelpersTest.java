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

    private static Stream<Arguments> provideReverseData() {
        return Stream.of(
                Arguments.of(List.of("A", "B", "C"), List.of("C", "B", "A")),
                Arguments.of(List.of(1, 2 ,3), List.of(3, 2, 1))
        );
    }
}

