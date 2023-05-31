package com.spring.hotel.model;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.persistence.*;

@Entity
@Table(name="booking_details")
public class BookingDetails {
	
	@Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_seq")
	@SequenceGenerator(name = "booking_seq", sequenceName = "booking_sequence", initialValue = 1001, allocationSize = 1)

	@Column(name = "bookingID")
    private Long bookingID;

    @Column(name = "duration")
    private int duration;
    
   // @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date start_date;
    
  //  @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date end_date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bookingID", referencedColumnName = "bookingID")
    private List<Customer> cust;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bookingID", referencedColumnName = "bookingID")
    private List<Room> room;

    @Column(name = "modeOfBooking")
    private String modeOfBooking;

    @Column(name = "bill_amount")
    private double bill_amount;

    @Column(name = "modeOfPayment")
    private String modeOfPayment;

	@Column(name="advanceAmount")
	private double advanceAmount;

	@Column(name = "discountPercentage")
	private Double discountPercentage;
	
    
	public BookingDetails() {

		super();
	}
	public BookingDetails(Long bookingID, int duration, Date start_date, Date end_date, String modeOfBooking,
			double bill_amount, String modeOfPayment, double advanceAmount,Double discountPercentage) {
		super();
		this.bookingID = bookingID;
		this.duration = duration;
		this.start_date = start_date;
		this.end_date = end_date;
		this.modeOfBooking = modeOfBooking;
		this.bill_amount = bill_amount;
		this.modeOfPayment = modeOfPayment;
		this.advanceAmount=advanceAmount;
		this.discountPercentage=discountPercentage;
	}
	public Long getBookingID() {

		return bookingID;
	}
	public void setBookingID(Long bookingID) {

		this.bookingID = bookingID;
	}
	public Integer getDuration() {

		return duration;
	}
	public void setDuration(Integer duration) {

		this.duration = duration;


	}
	public Date getStart_date() {

		return start_date;
	}
	public void setStart_date(Date start_date) {

		this.start_date = start_date;
	}
	public Date getEnd_date() {

		return end_date;
	}
	public void setEnd_date(Date end_date) {

		this.end_date = end_date;
	}
	public List<Customer> getCust() {

		return cust;
	}
	public void setCust(List<Customer> cust) {
		this.cust = cust;
		for (Customer customer : cust) {
			customer.setBookingDetails(this);
		}
	}
	public List<Room> getRoom() {

		return room;
	}
	public void setRoom(List<Room> room) {

		this.room = room;
		for (Room r : room) {
			r.setBookingDetails(this);
		}

	}
	public String getModeOfBooking() {

		return modeOfBooking;
	}
	public void setModeOfBooking(String modeOfBooking) {

		this.modeOfBooking = modeOfBooking;
	}
	public Double getBill_amount() {

		return bill_amount;
	}
	public void setBill_amount(Double bill_amount) {

		this.bill_amount = bill_amount;
	}
	public String getModeOfPayment() {

		return modeOfPayment;
	}
	public void setModeOfPayment(String modeOfPayment) {

		this.modeOfPayment = modeOfPayment;
	}

	public double getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
}
