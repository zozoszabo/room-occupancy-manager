package com.interview.roomoccupancymanager.service.model;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.javamoney.moneta.Money;
import org.springframework.util.Assert;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class CurrentRoomUsage {
    private final int totalRooms;
    private int paidRooms;
    private Money revenue;

    public static CurrentRoomUsage of(final int totalRooms, final CurrencyUnit currencyUnit) {
        return new CurrentRoomUsage(totalRooms, 0, Money.zero(currencyUnit));
    }

    public boolean isAnyRoomAvailable() {
        return paidRooms < totalRooms;
    }

    public void payForRoom(final MonetaryAmount amount) {
        Assert.isTrue(isAnyRoomAvailable(), () -> "no available rooms");

        paidRooms++;
        revenue = revenue.add(amount);
    }
}
