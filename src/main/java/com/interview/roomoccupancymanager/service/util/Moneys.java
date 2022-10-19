package com.interview.roomoccupancymanager.service.util;

import javax.money.CurrencySupplier;
import javax.money.CurrencyUnit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Moneys {
    public static boolean hasSameCurrency(@NonNull final CurrencySupplier currencySupplier, final CurrencyUnit currencyUnit) {
        return currencySupplier.getCurrency().equals(currencyUnit);
    }
}
