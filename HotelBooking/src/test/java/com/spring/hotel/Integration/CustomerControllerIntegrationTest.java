package com.spring.hotel.Integration;

import com.spring.hotel.controller.CustomerController;
import com.spring.hotel.model.BookingDetails;
import com.spring.hotel.model.Customer;
import com.spring.hotel.model.Room;
import com.spring.hotel.repository.BookingDetailsRepository;
import com.spring.hotel.repository.CustomerRepository;
import com.spring.hotel.repository.RoomRepository;
import com.spring.hotel.service.CustomerService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.http.MediaType;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Transactional
public class CustomerControllerIntegrationTest {
    @Container
    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService custServ;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    public BookingDetailsRepository bookingRepo;
    @Autowired
    public RoomRepository roomRepo;
    @PersistenceContext
    private EntityManager entityManager;


    @BeforeEach
    public void setup() {
        entityManager.createNativeQuery("INSERT INTO booking_details (bookingid, advance_amount, bill_amount, discount_percentage, duration, end_date, mode_of_booking, mode_of_payment, start_date) VALUES (1001, 1000.0, 2000.0, 10.0, 2, '2023-05-18', 'Online', 'Credit Card', '2023-05-18')")
                .executeUpdate();

        // Insert data into the room table
        entityManager.createNativeQuery("INSERT INTO room (roomid, availability, is_checked_in, is_checked_out, occupancy, price_per_day, room_number, room_type, bookingid) VALUES (1, true, false, false, 2, 1000.0, 201, 'Double', 1001), (2, true, false, false, 1, 500.0, 202, 'Single', 1001), (3, true, false, false, 2, 1000.0, 203, 'Double', 1001), (4, true, false, false, 2, 1000.0, 204, 'Double', 1001)")
                .executeUpdate();

        // Insert data into the customer table
        entityManager.createNativeQuery("INSERT INTO customer (customerid, address, age, contact_number, full_name, bookingid) VALUES (1, '123 Main St', 13, '9876543210', 'Binay Kumar', 1001), (2, '456 Elm St', 14, '1234567890', 'Gurjot Parmar', 1001), (3, '789 Oak St', 25, '5551234567', 'Radhika Narang', 1001), (4, '321 Pine St', 15, '9876543210', 'Shaurya Dev', 1001)")
                .executeUpdate();

        entityManager.createNativeQuery("INSERT INTO customer (customerid, address, age, contact_number, full_name, bookingid) VALUES (5, '789 Elm St', 35, '5559876543', 'Adult Customer', 1001)")
                .executeUpdate();
    }

//    @BeforeEach
//    public void setUp() throws IOException {
//        // Read the JSON data from the file
//        String jsonData = new String(Files.readAllBytes(Paths.get("C:\\Users\\binay\\OneDrive\\Desktop\\SpringBoot_Project\\HotelBooking\\src\\test\\resources\\data.json")));
//
//        // Parse the JSON into a BookingDetails object
//        ObjectMapper objectMapper = new ObjectMapper();
//        BookingDetails bookingDetails = objectMapper.readValue(jsonData, BookingDetails.class);
//
//        // Save the BookingDetails and related entities (Customer and Room)
//        BookingDetails savedBookingDetails = bookingRepo.save(bookingDetails);
//
//        // Get the list of customers and rooms from the saved bookingDetails
//        List<Customer> customers = savedBookingDetails.getCust();
//        List<Room> rooms = savedBookingDetails.getRoom();
//
//        // Save the customers
//        customerRepo.saveAll(customers);
//
//        // Save the rooms
//        roomRepo.saveAll(rooms);
//    }


    private Customer createCustomer(Long custID,String fullName, String address, String contactNumber, int age) {
        Customer customer = new Customer();
        customer.setCustomerID(custID);
        customer.setFullName(fullName);
        customer.setAddress(address);
        customer.setContactNumber(contactNumber);
        customer.setAge(age);
        return customer;
    }

    @Test
    public void testGetAllCustomer() throws Exception {
        System.out.println(mySQLContainer.getDatabaseName());
        System.out.println(mySQLContainer.getUsername());
        System.out.println(mySQLContainer.getPassword());
        System.out.println(mySQLContainer.getJdbcUrl());
        // Perform the GET request
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/custDetails"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(content().contentType("application/json"));

    }
    @Test
    public void testAddCustomer() throws Exception {
        // Create a sample customer data
        Customer newCustomer = new Customer();
        newCustomer.setFullName("New Customer");
        newCustomer.setAddress("456 Elm St");
        newCustomer.setContactNumber("1234567890");
        newCustomer.setAge(30);

        // Perform the POST request to add a customer
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/custDetails/1001") // Use bookingID 1001 as per your data setup
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newCustomer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Data added successfully"));

        // Check if the customer was added to the database
        Optional<Customer> savedCustomer = customerRepo.findByFullName("New Customer");
        assertTrue(savedCustomer.isPresent()); // Assert that the customer exists in the database
    }
    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateAndThenUpdateCustomer() throws Exception {
        // Create a new customer
        Customer newCustomer = new Customer();
        newCustomer.setFullName("New Customer");
        newCustomer.setAddress("123 Elm St");
        newCustomer.setContactNumber("9876543210");
        newCustomer.setAge(30);

        // Perform the POST request to create the customer
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/custDetails/1001") // Use bookingID 1001 as per your data setup
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newCustomer)))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Expect a successful response
                .andExpect(MockMvcResultMatchers.content().string("Data added successfully"));

        // Now, update the customer's information
        Customer updatedCustomer = new Customer();
        updatedCustomer.setFullName("Updated Customer");
        updatedCustomer.setAddress("456 Elm St");
        updatedCustomer.setContactNumber("1234567890");
        updatedCustomer.setAge(25);

        // Perform the PUT request to update the customer
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/custDetails/{customerID}/1001", 101L) // Use the correct customer ID and bookingID
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedCustomer)))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Expect a successful response
                .andExpect(MockMvcResultMatchers.content().string("Data updated successfully"));

        // Check if the customer was updated in the database
        Optional<Customer> updatedCustomerOptional = customerRepo.findById(101L); // Use the correct customer ID
        assertTrue(updatedCustomerOptional.isPresent()); // Assert that the customer exists in the database
        Customer updatedCustomerFromDb = updatedCustomerOptional.get();
        assertEquals("Updated Customer", updatedCustomerFromDb.getFullName());
        assertEquals("456 Elm St", updatedCustomerFromDb.getAddress());
        assertEquals("1234567890", updatedCustomerFromDb.getContactNumber());
        assertEquals(25, updatedCustomerFromDb.getAge());
    }

    @Test
    public void testUpdateAdultCustomerAgeWithAssociatedAdult() throws Exception {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerID(3L); // Use the correct customer ID (3) for Radhika Narang
        updatedCustomer.setFullName("Radhika Narang");
        updatedCustomer.setAddress("789 Oak St");
        updatedCustomer.setContactNumber("5551234567");
        updatedCustomer.setAge(30); // Attempting to update the age of an adult customer

        // Perform the PUT request to update the customer's age
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/custDetails/3/1001") // Use customerID 3 and bookingID 1001 as per your data setup
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedCustomer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Data updated successfully"));

        // Check if the customer's age was updated in the database
        Optional<Customer> updatedCustomerOptional = customerRepo.findById(3L); // Use the correct customer ID (3)
        assertTrue(updatedCustomerOptional.isPresent()); // Assert that the customer exists in the database
        Customer updatedCustomerFromDb = updatedCustomerOptional.get();
        assertEquals("Radhika Narang", updatedCustomerFromDb.getFullName());
        assertEquals("789 Oak St", updatedCustomerFromDb.getAddress());
        assertEquals("5551234567", updatedCustomerFromDb.getContactNumber());
        assertEquals(30, updatedCustomerFromDb.getAge()); // Ensure the age is updated

        // Ensure that the age constraint is satisfied (at least one adult associated)
        boolean hasAdult = updatedCustomerFromDb.getBookingDetails().getCust().stream()
                .anyMatch(c -> c.getAge() >= 18 && !c.getCustomerID().equals(3L));
        assertTrue(hasAdult);
    }
    @Test
    public void testDeleteExistingCustomer() throws Exception {
        // Perform the DELETE request to delete the customer named "Binay"
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/custDetails/{customerID}", 1L)) // Use the correct customer ID for Binay
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Data deleted successfully"));

        // Check if the customer "Binay" is deleted from the database
        Optional<Customer> deletedCustomer = customerRepo.findByFullName("Binay Kumar");
        assertFalse(deletedCustomer.isPresent()); // Assert that the customer is not found in the database
    }



}
