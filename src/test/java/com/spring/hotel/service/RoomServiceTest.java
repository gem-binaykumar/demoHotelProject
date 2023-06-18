package com.spring.hotel.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.spring.hotel.model.Room;
import com.spring.hotel.repository.RoomRepository;
import com.spring.hotel.service.RoomService;

public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private Room createRoom(String roomType, int occupancy, boolean availability, double pricePerDay) {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setOccupancy(occupancy);
        room.setAvailability(availability);
        room.setPricePerDay(pricePerDay);
        return room;
    }

    @Test
    public void testGetAllRooms() {
        List<Room> expectedRooms = new ArrayList<>();
        expectedRooms.add(createRoom("Single", 1, true, 100.0));
        expectedRooms.add(createRoom("Double", 2, true, 150.0));

        when(roomRepository.findAll()).thenReturn(expectedRooms);

        List<Room> actualRooms = roomService.getAllRooms();

        assertEquals(expectedRooms.size(), actualRooms.size());
        assertEquals(expectedRooms, actualRooms);

        verify(roomRepository, times(1)).findAll();
    }

    @Test
    public void testAddRoom() {
        Room room = createRoom("Single", 1, true, 100.0);

        when(roomRepository.save(any(Room.class))).thenReturn(room);

        assertDoesNotThrow(() -> roomService.addRoom(room));

        verify(roomRepository, times(1)).save(room);
    }

    @Test
    public void testUpdateRoom() {
        Long roomId = 1L;
        Room existingRoom = createRoom("Single", 1, true, 100.0);
        Room updatedRoom = createRoom("Double", 2, true, 150.0);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(existingRoom));
        when(roomRepository.save(existingRoom)).thenReturn(updatedRoom);

        assertDoesNotThrow(() -> roomService.updateRoom(roomId, updatedRoom));

        assertEquals("Double", existingRoom.getRoomType());
        assertEquals(2, existingRoom.getOccupancy());
        assertEquals(150.0, existingRoom.getPricePerDay(), 0.0);

        verify(roomRepository, times(1)).findById(roomId);
        verify(roomRepository, times(1)).save(existingRoom);
    }

    @Test
    public void testDeleteRoom() {
        Long roomId = 1L;

        assertDoesNotThrow(() -> roomService.deleteRoom(roomId));

        verify(roomRepository, times(1)).deleteById(roomId);
    }
}

