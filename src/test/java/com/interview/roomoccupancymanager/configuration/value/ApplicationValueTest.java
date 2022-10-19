package com.interview.roomoccupancymanager.configuration.value;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

class ApplicationValueTest {
    @Test
    void shouldFailOnInstantiationIfNotTheSameCurrency() {
        Assertions.assertThatThrownBy(() -> new ApplicationValue(currency("USD"), money("EUR")))
                  .isInstanceOf(IllegalArgumentException.class)
                  .hasMessage("premium limit has to have '%s' currency", currency("USD"));
    }

    static CurrencyUnit currency(final String EUR) {
        return Monetary.getCurrency(EUR);
    }

    static Money money(final String currencyCode) {
        return Money.of(5, currency(currencyCode));
    }
}
