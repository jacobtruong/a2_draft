package dev.jacob.a2_draft.service.impl;

import dev.jacob.a2_draft.exception.ResourceNotFoundException;
import dev.jacob.a2_draft.model.Car;
import dev.jacob.a2_draft.model.Driver;
import dev.jacob.a2_draft.repository.CarRepository;
import dev.jacob.a2_draft.repository.DriverRepository;
import dev.jacob.a2_draft.service.DriverService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {
    private DriverRepository driverRepository;
    private CarRepository carRepository;

    public DriverServiceImpl(DriverRepository driverRepository, CarRepository carRepository) {
        super();
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }

    @Override
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public Driver getDriver(Long id) {
        Optional<Driver> driver = driverRepository.findById(id);

        if(driver.isPresent()) {
            return driver.get();
        } else {
            throw new ResourceNotFoundException("Driver", "ID", id);
        }

//        return driverRepository.findById(id).orElseThrow(() ->
//                new ResourceNotFoundException("Driver", "ID", id));
    }

    @Override
    public Driver updateDriver(Driver driver, Long id) {
        // Check if there exists an driver with ID in DB
        Driver existingDriver = driverRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Driver", "ID", id));

        // Update using data from body
        existingDriver.setFirst_name(driver.getFirst_name());
        existingDriver.setLast_name(driver.getLast_name());
        existingDriver.setLicense_number(driver.getLicense_number());
        existingDriver.setPhone_number(driver.getPhone_number());
        existingDriver.setRating(driver.getRating());

        // Save existing driver to DB
        driverRepository.save(existingDriver);

        return existingDriver;
    }

    @Override
    public void deleteDriver(Long id) {
        Optional<Driver> driver = driverRepository.findById(id);

        // Check if there exists an driver with ID in DB
        if (driver.isPresent()) {
            driverRepository.deleteById(id);
            return;
        } else {
            throw new ResourceNotFoundException("Driver", "ID", id);
        }
    }

    @Override
    public Driver bookCar(Long driver_id, Long car_id) {
        Driver driver = driverRepository.findById(driver_id).orElseThrow(() -> new ResourceNotFoundException("Driver", "ID", driver_id));
        if (driver.getCar() != null) {
            throw new RuntimeException(String.format("Driver with ID %d already has already booked a car (Car %d)!\n", driver_id, driver.getCar().getId()));
        }

        Car car = carRepository.findById(car_id).orElseThrow(() -> new ResourceNotFoundException("Car", "ID", car_id));

        if (car.isHaving_driver()) {
            throw new RuntimeException(String.format("Car with ID %d has already been booked!\n", car_id));
        } else {
            car.setHaving_driver(true);
            car.setDriver(driver);
            car.setAvailable(true);
            carRepository.save(car);
            // driver.setCar(car);
        }

        return driver;
    }

    @Override
    public String returnCar(Long driver_id) {
        Driver driver = driverRepository.findById(driver_id).orElseThrow(() -> new ResourceNotFoundException("Driver", "ID", driver_id));

        Car car = driver.getCar();
        car.setAvailable(false);
        car.setHaving_driver(false);
        car.setDriver(null);

        carRepository.save(car);

//        driver.setCar(null);
//        driverRepository.save(driver);

        return String.format("Driver %d has successfully returned Car %d", driver_id, car.getId());
    }
}
