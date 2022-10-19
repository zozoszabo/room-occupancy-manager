package com.interview.roomoccupancymanager.model.dto.input;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
class AvailableRoomsTest {
    @Autowired JacksonTester<AvailableRooms> jacksonTester;

    @Test
    void verifyDeserialization() throws Exception {
        final AvailableRooms actual = jacksonTester.parseObject("{\"availablePremiumRooms\":10,\"availableEconomyRooms\":5}");

        Assertions.assertThat(actual).isEqualTo(availableRooms(5, 10));
    }

    static AvailableRooms availableRooms(final int availableEconomyRooms, final int availablePremiumRooms) {
        return AvailableRooms.builder().availableEconomyRooms(availableEconomyRooms).availablePremiumRooms(availablePremiumRooms).build();
    }
}
