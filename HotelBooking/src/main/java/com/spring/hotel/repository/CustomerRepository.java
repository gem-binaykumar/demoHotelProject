package com.spring.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.hotel.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByFullName(String fullName);
}

