package com.interview.roomoccupancymanager.model.dto.output;

import com.interview.roomoccupancymanager.service.model.CurrentRoomUsage;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
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

        public static Usage of(@NonNull final CurrentRoomUsage currentRoomUsage) {
            return builder()
                    .available(currentRoomUsage.getTotalRooms() - currentRoomUsage.getPaidRooms())
                    .used(currentRoomUsage.getPaidRooms())
                    .income(currentRoomUsage.getRevenue())
                    .build();
        }
    }
}
