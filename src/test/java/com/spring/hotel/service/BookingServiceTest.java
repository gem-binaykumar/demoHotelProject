package com.spring.hotel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.spring.hotel.model.BookingDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.spring.hotel.model.Customer;
import com.spring.hotel.model.Room;
import com.spring.hotel.repository.BookingDetailsRepository;

class BookingServiceTest {

    @Mock
    private BookingDetailsRepository bookingRepo;

    @InjectMocks
    private BookingDetailsService bookingService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBookingDetails() {
        BookingDetails bookingDetails = createBookingDetails();
        bookingService.addBookingDetails(bookingDetails);
        verify(bookingRepo, times(1)).save(bookingDetails);
    }

    @Test
    public void testUpdateBookingDetails() {
        Long id = 1000L;
        BookingDetails bookingDetails = createBookingDetails();
        when(bookingRepo.findById(id)).thenReturn(Optional.empty()); // Return empty Optional if ID does not exist
        when(bookingRepo.save(any(BookingDetails.class))).thenReturn(bookingDetails);
        assertThrows(NoSuchElementException.class, () -> {
            bookingService.updateBookingDetails(id, bookingDetails);
        });
    }

    @Test
    void testUpdateBookingDetails_NonExistentId() {
        long nonExistentId = 1000L;
        BookingDetails updatedBookingDetails = new BookingDetails();
        updatedBookingDetails.setBookingID(nonExistentId);
        when(bookingRepo.existsById(nonExistentId)).thenReturn(false);
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            bookingService.updateBookingDetails(nonExistentId, updatedBookingDetails);
        });
        assertEquals("Cannot update booking as Booking ID does not exist.", exception.getMessage());
    }


    @Test
    public void testDeleteBookingDetails() {
        Long id = 1000L;
        when(bookingRepo.existsById(id)).thenReturn(true);
        bookingService.deleteBookingDetails(id);
        verify(bookingRepo, times(1)).deleteById(id);
    }


    @Test
    public void testGetAllBookingDetails() {
        List<BookingDetails> bookingDetailsList = new ArrayList<>();
        bookingDetailsList.add(createBookingDetails());
        when(bookingRepo.findAll()).thenReturn(bookingDetailsList);
        List<BookingDetails> result = bookingService.getAllBookingDetails();
        assertEquals(bookingDetailsList.size(), result.size());
    }

    @Test
    public void testGetBookingDetailsById() {
        Long id = 1000L;
        BookingDetails bookingDetails = createBookingDetails();
        when(bookingRepo.findById(id)).thenReturn(Optional.of(bookingDetails));
        BookingDetails result = bookingService.getBookingDetailsById(id);
        assertEquals(bookingDetails, result);
    }

    private BookingDetails createBookingDetails() {
        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setStart_date(Date.valueOf("2023-05-18"));
        bookingDetails.setEnd_date(Date.valueOf("2023-05-20"));
        bookingDetails.setModeOfBooking("Online");
        bookingDetails.setModeOfPayment("Credit Card");
        bookingDetails.setDiscountPercentage(25.0);

        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer();
        customer1.setFullName("Binay Kumar");
        customer1.setAddress("123 Main St");
        customer1.setContactNumber("9876543210");
        customer1.setAge(13);
        customers.add(customer1);

        Customer customer2 = new Customer();
        customer2.setFullName("Gurjot Parmar");
        customer2.setAddress("456 Elm St");
        customer2.setContactNumber("1234567890");
        customer2.setAge(14);
        customers.add(customer2);

        Customer customer3 = new Customer();
        customer3.setFullName("Radhika Narang");
        customer3.setAddress("789 Oak St");
        customer3.setContactNumber("5551234567");
        customer3.setAge(25);
        customers.add(customer3);

        Customer customer4 = new Customer();
        customer4.setFullName("Shaurya Dev");
        customer4.setAddress("321 Pine St");
        customer4.setContactNumber("9876543210");
        customer4.setAge(15);
        customers.add(customer4);

        bookingDetails.setCust(customers);

        List<Room> rooms = new ArrayList<>();
        Room room1 = new Room();
        room1.setRoomNumber(201L);
        room1.setRoomType("Double");
        room1.setOccupancy(2);
        room1.setAvailability(true);
        room1.setPricePerDay(1000.0);
        room1.setCheckedIn(false);
        room1.setCheckedOut(false);
        rooms.add(room1);

        Room room2 = new Room();
        room2.setRoomNumber(202L);
        room2.setRoomType("Single");
        room2.setOccupancy(1);
        room2.setAvailability(true);
        room2.setPricePerDay(500.0);
        room2.setCheckedIn(false);
        room2.setCheckedOut(false);
        rooms.add(room2);

        Room room3 = new Room();
        room3.setRoomNumber(203L);
        room3.setRoomType("Double");
        room3.setOccupancy(2);
        room3.setAvailability(true);
        room3.setPricePerDay(1000.0);
        room3.setCheckedIn(false);
        room3.setCheckedOut(false);
        rooms.add(room3);

        Room room4 = new Room();
        room4.setRoomNumber(204L);
        room4.setRoomType("Double");
        room4.setOccupancy(2);
        room4.setAvailability(true);
        room4.setPricePerDay(1000.0);
        room4.setCheckedIn(false);
        room4.setCheckedOut(false);
        rooms.add(room4);

        bookingDetails.setRoom(rooms);

        return bookingDetails;
    }
}
