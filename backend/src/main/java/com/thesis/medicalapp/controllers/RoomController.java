package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.models.Room;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.services.RoomService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<Object> getRooms() {
        List<Room> rooms = roomService.getRooms();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(rooms)
        );
    }
    @PostMapping("/rooms")
    public ResponseEntity<Object> saveRoom(@RequestBody Room room) {
        Room roomEntity = roomService.saveRoom(room);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(roomEntity)
        );
    }
}
