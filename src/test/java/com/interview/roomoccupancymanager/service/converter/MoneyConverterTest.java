package com.interview.roomoccupancymanager.service.converter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import javax.money.Monetary;

import com.interview.roomoccupancymanager.configuration.value.ApplicationValue;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MoneyConverterTest {
    @Mock ApplicationValue applicationValueMock;

    @InjectMocks MoneyConverter objectUnderTest;

    @Test
    void shouldConvertToMoney() {
        when(applicationValueMock.getCurrencyUnit()).thenReturn(Monetary.getCurrency("EUR"));

        final Collection<Money> actual = objectUnderTest.convertToGlobalCurrency(List.of(1, 2));

        verify(applicationValueMock).getCurrencyUnit();

        Assertions.assertThat(actual).containsExactly(
                Money.of(1,Monetary.getCurrency("EUR")),
                Money.of(2,Monetary.getCurrency("EUR"))
        );
    }
}
