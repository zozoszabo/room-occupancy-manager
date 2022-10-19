package com.interview.roomoccupancymanager.model.dto.input;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class AvailableRooms {
    private final Integer availablePremiumRooms;
    private final Integer availableEconomyRooms;
}
