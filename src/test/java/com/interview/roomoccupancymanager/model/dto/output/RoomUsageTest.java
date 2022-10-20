package com.interview.roomoccupancymanager.model.dto.output;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import com.interview.roomoccupancymanager.model.dto.output.RoomUsage.Usage;
import com.interview.roomoccupancymanager.service.model.CurrentRoomUsage;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

@JsonTest
class RoomUsageTest {
    @Autowired JacksonTester<RoomUsage> jacksonTester;

    @Test
    void verifySerialization() throws Exception {
        final RoomUsage roomUsage = roomUsage(
                usage(10, 5, money(5)),
                usage(15, 10, money(10))
        );

        final JsonContent<RoomUsage> actual = jacksonTester.write(roomUsage);

        Assertions.assertThat(actual).extractingJsonPathNumberValue("$.premiumUsage.available").isEqualTo(10);
        Assertions.assertThat(actual).extractingJsonPathNumberValue("$.premiumUsage.used").isEqualTo(5);
        Assertions.assertThat(actual).extractingJsonPathStringValue("$.premiumUsage.income").isEqualTo("EUR 5.00");
        Assertions.assertThat(actual).extractingJsonPathNumberValue("$.economyUsage.available").isEqualTo(15);
        Assertions.assertThat(actual).extractingJsonPathNumberValue("$.economyUsage.used").isEqualTo(10);
        Assertions.assertThat(actual).extractingJsonPathStringValue("$.economyUsage.income").isEqualTo("EUR 10.00");
    }

    @Test
    void createUsageFromCurrentRoomUsage() {
        final Usage actual = Usage.of(CurrentRoomUsage.of(5, euro()));

        Assertions.assertThat(actual).isEqualTo(usage(5, 0, money(0)));
    }

    static RoomUsage roomUsage(final Usage premiumUsage, final Usage economyUsage) {
        return RoomUsage.builder().premiumUsage(premiumUsage).economyUsage(economyUsage).build();
    }

    static Usage usage(final int available, final int used, final Money money) {
        return Usage.builder().available(available).used(used).income(money).build();
    }

    static CurrencyUnit euro() {
        return Monetary.getCurrency("EUR");
    }

    static Money money(final int number) {
        return Money.of(number, euro());
    }
}
