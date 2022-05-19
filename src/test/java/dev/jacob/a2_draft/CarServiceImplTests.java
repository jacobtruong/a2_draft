package dev.jacob.a2_draft;

import dev.jacob.a2_draft.booking.Booking;
import dev.jacob.a2_draft.customer.Customer;
import dev.jacob.a2_draft.driver.Driver;
import dev.jacob.a2_draft.exception.ResourceNotFoundException;
import dev.jacob.a2_draft.invoice.Invoice;
import dev.jacob.a2_draft.car.Car;
import dev.jacob.a2_draft.car.CarRepository;
import dev.jacob.a2_draft.booking.BookingServiceImpl;
import dev.jacob.a2_draft.car.CarServiceImpl;
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
public class CarServiceImplTests {

    @MockBean
    CarRepository carRepository;

    @InjectMocks
    CarServiceImpl carService;

    @MockBean
    BookingServiceImpl bookingService;


    @Test
    @Rollback(value = false)
    public void testSave() {
        // Create Mock car
        Car car1 = new Car();
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(false);
        car1.setAvailable(false);


        // Define behaviour of repository
        when (carRepository.save(car1)).thenReturn(car1);

        // Call service method
        Car actualCar = carService.saveCar(car1);

        // Assert the result
        assertEquals(actualCar, car1);
    }

    @Test
    @Rollback(value = false)
    public void testGetAll() {
        // Create Mock car
        List<Car> mockCars = new ArrayList<>();

        Car car1 = new Car();
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(false);
        car1.setAvailable(false);

        Car car2 = new Car();
        car2.setVin("WDF9634031B995860");
        car2.setMake("Mercedes");
        car2.setColour("Grey");
        car2.setConvertible(false);
        car2.setRating(5f);
        car2.setLicense_plate("50E-99999");
        car2.setRpk(3.5f);
        car2.setHaving_driver(false);
        car2.setAvailable(false);

        mockCars.add(car1);
        mockCars.add(car2);

        Page<Car> mock = new PageImpl<>(mockCars);

        // Define behaviour of repository
        when (carRepository.findAll(PageRequest.of(0, 5))).thenReturn(mock);

        // Call service method
        Page<Car> actualCars = carService.getAllCars(0);

        // Assert the result
        assertEquals(actualCars.getContent(), mockCars);
    }

    @Test
    @Rollback(value = false)
    public void testGetAllAvailable() {
        // Create Mock car
        List<Car> mockCars = new ArrayList<>();

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(false);
        car1.setAvailable(false);

        Car car2 = new Car();
        car2.setId(2L);
        car2.setVin("WDF9634031B995860");
        car2.setMake("Mercedes");
        car2.setColour("Grey");
        car2.setConvertible(false);
        car2.setRating(5f);
        car2.setLicense_plate("50E-99999");
        car2.setRpk(3.5f);
        car2.setHaving_driver(false);
        car2.setAvailable(true);

        mockCars.add(car1);
        mockCars.add(car2);

        // Define behaviour of repository
        when (carRepository.findAll()).thenReturn(mockCars);

        // Call service method
        List<Car> actualCars = carService.getAllAvailableCars();
        mockCars.remove(car1);
        assertEquals(mockCars, actualCars);
    }

    @Test
    @Rollback(value = false)
    public void testGetCar() {
        // Create Mock car
        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(false);
        car1.setAvailable(false);

        // Define behaviour of repository
        when (carRepository.findById(1L)).thenReturn(Optional.of(car1));

        // Call service method
        Car actualCar = carService.getCar(1L);

        // Assert the result
        assertEquals(car1, actualCar);
        assertThatThrownBy(() -> carService.getCar(2L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @Rollback(value = false)
    public void testUpdateCar() {
        // Create Mock car
        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(false);
        car1.setAvailable(false);

        // Define behaviour of repository
        when (carRepository.findById(1L)).thenReturn(Optional.of(car1));

        // Create car2
        Car car2 = new Car();
        car2.setId(1L);
        car2.setVin("WDF9634031B995860");
        car2.setMake("Mercedes");
        car2.setColour("Grey");
        car2.setConvertible(false);
        car2.setRating(5f);
        car2.setLicense_plate("50E-99999");
        car2.setRpk(3.5f);
        car2.setHaving_driver(false);
        car2.setAvailable(false);

        // Call service method
        Car actualCar = carService.updateCar(car2, 1L);

        // Assert the result
        assertEquals(car2, actualCar);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteCar() {
        // Create Mock driver
        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(false);
        car1.setAvailable(false);

        // Define behaviour of repository
        when (carRepository.findById(1L)).thenReturn(Optional.of(car1));

        // Call service method
        carService.deleteCar(1L);

        // Assert the result
        assertThatThrownBy(() -> carService.deleteCar(2L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @Rollback(value = false)
    public void testGetMonthlyCarStatistics() {
        // Create Mock booking
        List<Booking> bookings = new ArrayList<>();

        int distance = 10;

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
        invoice1.setTotal_charge(distance * car1.getRpk());
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

        bookings.add(booking1);

        // Define behaviour of repository
        when (bookingService.searchBookings("", "01 05 2022", "31 05 2022")).thenReturn(bookings);

        // Call service method
        assertThatThrownBy(() -> carService.getMonthlyCarStatistics("")).isInstanceOf(RuntimeException.class);

        String result = carService.getMonthlyCarStatistics("05 2022");
        assertEquals("Month: 05 2022\n" +
                "Car             |             Days\n" +
                "50E-23122       |             1\n", result);
    }
}
