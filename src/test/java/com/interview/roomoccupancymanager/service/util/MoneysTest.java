package com.interview.roomoccupancymanager.service.util;

import java.util.stream.Stream;
import javax.money.CurrencySupplier;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MoneysTest {
    @MethodSource("provideHasSameCurrencyData")
    @ParameterizedTest(name = "[{index}] Currency supplier ''{0}'' has same currency unit ''{1}'' ==> ''{2}''")
    void verifyHasSameCurrency(final CurrencySupplier currencySupplier, final CurrencyUnit currencyUnit, final boolean expected) {
        final boolean actual = Moneys.hasSameCurrency(currencySupplier, currencyUnit);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> provideHasSameCurrencyData() {
        return Stream.of(
                Arguments.of(money("EUR"), currency("EUR"), true),
                Arguments.of(money("USD"), currency("EUR"), false)
        );
    }

    static CurrencyUnit currency(final String currencyCode) {
        return Monetary.getCurrency(currencyCode);
    }

    static Money money(final String currencyCode) {
        return Money.of(5, currency(currencyCode));
    }
}
