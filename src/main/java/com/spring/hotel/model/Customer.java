package com.spring.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
	@SequenceGenerator(name = "customer_seq", sequenceName = "customer_sequence", initialValue = 101, allocationSize = 1)

	@Column(name = "customerID")
    private Long customerID;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "age")
    private int age;

    @ManyToOne
    @JoinColumn(name = "bookingID")
	@JsonIgnore
    private BookingDetails bookingDetails;
    
    
    
	public Customer() {
		super();
	}

	public Customer(Long customerID, String fullName, String address, String contactNumber, int age) {
		super();
		this.customerID = customerID;
		this.fullName = fullName;
		this.address = address;
		this.contactNumber = contactNumber;
		this.age = age;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	public BookingDetails getBookingDetails() {
		return bookingDetails;
	}

	public void setBookingDetails(BookingDetails bookingDetails) {
		this.bookingDetails = bookingDetails;
	}
	

    
}


