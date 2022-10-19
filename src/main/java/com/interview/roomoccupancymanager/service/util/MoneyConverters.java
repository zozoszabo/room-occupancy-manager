package com.interview.roomoccupancymanager.service.util;

import java.util.Collection;
import java.util.stream.Collectors;
import javax.money.CurrencyUnit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.javamoney.moneta.Money;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MoneyConverters {
    public static <T extends Number> Collection<Money> convert(final CurrencyUnit currencyUnit, @NonNull final Collection<T> collection) {
        return collection.stream().map(number -> Money.of(number, currencyUnit)).collect(Collectors.toList());
    }
}
