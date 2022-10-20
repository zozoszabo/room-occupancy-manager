package com.interview.roomoccupancymanager.controller.api;

import static javax.servlet.http.HttpServletResponse.SC_OK;

import com.interview.roomoccupancymanager.model.dto.input.AvailableRooms;
import com.interview.roomoccupancymanager.model.dto.output.RoomUsage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "Room Occupancy Controller")
@RequestMapping("/api/room-occupancy")
public interface RoomOccupancyController {
    @ApiOperation("Calculates room occupancy")
    @ApiResponses(@ApiResponse(code = SC_OK, message = "Calculated room occupancy", response = RoomUsage.class))
    @PostMapping(value = "/usage", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoomUsage> calculateUsage(@RequestBody AvailableRooms availableRooms);
}
