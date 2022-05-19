package dev.jacob.a2;

import dev.jacob.a2.exception.ResourceNotFoundException;
import dev.jacob.a2.car.Car;
import dev.jacob.a2.driver.Driver;
import dev.jacob.a2.car.CarRepository;
import dev.jacob.a2.driver.DriverRepository;
import dev.jacob.a2.car.CarServiceImpl;
import dev.jacob.a2.driver.DriverServiceImpl;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DriverServiceImplTests {

    @MockBean
    DriverRepository driverRepository;

    @MockBean
    CarRepository carRepository;

    @InjectMocks
    DriverServiceImpl driverService;

    @InjectMocks
    CarServiceImpl carService;

    @Test
    @Rollback(value = false)
    public void testSave() {
        // Create Mock driver
        Driver driver1 = new Driver();
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);


        // Define behaviour of repository
        when (driverRepository.save(driver1)).thenReturn(driver1);

        // Call service method
        Driver actualDriver = driverService.saveDriver(driver1);

        // Assert the result
        assertEquals(actualDriver, driver1);
    }

    @Test
    @Rollback(value = false)
    public void testGetAll() {
        // Create Mock driver
        List<Driver> mockDrivers = new ArrayList<>();

        Driver driver1 = new Driver();
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Driver driver2 = new Driver();
        driver2.setFirst_name("Balentina");
        driver2.setLast_name("Tossi");
        driver2.setPhone_number("0909090945");
        driver2.setLicense_number("123456789123");
        driver2.setRating(0.5f);

        mockDrivers.add(driver1);
        mockDrivers.add(driver2);

        Page<Driver> mock = new PageImpl<>(mockDrivers);

        // Define behaviour of repository
        when (driverRepository.findAll(PageRequest.of(0, 5))).thenReturn(mock);

        // Call service method
        Page<Driver> actualDrivers = driverService.getAllDrivers(0);

        // Assert the result
        assertEquals(actualDrivers.getContent(), mockDrivers);
    }

    @Test
    @Rollback(value = false)
    public void testGetDriver() {
        // Create Mock driver
        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        // Define behaviour of repository
        when (driverRepository.findById(1L)).thenReturn(Optional.of(driver1));

        // Call service method
        Driver actualDriver = driverService.getDriver(1L);

        // Assert the result
        assertEquals(driver1, actualDriver);
        assertThatThrownBy(() -> driverService.getDriver(2L));
    }

    @Test
    @Rollback(value = false)
    public void testUpdateDriver() {
        // Create Mock driver
        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        // Define behaviour of repository
        when (driverRepository.findById(1L)).thenReturn(Optional.of(driver1));

        // Create driver2
        Driver driver2 = new Driver();
        driver2.setId(1L);
        driver2.setFirst_name("Balentina");
        driver2.setLast_name("Tossi");
        driver2.setPhone_number("0909090945");
        driver2.setLicense_number("123456789123");
        driver2.setRating(0.5f);

        // Call service method
        Driver actualDriver = driverService.updateDriver(driver2, 1L);

        // Assert the result
        assertEquals(driver2, actualDriver);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteDriver() {
        // Create Mock driver
        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        // Define behaviour of repository
        when (driverRepository.findById(1L)).thenReturn(Optional.of(driver1));

        // Call service method
        driverService.deleteDriver(1L);

        // Assert the result
        assertThatThrownBy(() -> driverService.deleteDriver(2L));
    }

    @Test
    @Rollback(value = false)
    public void testBookCar() {
        // Create Mock driver
        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

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
        when (driverRepository.findById(1L)).thenReturn(Optional.of(driver1));
        when (carRepository.findById(1L)).thenReturn(Optional.of(car1));

        // Call service method and Assert the result
        assertThatThrownBy(() -> driverService.bookCar(2L, 1L)).isInstanceOf(ResourceNotFoundException.class);

        driver1.setCar(car1);
        assertThatThrownBy(() -> driverService.bookCar(1L, 1L)).isInstanceOf(RuntimeException.class);
        driver1.setCar(null);

        assertThatThrownBy(() -> driverService.bookCar(1L, 2L)).isInstanceOf(ResourceNotFoundException.class);

        car1.setHaving_driver(true);
        assertThatThrownBy(() -> driverService.bookCar(1L, 1L)).isInstanceOf(RuntimeException.class);
        car1.setHaving_driver(false);

        assertEquals(driver1, driverService.bookCar(1L, 1L));
    }

    @Test
    @Rollback(value = false)
    public void testReturnCar() {
        // Create Mock driver
        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

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
        when (driverRepository.findById(1L)).thenReturn(Optional.of(driver1));

        // Call service method and Assert the result
        assertThatThrownBy(() -> driverService.returnCar(2L)).isInstanceOf(ResourceNotFoundException.class);

        driver1.setCar(car1);

        assertEquals("Driver 1 has successfully returned Car 1", driverService.returnCar(1L));
    }
}
