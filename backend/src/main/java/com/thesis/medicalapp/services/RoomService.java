package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.Room;

import java.util.List;

public interface RoomService {
    List<Room> getRooms();
    Room saveRoom(Room room);
}
