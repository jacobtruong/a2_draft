package dev.jacob.a2_draft;

import dev.jacob.a2_draft.car.Car;
import dev.jacob.a2_draft.car.CarRepository;
import dev.jacob.a2_draft.customer.Customer;
import dev.jacob.a2_draft.customer.CustomerRepository;
import dev.jacob.a2_draft.driver.Driver;
import dev.jacob.a2_draft.exception.ResourceNotFoundException;
import dev.jacob.a2_draft.invoice.Invoice;
import dev.jacob.a2_draft.booking.Booking;
import dev.jacob.a2_draft.booking.BookingRepository;
import dev.jacob.a2_draft.booking.BookingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookingServiceImplTests {

    @MockBean
    BookingRepository bookingRepository;

    @InjectMocks
    BookingServiceImpl bookingService;

    @Test
    @Rollback(value = false)
    public void testSave() {
        // Create Mock booking
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);
        driver1.setCar(car1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setCar(car1);
        booking1.setStarting_location("Nguyen Van Linh, Quan 7");
        booking1.setEnd_location("Truong Son, Quan Tan Binh");
        booking1.setPick_up_time(ZonedDateTime.now());
        booking1.setDrop_off_time(ZonedDateTime.now());
        booking1.setDateCreated(ZonedDateTime.now());
        booking1.setInvoice(invoice1);
        invoice1.setBooking(booking1);

        // Define behaviour of repository
        when (bookingRepository.save(booking1)).thenReturn(booking1);

        // Call service method
        Booking actualBooking = bookingService.saveBooking(booking1);

        // Assert the result
        assertEquals(actualBooking, booking1);
    }

    @Test
    @Rollback(value = false)
    public void testGetAll() {
        // Create Mock booking
        List<Booking> mockBookings = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);
        driver1.setCar(car1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setCar(car1);
        booking1.setStarting_location("Nguyen Van Linh, Quan 7");
        booking1.setEnd_location("Truong Son, Quan Tan Binh");
        booking1.setPick_up_time(ZonedDateTime.now());
        booking1.setDrop_off_time(ZonedDateTime.now());
        booking1.setDateCreated(ZonedDateTime.now());
        booking1.setInvoice(invoice1);
        invoice1.setBooking(booking1);

        Invoice invoice2 = new Invoice();
        invoice1.setId(2L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Booking booking2 = new Booking();
        booking2.setId(2L);
        booking2.setCar(car1);
        booking2.setStarting_location("Nguyen Van Linh, Quan 7");
        booking2.setEnd_location("Truong Son, Quan Tan Binh");
        booking2.setPick_up_time(ZonedDateTime.now());
        booking2.setDrop_off_time(ZonedDateTime.now());
        booking2.setDateCreated(ZonedDateTime.now());
        booking2.setInvoice(invoice2);
        invoice2.setBooking(booking2);

        mockBookings.add(booking1);
        mockBookings.add(booking2);

        Page<Booking> mock = new PageImpl<>(mockBookings);

        // Define behaviour of repository
        when (bookingRepository.findAll(PageRequest.of(0,5))).thenReturn(mock);

        // Call service method
        Page<Booking> actualBookings = bookingService.getAllBookings(0);

        // Assert the result
        assertEquals(actualBookings.getContent(), mockBookings);
    }

    @Test
    @Rollback(value = false)
    public void testSearchBookings() {
        // Create Mock booking
        List<Booking> mockBookings = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);
        driver1.setCar(car1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setCar(car1);
        booking1.setStarting_location("Nguyen Van Linh, Quan 7");
        booking1.setEnd_location("Truong Son, Quan Tan Binh");
        booking1.setPick_up_time(ZonedDateTime.now());
        booking1.setDrop_off_time(ZonedDateTime.now());
        booking1.setDateCreated(ZonedDateTime.now());
        booking1.setInvoice(invoice1);
        invoice1.setBooking(booking1);

        Invoice invoice2 = new Invoice();
        invoice1.setId(2L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Booking booking2 = new Booking();
        booking2.setId(2L);
        booking2.setCar(car1);
        booking2.setStarting_location("Nguyen Van Linh, Quan 7");
        booking2.setEnd_location("Truong Son, Quan Tan Binh");
        booking2.setPick_up_time(ZonedDateTime.now());
        booking2.setDrop_off_time(ZonedDateTime.now());
        booking2.setDateCreated(ZonedDateTime.now());
        booking2.setInvoice(invoice2);
        invoice2.setBooking(booking2);

        mockBookings.add(booking1);
        mockBookings.add(booking2);

        String date = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd MM yyyy"));

        // Define behaviour of repository
        when (bookingRepository.findAll()).thenReturn(mockBookings);

        // Call service method
        List<Booking> actualBookings = bookingService.searchBookings(date, "", "");
        assertEquals(actualBookings, mockBookings);

        actualBookings = bookingService.searchBookings("", date, "");
        assertEquals(actualBookings, mockBookings);

        actualBookings = bookingService.searchBookings("", "", date);
        assertEquals(actualBookings, mockBookings);

        actualBookings = bookingService.searchBookings("", date, date);
        assertEquals(actualBookings, mockBookings);

        actualBookings = bookingService.searchBookings("", "", "");
        mockBookings.clear();
        assertEquals(actualBookings, mockBookings);
    }

    @Test
    @Rollback(value = false)
    public void testGetBooking() {
        // Create Mock booking
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);
        driver1.setCar(car1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setCar(car1);
        booking1.setStarting_location("Nguyen Van Linh, Quan 7");
        booking1.setEnd_location("Truong Son, Quan Tan Binh");
        booking1.setPick_up_time(ZonedDateTime.now());
        booking1.setDrop_off_time(ZonedDateTime.now());
        booking1.setDateCreated(ZonedDateTime.now());
        booking1.setInvoice(invoice1);
        invoice1.setBooking(booking1);

        // Define behaviour of repository
        when (bookingRepository.findById(1L)).thenReturn(Optional.of(booking1));

        // Call service method
        assertEquals(booking1, bookingService.getBooking(1L));
        assertThatThrownBy(() -> bookingService.getBooking(2L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @Rollback(value = false)
    public void testUpdateBooking() {
        // Create Mock booking
        List<Booking> mockBookings = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);
        driver1.setCar(car1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setCar(car1);
        booking1.setStarting_location("Nguyen Van Linh, Quan 7");
        booking1.setEnd_location("Truong Son, Quan Tan Binh");
        booking1.setPick_up_time(ZonedDateTime.now());
        booking1.setDrop_off_time(ZonedDateTime.now());
        booking1.setDateCreated(ZonedDateTime.now());
        booking1.setInvoice(invoice1);
        invoice1.setBooking(booking1);

        Invoice invoice2 = new Invoice();
        invoice1.setId(2L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Booking booking2 = new Booking();
        booking2.setId(1L);
        booking2.setCar(car1);
        booking2.setStarting_location("Nguyen Van Linh, Quan 7");
        booking2.setEnd_location("Truong Son, Quan Tan Binh");
        booking2.setPick_up_time(ZonedDateTime.now());
        booking2.setDrop_off_time(ZonedDateTime.now());
        booking2.setDateCreated(ZonedDateTime.now());
        booking2.setInvoice(invoice2);
        invoice2.setBooking(booking2);

        mockBookings.add(booking1);
        mockBookings.add(booking2);

        // Define behaviour of repository
        when (bookingRepository.findById(1L)).thenReturn(Optional.of(booking1));

        // Call service method
        Booking actualBooking = bookingService.updateBooking(booking2, 1L);

        // Assert the result
        assertEquals(booking2, actualBooking);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteBooking() {
        // Create Mock booking
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);
        driver1.setCar(car1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setCar(car1);
        booking1.setStarting_location("Nguyen Van Linh, Quan 7");
        booking1.setEnd_location("Truong Son, Quan Tan Binh");
        booking1.setPick_up_time(ZonedDateTime.now());
        booking1.setDrop_off_time(ZonedDateTime.now());
        booking1.setDateCreated(ZonedDateTime.now());
        booking1.setInvoice(invoice1);
        invoice1.setBooking(booking1);

        // Define behaviour of repository
        when (bookingRepository.findById(1L)).thenReturn(Optional.of(booking1));

        // Call service method
        bookingService.deleteBooking(1L);

        // Assert the result
        assertThatThrownBy(() -> bookingService.deleteBooking(2L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    CarRepository carRepository;

    @Test
    @Rollback(value = false)
    public void testCreateBooking() {
        // Create Mock booking
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setCar(car1);
        booking1.setStarting_location("Nguyen Van Linh, Quan 7");
        booking1.setEnd_location("Truong Son, Quan Tan Binh");
        booking1.setPick_up_time(ZonedDateTime.now());
        booking1.setDrop_off_time(ZonedDateTime.now());
        booking1.setDateCreated(ZonedDateTime.now());
        booking1.setInvoice(invoice1);
//        invoice1.setBooking(booking1);

        // Define behaviour of repository
        when (customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when (carRepository.findById(1L)).thenReturn(Optional.of(car1));

        // Call service method
        Booking actualBooking = bookingService.createBooking(1L, 1L, "Nguyen Van Linh, Quan 7", "Truong Son, Quan Tan Binh", 10);

        // Assert the result
        assertEquals(booking1.getStarting_location(), actualBooking.getStarting_location());
        assertEquals(booking1.getEnd_location(), actualBooking.getEnd_location());
        assertThatThrownBy(() -> bookingService.createBooking(2L, 2L, "", "", 10)).isInstanceOf(ResourceNotFoundException.class);

        car1.setAvailable(false);
        assertThatThrownBy(() -> bookingService.createBooking(1L, 1L, "", "", 10)).isInstanceOf(RuntimeException.class);
        car1.setAvailable(true);

        assertThatThrownBy(() -> bookingService.createBooking(2L, 1L, "", "", 10)).isInstanceOf(ResourceNotFoundException.class);

    }

    @Test
    @Rollback(value = false)
    public void testFinishBooking() {
        // Create Mock booking
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);
        driver1.setCar(car1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setCar(car1);
        booking1.setStarting_location("Nguyen Van Linh, Quan 7");
        booking1.setEnd_location("Truong Son, Quan Tan Binh");
        booking1.setPick_up_time(ZonedDateTime.now());
        booking1.setDrop_off_time(null);
        booking1.setDateCreated(ZonedDateTime.now());
        booking1.setInvoice(invoice1);
//        invoice1.setBooking(booking1);

        // Define behaviour of repository
        when (bookingRepository.findById(1L)).thenReturn(Optional.of(booking1));

        // Call service method
        Booking actualBooking = bookingService.finishBooking(1L);

        // Assert the result
        booking1.setDrop_off_time(actualBooking.getDrop_off_time());
        assertEquals(actualBooking, booking1);

        assertThatThrownBy(() -> bookingService.finishBooking(2L)).isInstanceOf(ResourceNotFoundException.class);

        booking1.setDrop_off_time(ZonedDateTime.now());
        assertThatThrownBy(() -> bookingService.finishBooking(1L)).isInstanceOf(RuntimeException.class);
    }
}
