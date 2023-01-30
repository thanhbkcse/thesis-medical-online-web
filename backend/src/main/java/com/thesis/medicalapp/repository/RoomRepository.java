package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    Room findRoomById(String id);
}
