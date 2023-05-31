package com.spring.hotel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.hotel.model.Room;
import com.spring.hotel.repository.RoomRepository;

@Service
public class RoomService {
	
	@Autowired
	public RoomRepository roomRepo;
	
	public List<Room> getAllRooms(){
		List<Room> roomDetail = new ArrayList<>();
		roomRepo.findAll().forEach(roomDetail::add);
		return roomDetail;
	}
	
	public void addRoom(Room roomDetails) {
		roomRepo.save(roomDetails); 
		
	}

	public void updateRoom(Long id, Room roomDetails) {
//		Room existingRoom = roomRepo.findById(id).orElse(null);
//
//		if (existingRoom != null) {
//			existingRoom.setRoomType(roomDetails.getRoomType());
//			existingRoom.setOccupancy(roomDetails.getOccupancy());
//			existingRoom.setAvailability(roomDetails.getAvailability());
//			existingRoom.setPricePerDay(roomDetails.getPricePerDay());
//			existingRoom.setCheckedIn(roomDetails.isCheckedIn());
//			existingRoom.setCheckedOut(roomDetails.isCheckedOut());
//			existingRoom.setBookingDetails(roomDetails.getBookingDetails()); // Preserve the bookingID association
//
//			roomRepo.save(existingRoom);
//		}
		Room existingRoom = roomRepo.findById(id).orElse(null);

		if (existingRoom != null) {
			// Preserve the existing IDs
			Long roomID = existingRoom.getRoomID();
			Long bookingID = existingRoom.getBookingDetails().getBookingID();

			// Update the room details
			existingRoom.setRoomType(roomDetails.getRoomType());
			existingRoom.setOccupancy(roomDetails.getOccupancy());
			existingRoom.setAvailability(roomDetails.getAvailability());
			existingRoom.setPricePerDay(roomDetails.getPricePerDay());
			existingRoom.setCheckedIn(roomDetails.isCheckedIn());
			existingRoom.setCheckedOut(roomDetails.isCheckedOut());

			// Restore the existing IDs
			existingRoom.setBookingDetails(roomDetails.getBookingDetails());

			roomRepo.save(existingRoom);
		}
		
	}

	public void deleteRoom(Long id) {
		roomRepo.deleteById(id);
	}	
}
