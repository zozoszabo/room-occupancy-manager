package com.interview.roomoccupancymanager.application;

import java.net.URI;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

import com.interview.roomoccupancymanager.model.dto.input.AvailableRooms;
import com.interview.roomoccupancymanager.model.dto.output.RoomUsage;
import com.interview.roomoccupancymanager.model.dto.output.RoomUsage.Usage;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
class ApplicationTests {
    @Autowired TestRestTemplate testRestTemplate;

    @Test
    void shouldCalculateUsage() {
        final ResponseEntity<RoomUsage> response = testRestTemplate.postForEntity(
                URI.create("/api/room-occupancy/usage"),
                availableRooms(3, 3),
                RoomUsage.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).satisfies(roomUsage -> {
            Assertions.assertThat(roomUsage.getPremiumUsage()).as("premiumUsage").isEqualTo(usage(0, 3, money(738.0D)));
            Assertions.assertThat(roomUsage.getEconomyUsage()).as("economyUsage").isEqualTo(usage(0, 3, money(167.99D)));
        });
    }

    static Money money(final double number) {
        return Money.of(number, euro());
    }

    static CurrencyUnit euro() {
        return Monetary.getCurrency("EUR");
    }

    static Usage usage(final int available, final int used, final Money money) {
        return Usage.builder().available(available).used(used).income(money).build();
    }

    static AvailableRooms availableRooms(final int availableEconomyRooms, final int availablePremiumRooms) {
        return AvailableRooms.builder().availableEconomyRooms(availableEconomyRooms).availablePremiumRooms(availablePremiumRooms).build();
    }

}
