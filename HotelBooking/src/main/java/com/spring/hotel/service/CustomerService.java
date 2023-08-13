package com.spring.hotel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import com.spring.hotel.repository.BookingDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.hotel.model.BookingDetails;
import com.spring.hotel.model.Customer;
import com.spring.hotel.repository.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;



@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private BookingDetailsRepository bookingDetailsRepo;

	private static final Logger logger = LoggerFactory.getLogger(BookingDetailsService.class);


	public List<Customer> getAllCustomer() {
		logger.info("Getting all customer details");
		return new ArrayList<>(customerRepo.findAll());
	}

	// checks if Booking ID exists or not
	public BookingDetails checkIfBdExist(Long bookingID){
		logger.info("Checking if Booking Details exists by its id:{}",bookingID);
		return bookingDetailsRepo.findById(bookingID)
				.orElseThrow(() -> new NoSuchElementException("Booking ID does not exist."));
	}

	public void addCustomer(Long bookingID, Customer custDetails) {
		logger.info("Adding Customer by its  Booking id:{}",bookingID);
		BookingDetails bookingDetails = checkIfBdExist(bookingID);
		if (custDetails.getAge() < 18) {
			boolean hasAdult = bookingDetails.getCust().stream().anyMatch(c -> c.getAge() >= 18);
			if (!hasAdult) {
				throw new IllegalArgumentException("Cannot add a booking without an associated adult customer.");
			}
		}

		custDetails.setBookingDetails(bookingDetails);
		customerRepo.save(custDetails);
	}


	public void updateCustomer(Long customerID, Long bookingID, Customer custDetails) {
		logger.info("Updating Customer by its  Booking id:{} and Customer id:{}",bookingID,customerID);
		BookingDetails bookingDetails = checkIfBdExist(bookingID);
		Optional<Customer> existingCustomerOptional = customerRepo.findById(customerID);
		if (existingCustomerOptional.isPresent()) {
			Customer existingCustomer = existingCustomerOptional.get();
			bookingDetails = existingCustomer.getBookingDetails();

			// Check if the customer is a minor
			if (custDetails.getAge() < 18) {
				boolean hasAdult = bookingDetails.getCust().stream().anyMatch(c -> c.getAge() >= 18 && !c.getCustomerID().equals(customerID));
				if (!hasAdult) {
					throw new IllegalArgumentException("Cannot update a minor customer without an associated adult.");
				}
			}
			// Update the specific fields of the existing customer
			existingCustomer.setFullName(custDetails.getFullName());
			existingCustomer.setAddress(custDetails.getAddress());
			existingCustomer.setContactNumber(custDetails.getContactNumber());
			existingCustomer.setAge(custDetails.getAge());

			// Save the updated customer
			customerRepo.save(existingCustomer);
		} else {
			throw new IllegalArgumentException("Customer ID not found: " + customerID);
		}
	}
	public void deleteCustomer(Long id) {
		logger.info("Deleting Customer by its Customer id:{}",id);
		if (customerRepo.existsById(id)) {
			customerRepo.deleteById(id);
		} else {
			throw new NoSuchElementException("Cannot delete booking as Customer ID does not exist.");
		}
	}

}
