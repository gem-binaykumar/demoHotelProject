package com.spring.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.hotel.model.BookingDetails;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Long> {
	
}

