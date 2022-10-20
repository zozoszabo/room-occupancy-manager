package com.interview.roomoccupancymanager.service;

import java.util.Collection;
import java.util.List;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

import com.interview.roomoccupancymanager.configuration.value.ApplicationValue;
import com.interview.roomoccupancymanager.model.dto.input.AvailableRooms;
import com.interview.roomoccupancymanager.model.dto.output.RoomUsage;
import com.interview.roomoccupancymanager.model.dto.output.RoomUsage.Usage;
import com.interview.roomoccupancymanager.service.model.CurrentRoomUsage;
import com.interview.roomoccupancymanager.service.util.CollectionHelpers;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomOccupancyService {
    private final ApplicationValue applicationValue;

    @Setter(onMethod_ = @Autowired) private Collection<Money> guests;

    public RoomUsage calculateUsage(@NonNull final AvailableRooms availableRooms) {
        final CurrencyUnit currencyUnit = applicationValue.getCurrencyUnit();
        final Money premiumLimit = applicationValue.getPremiumLimit();
        final List<MonetaryAmount> reverseGuests = CollectionHelpers.reverse(guests);

        final CurrentRoomUsage premiumRoomUsage = CurrentRoomUsage.of(availableRooms.getAvailablePremiumRooms(), currencyUnit);
        final CurrentRoomUsage economyRoomUsage = CurrentRoomUsage.of(availableRooms.getAvailableEconomyRooms(), currencyUnit);

        for (int index = 0; index < reverseGuests.size(); index++) {
            final MonetaryAmount currentGuest = reverseGuests.get(index);
            if ((premiumRoomUsage.isAnyRoomAvailable() && isPayingPremium(currentGuest, premiumLimit)) ||
                (premiumRoomUsage.isAnyRoomAvailable() && isEconomyOverloaded(economyRoomUsage, reverseGuests, index))) {
                premiumRoomUsage.payForRoom(currentGuest);
            } else if (isNotPayingPremium(currentGuest, premiumLimit) && economyRoomUsage.isAnyRoomAvailable()) {
                economyRoomUsage.payForRoom(currentGuest);
            }
        }

        return roomUsage(premiumRoomUsage, economyRoomUsage);
    }

    private static RoomUsage roomUsage(final CurrentRoomUsage premiumRoomUsage, final CurrentRoomUsage economyRoomUsage) {
        return RoomUsage.builder()
                        .economyUsage(Usage.of(economyRoomUsage))
                        .premiumUsage(Usage.of(premiumRoomUsage))
                        .build();
    }

    private static boolean isEconomyOverloaded(@NonNull final CurrentRoomUsage economyRoomUsage,
                                               final List<MonetaryAmount> reverseGuests,
                                               final int index) {
        return economyRoomUsage.getTotalRooms() < CollectionHelpers.remainingElements(reverseGuests, index);
    }

    private static boolean isNotPayingPremium(final MonetaryAmount current, final MonetaryAmount premiumLimit) {
        return !isPayingPremium(current, premiumLimit);
    }

    private static boolean isPayingPremium(@NonNull final MonetaryAmount current, final MonetaryAmount premiumLimit) {
        return current.isGreaterThanOrEqualTo(premiumLimit);
    }
}
