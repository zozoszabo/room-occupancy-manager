package com.interview.roomoccupancymanager.service.model;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CurrentRoomUsageTest {
    @Test
    void shouldCreateInstance() {
        final CurrentRoomUsage actual = CurrentRoomUsage.of(5, euro());

        Assertions.assertThat(actual.getTotalRooms()).isEqualTo(5);
        Assertions.assertThat(actual.getPaidRooms()).isZero();
        Assertions.assertThat(actual.getRevenue()).isEqualTo(money(0));
    }

    @MethodSource("provideIsAnyRoomAvailableData")
    @ParameterizedTest(name = "[{index}] total rooms ''{0}'' paid rooms ''{1}'' ==>  ''{2}''")
    void shouldVerifyIsAnyRoomAvailable(final int totalRooms, final int paidRooms, final boolean expected) {
        final CurrentRoomUsage actual = CurrentRoomUsage.of(totalRooms, euro());
        payForNRooms(actual, paidRooms);

        Assertions.assertThat(actual.isAnyRoomAvailable()).isEqualTo(expected);
    }

    @Nested
    class PayForRoom {

        @Test
        void shouldRaiseExceptionIfRoomIsOverloaded() {
            final CurrentRoomUsage actual = CurrentRoomUsage.of(0, euro());

            Assertions.assertThatThrownBy(() -> actual.payForRoom(money(10)))
                      .isInstanceOf(IllegalArgumentException.class)
                      .hasMessage("no available rooms");
        }

        @Test
        void shouldPayForRoom() {
            final CurrentRoomUsage actual = CurrentRoomUsage.of(5, euro());
            payForNRooms(actual, 3);

            Assertions.assertThat(actual.getPaidRooms()).isEqualTo(3);
            Assertions.assertThat(actual.getRevenue()).isEqualTo(money(30));
        }
    }

    static Stream<Arguments> provideIsAnyRoomAvailableData() {
        return Stream.of(
                Arguments.of(1, 0, true),
                Arguments.of(1, 1, false)
        );
    }

    static void payForNRooms(final CurrentRoomUsage actual, final int paidRooms) {
        IntStream.range(0, paidRooms).forEach(i -> actual.payForRoom(money(10)));
    }

    static Money money(final double number) {
        return Money.of(number, euro());
    }


    static CurrencyUnit euro() {
        return Monetary.getCurrency("EUR");
    }

}
