package com.interview.roomoccupancymanager.service.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MoneyConvertersTest {
    @MethodSource("provideConvertToMoneyData")
    @ParameterizedTest(name = "[{index}] Numbers ''{1}'' converted to  ''{0}'' ==> ''{2}''")
    void shouldConvertToMoney(final CurrencyUnit currencyUnit, final Collection<Integer> numbers, final Collection<Money> expected) {
        final Collection<Money> actual = MoneyConverters.convert(currencyUnit, numbers);
        Assertions.assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    static Stream<Arguments> provideConvertToMoneyData() {
        return Stream.of(
                Arguments.of(
                        currency("EUR"),
                        List.of(1,2),
                        List.of(money(1, "EUR"), money(2, "EUR"))
                ),
                Arguments.of(
                        currency("HUF"),
                        List.of(1,2),
                        List.of(money(1, "HUF"), money(2, "HUF"))
                )
        );
    }

    static Money money(final int number, final String currencyCode) {
        return Money.of(number, currency(currencyCode));
    }

    static CurrencyUnit currency(final String currencyCode) {
        return Monetary.getCurrency(currencyCode);
    }

}
