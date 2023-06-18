package com.spring.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(method = RequestMethod.POST, value = "/custDetails/{bookingID}")
	public void addCustomer(@PathVariable Long bookingID, @RequestBody Customer custDetails) {
		custServ.addCustomer(bookingID, custDetails);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/custDetails/{customerID}/{bookingID}")
	public void updateCustomer(@PathVariable Long customerID, @PathVariable Long bookingID, @RequestBody Customer custDetails) {
		custServ.updateCustomer(customerID, bookingID, custDetails);
	}



	@RequestMapping(method=RequestMethod.DELETE , value ="/custDetails/{customerID}")
	public void deleteCustomer(@PathVariable Long customerID) {
		custServ.deleteCustomer(customerID);
	}
}
