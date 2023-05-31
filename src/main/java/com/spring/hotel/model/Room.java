package com.spring.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
@Table(name = "room")
public class Room {

    @Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq")
	@SequenceGenerator(name = "room_seq", sequenceName = "room_sequence", initialValue = 1601, allocationSize = 1)

	@Column(name = "roomID")
	private Long roomID;

    @Column(name = "roomNumber")
    private Long roomNumber;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "occupancy")
    private int occupancy;

    @Column(name = "availability")
    private boolean availability;

    @Column(name = "price_per_day")
    private double pricePerDay;

    @Column(name = "is_checked_in")
    private boolean isCheckedIn;

    @Column(name = "is_checked_out")
    private boolean isCheckedOut;

    @ManyToOne
    @JoinColumn(name = "bookingID")
	@JsonIgnore
    private BookingDetails bookingDetails;
    
    

	public Room() {
		super();
	}

	public Room(Long roomID,Long roomNumber, String roomType, int occupancy, boolean availability, double pricePerDay,
			boolean isCheckedIn, boolean isCheckedOut) {
		super();
		this.roomID = roomID;
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.occupancy = occupancy;
		this.availability = availability;
		this.pricePerDay = pricePerDay;
		this.isCheckedIn = isCheckedIn;
		this.isCheckedOut = isCheckedOut;
	}

	public Long getRoomID() {
		return roomID;
	}

	public void setRoomID(Long roomID) {
		this.roomID = roomID;
	}

	public Long getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Long roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public boolean getAvailability() {
		return availability;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public boolean isCheckedIn() {
		return isCheckedIn;
	}

	public void setCheckedIn(boolean isCheckedIn) {
		this.isCheckedIn = isCheckedIn;
	}

	public boolean isCheckedOut() {
		return isCheckedOut;
	}

	public void setCheckedOut(boolean isCheckedOut) {
		this.isCheckedOut = isCheckedOut;
	}

	public BookingDetails getBookingDetails() {
		return bookingDetails;
	}

	public void setBookingDetails(BookingDetails bookingDetails) {
		this.bookingDetails = bookingDetails;
	}

    
}
