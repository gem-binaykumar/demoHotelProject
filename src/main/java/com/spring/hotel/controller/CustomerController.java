package com.spring.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.hotel.model.Customer;
import com.spring.hotel.service.CustomerService;


@RestController
public class CustomerController {
	@Autowired
	private CustomerService custServ;
	
	@RequestMapping("/custDetails")
	public List<Customer> getAllCustomer(){

		return custServ.getAllCustomer();
	}
	
	@RequestMapping(method= RequestMethod.POST, value ="/custDetails")
	public void addCustomer(@RequestBody Customer custDetails) {

		custServ.addCustomer(custDetails);
	}
	
	@RequestMapping(method= RequestMethod.PUT, value ="/custDetails/{customerID}")
	public void updateCustomer(@PathVariable Long customerID,@RequestBody Customer custDetails) {

		custServ.updateCustomer(customerID,custDetails);
	}
	
	@RequestMapping(method=RequestMethod.DELETE , value ="/custDetails/{customerID}")
	public void deleteCustomer(@PathVariable Long customerID) {
		custServ.deleteCustomer(customerID);
	}
}
