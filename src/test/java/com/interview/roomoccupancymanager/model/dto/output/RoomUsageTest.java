package com.interview.roomoccupancymanager.model.dto.output;

import com.interview.roomoccupancymanager.model.dto.output.RoomUsage.Usage;
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
                usage(10, 5, euro(5)),
                usage(15, 10, euro(10))
        );

        final JsonContent<RoomUsage> actual = jacksonTester.write(roomUsage);

        Assertions.assertThat(actual).extractingJsonPathNumberValue("$.premiumUsage.available").isEqualTo(10);
        Assertions.assertThat(actual).extractingJsonPathNumberValue("$.premiumUsage.used").isEqualTo(5);
        Assertions.assertThat(actual).extractingJsonPathStringValue("$.premiumUsage.income").isEqualTo("EUR 5.00");
        Assertions.assertThat(actual).extractingJsonPathNumberValue("$.economyUsage.available").isEqualTo(15);
        Assertions.assertThat(actual).extractingJsonPathNumberValue("$.economyUsage.used").isEqualTo(10);
        Assertions.assertThat(actual).extractingJsonPathStringValue("$.economyUsage.income").isEqualTo("EUR 10.00");
    }

    static RoomUsage roomUsage(final Usage premiumUsage, final Usage economyUsage) {
        return RoomUsage.builder().premiumUsage(premiumUsage).economyUsage(economyUsage).build();
    }

    static Usage usage(final int available, final int used, final Money money) {
        return Usage.builder().available(available).used(used).income(money).build();
    }

    static Money euro(final int number) {
        return Money.of(number, "EUR");
    }
}
