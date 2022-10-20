package com.interview.roomoccupancymanager.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

import com.interview.roomoccupancymanager.configuration.value.ApplicationValue;
import com.interview.roomoccupancymanager.model.dto.input.AvailableRooms;
import com.interview.roomoccupancymanager.model.dto.output.RoomUsage;
import com.interview.roomoccupancymanager.model.dto.output.RoomUsage.Usage;
import com.interview.roomoccupancymanager.service.util.MoneyConverters;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoomOccupancyServiceTest {
    @Mock ApplicationValue applicationValueMock;

    @InjectMocks RoomOccupancyService objectUnderTest;

    @MethodSource("provideCalculateUsageData")
    @ParameterizedTest(name = "[{index}] Premium limit: ''{0}'' Available rooms: ''{1}'' Guests: ''{2}'' ==> ''{3}''")
    void shouldCalculateUsage(final Money premiumLimit, final AvailableRooms availableRooms, final Collection<Money> guests, final RoomUsage expected) {
        when(applicationValueMock.getCurrencyUnit()).thenReturn(premiumLimit.getCurrency());
        when(applicationValueMock.getPremiumLimit()).thenReturn(premiumLimit);

        objectUnderTest.setGuests(guests);

        final RoomUsage actual = objectUnderTest.calculateUsage(availableRooms);

        verify(applicationValueMock).getCurrencyUnit();
        verify(applicationValueMock).getPremiumLimit();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideCalculateUsageData() {
        return Stream.of(
                Arguments.of( /* Test 1 */
                        premiumLimit(100.0D),
                        availableRooms(3, 3),
                        guests(23.0D, 45.0D, 155.0D, 374.0D, 22.0D, 99.99D, 100.0D, 101.0D, 115.0D, 209.0D),
                        RoomUsage.builder()
                            .economyUsage(usage(0, 3, money(167.99D)))
                            .premiumUsage(usage(0, 3, money(738.0D)))
                            .build()
                ),
                Arguments.of( /* Test 2 */
                        premiumLimit(100.0D),
                        availableRooms(5, 7),
                        guests(23.0D, 45.0D, 155.0D, 374.0D, 22.0D, 99.99D, 100.0D, 101.0D, 115.0D, 209.0D),
                        RoomUsage.builder()
                                 .economyUsage(usage(1, 4, money(189.99D)))
                                 .premiumUsage(usage(1, 6, money(1_054.0D)))
                                 .build()
                ),
                Arguments.of( /* Test 3 */
                        premiumLimit(100.0D),
                        availableRooms(7, 2),
                        guests(23.0D, 45.0D, 155.0D, 374.0D, 22.0D, 99.99D, 100.0D, 101.0D, 115.0D, 209.0D),
                        RoomUsage.builder()
                                 .economyUsage(usage(3, 4, money(189.99D)))
                                 .premiumUsage(usage(0, 2, money(583.0D)))
                                 .build()
                ),
                Arguments.of( /* Test 4 */
                        premiumLimit(100.0D),
                        availableRooms(1, 7),
                        guests(23.0D, 45.0D, 155.0D, 374.0D, 22.0D, 99.99D, 100.0D, 101.0D, 115.0D, 209.0D),
                        RoomUsage.builder()
                                 .economyUsage(usage(0, 1, money(45.0D)))
                                 .premiumUsage(usage(0, 7, money(1_153.99D)))
                                 .build()
                ),
                Arguments.of( /* When every guest is paying over the limit */
                        premiumLimit(0.5D),
                        availableRooms(5, 5),
                        guests(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D),
                        RoomUsage.builder()
                                 .economyUsage(usage(5, 0, money(0.0D)))
                                 .premiumUsage(usage(0, 5, money(40.0D)))
                                 .build()
                ),
                Arguments.of( /* When every guest is paying less than the limit */
                        premiumLimit(11.0D),
                        availableRooms(5, 5),
                        guests(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D),
                        RoomUsage.builder()
                                 .economyUsage(usage(0, 5, money(15.0D)))
                                 .premiumUsage(usage(0, 5, money(40.0D)))
                                 .build()
                ),
                Arguments.of( /* When economy is overloaded and premium is available then occupy premium */
                        premiumLimit(3.5D),
                        availableRooms(2, 3),
                        guests(1.0D, 2.0D, 3.0D, 4.0D),
                        RoomUsage.builder()
                                 .economyUsage(usage(0, 2, money(3.0D)))
                                 .premiumUsage(usage(1, 2, money(7.0D)))
                                 .build()
                )
        );
    }

    static Collection<Money> guests(final Double... numbers) {
        return MoneyConverters.convert(euro(), List.of(numbers));
    }

    static Money premiumLimit(final double number) {
        return money(number);
    }

    static AvailableRooms availableRooms(final int availableEconomyRooms, final int availablePremiumRooms) {
        return AvailableRooms.builder().availableEconomyRooms(availableEconomyRooms).availablePremiumRooms(availablePremiumRooms).build();
    }

    static Usage usage(final int available, final int used, final Money money) {
        return Usage.builder().available(available).used(used).income(money).build();
    }

    static Money money(final double number) {
        return Money.of(number, euro());
    }

    static CurrencyUnit euro() {
        return Monetary.getCurrency("EUR");
    }
}
