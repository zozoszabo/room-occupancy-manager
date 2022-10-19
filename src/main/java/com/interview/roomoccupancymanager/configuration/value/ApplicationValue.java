package com.interview.roomoccupancymanager.configuration.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("room-occupancy-manager.application-value")
public class ApplicationValue {
    private final Money premiumLimit;
}
