package com.interview.roomoccupancymanager.configuration.value;

import javax.money.CurrencyUnit;

import lombok.Getter;
import lombok.NonNull;
import org.javamoney.moneta.Money;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.util.Assert;

@Getter
@ConstructorBinding
@ConfigurationProperties("room-occupancy-manager.application-value")
public class ApplicationValue {
    private final CurrencyUnit currencyUnit;
    private final Money premiumLimit;

    public ApplicationValue(final CurrencyUnit currencyUnit, @NonNull final Money premiumLimit) {
        Assert.isTrue(premiumLimit.getCurrency().equals(currencyUnit), () -> String.format("premium limit has to have '%s' currency", currencyUnit));

        this.currencyUnit = currencyUnit;
        this.premiumLimit = premiumLimit;
    }
}
