package com.interview.roomoccupancymanager.controller;

import com.interview.roomoccupancymanager.controller.api.RoomOccupancyController;
import com.interview.roomoccupancymanager.model.dto.input.AvailableRooms;
import com.interview.roomoccupancymanager.model.dto.output.RoomUsage;
import com.interview.roomoccupancymanager.service.RoomOccupancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoomOccupancyControllerImpl implements RoomOccupancyController {
    private final RoomOccupancyService roomOccupancyService;

    @Override
    public ResponseEntity<RoomUsage> calculateUsage(@RequestBody final AvailableRooms availableRooms) {
        return ResponseEntity.ok(roomOccupancyService.calculateUsage(availableRooms));
    }

}
