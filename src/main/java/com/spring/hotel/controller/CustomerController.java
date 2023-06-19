package com.spring.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.hotel.model.Customer;
import com.spring.hotel.service.CustomerService;



@RestController
public class CustomerController {
	@Autowired
	private CustomerService custServ;

	@RequestMapping("/custDetails")
	public List<Customer> getAllCustomer(){
		List<Customer> customers = custServ.getAllCustomer();
		return customers;
	}

	@PostMapping("/custDetails/{bookingID}")
	public ResponseEntity<String> addCustomer(@PathVariable Long bookingID, @RequestBody Customer custDetails) {
		custServ.addCustomer(bookingID, custDetails);
		return ResponseEntity.ok("Data added successfully");
	}

	@PutMapping("/custDetails/{customerID}/{bookingID}")
	public ResponseEntity<String> updateCustomer(@PathVariable Long customerID, @PathVariable Long bookingID, @RequestBody Customer custDetails) {
		custServ.updateCustomer(customerID, bookingID, custDetails);
		return ResponseEntity.ok("Data updated successfully");
	}

	@DeleteMapping("/custDetails/{customerID}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long customerID) {
		custServ.deleteCustomer(customerID);
		return ResponseEntity.ok("Data deleted successfully");
	}

}
