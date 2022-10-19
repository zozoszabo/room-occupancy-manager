package com.interview.roomoccupancymanager.service.converter;

import java.util.Collection;

import com.interview.roomoccupancymanager.configuration.value.ApplicationValue;
import com.interview.roomoccupancymanager.service.util.MoneyConverters;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MoneyConverter {
    private final ApplicationValue applicationValue;

    public <T extends Number> Collection<Money> convertToGlobalCurrency(final Collection<T> numbers) {
        return MoneyConverters.convert(applicationValue.getCurrencyUnit(), numbers);
    }

}
