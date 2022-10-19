package com.interview.roomoccupancymanager.model.dto.output;

import lombok.Builder;
import lombok.Data;
import org.javamoney.moneta.Money;

@Data
@Builder
public class RoomUsage {
    private final Usage premiumUsage;
    private final Usage economyUsage;

    @Data
    @Builder
    public static final class Usage {
        private final Integer available;
        private final Integer used;
        private final Money income;
    }
}
