package com.spring.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.hotel.model.Room;
import com.spring.hotel.service.RoomService;


@RestController
public class RoomController {
	@Autowired
	private RoomService roomServ;
	
	@RequestMapping("/roomDetails")
	public List<Room> getAllRooms(){
		return roomServ.getAllRooms();
	}
	
	@RequestMapping(method= RequestMethod.POST, value ="/roomDetails")
	public void addRoom(@RequestBody Room roomDetails) {

		roomServ.addRoom(roomDetails);
	}
	
	@RequestMapping(method= RequestMethod.PUT, value ="/roomDetails/{roomID}")
	public void updateRoom(@PathVariable Long roomID,@RequestBody Room roomDetails) {
		roomServ.updateRoom(roomID,roomDetails);
	}
	
	@RequestMapping(method=RequestMethod.DELETE , value ="/roomDetails/{roomID}")
	public void deleteRoom(@PathVariable Long roomID) {
		roomServ.deleteRoom(roomID);
	}
}
