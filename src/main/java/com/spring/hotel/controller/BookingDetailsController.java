package com.spring.hotel.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.hotel.model.BookingDetails;
import com.spring.hotel.service.BookingDetailsService;

@RestController
public class BookingDetailsController {
	
	@Autowired
	private BookingDetailsService bookServ;
	
	@GetMapping("/bookingDetails")
	public List<BookingDetails> getAllBookingDetails(){

		return bookServ.getAllBookingDetails();
	}

	@GetMapping("/bookingDetails/{bookingID}")
	public ResponseEntity<BookingDetails> getBookingDetailsById(@PathVariable Long bookingID){
		BookingDetails bd = bookServ.getBookingDetailsById(bookingID);
		return ResponseEntity.ok(bd);

	}
	
	@PostMapping("/bookingDetails")
	public ResponseEntity<String> addBookingDetails(@RequestBody BookingDetails bookingDetails) {
		bookServ.addBookingDetails(bookingDetails);
		return ResponseEntity.ok("Data added successfully");
	}
	
	@RequestMapping(method= RequestMethod.PUT, value ="/bookingDetails/{bookingID}")
	public ResponseEntity<String> updateBookingDetails(@PathVariable Long bookingID,@RequestBody BookingDetails bookingDetails) {
		try {
			bookServ.updateBookingDetails(bookingID, bookingDetails);
			return ResponseEntity.ok("Data of Booking ID: " + bookingID + "\nUpdated successfully");
		}catch(NoSuchElementException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking id is not found.");
		}
	}

	@RequestMapping(method=RequestMethod.DELETE , value ="/bookingDetails/{bookingID}")
	public ResponseEntity<String> deleteBookingDetails(@PathVariable Long bookingID) {
		bookServ.deleteBookingDetails(bookingID);
		return ResponseEntity.ok("Data of Booking ID: "+bookingID+"\nDeleted successfully");
	}
}
