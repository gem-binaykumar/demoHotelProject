package com.spring.hotel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.hotel.model.Customer;
import com.spring.hotel.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	public CustomerRepository customerRepo;
	
	public List<Customer> getAllCustomer(){
		List<Customer> custDetail = new ArrayList<>();
		customerRepo.findAll().forEach(custDetail::add);
		return custDetail;
	}
	
	public void addCustomer(Customer custDetails) {
		if (custDetails.getAge() < 18) {
			// Check if there is at least one adult associated with the minor customer
			boolean hasAdult = custDetails.getBookingDetails().getCust().stream()
					.filter(customer -> customer.getAge() >= 18)
					.findAny()
					.isPresent();
			if (!hasAdult) {
				// No adult associated with the minor, so do not add the customer
				throw new IllegalArgumentException("Cannot add a minor customer without an associated adult.");
			}
		}

		customerRepo.save(custDetails);
		
	}

	public void updateCustomer(Long id, Customer custDetails) {
		Optional<Customer> existingCustomerOptional = customerRepo.findById(id);
		if (existingCustomerOptional.isPresent()) {
			Customer existingCustomer = existingCustomerOptional.get();

			if (custDetails.getAge() < 18) {
				// Check if there is at least one adult associated with the minor customer
				boolean hasAdult = existingCustomer.getBookingDetails().getCust().stream()
						.filter(customer -> customer.getAge() >= 18)
						.findAny()
						.isPresent();
				if (!hasAdult) {
					// No adult associated with the minor, so do not update
					return;
				}
			}



			// Update the specific fields of the existing customer
			existingCustomer.setFullName(custDetails.getFullName());
			existingCustomer.setAddress(custDetails.getAddress());
			existingCustomer.setContactNumber(custDetails.getContactNumber());
			existingCustomer.setAge(custDetails.getAge());




			// Preserve the bookingID association
			existingCustomer.setBookingDetails(custDetails.getBookingDetails());
			// Save the updated customer
			customerRepo.save(existingCustomer);
		}
	}


	public void deleteCustomer(Long id) {
		customerRepo.deleteById(id);
	}	
}
