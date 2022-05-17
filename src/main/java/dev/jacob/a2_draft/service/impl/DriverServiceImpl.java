package dev.jacob.a2_draft.service.impl;

import dev.jacob.a2_draft.exception.ResourceNotFoundException;
import dev.jacob.a2_draft.model.Driver;
import dev.jacob.a2_draft.repository.DriverRepository;
import dev.jacob.a2_draft.service.DriverService;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {
    private DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        super();
        this.driverRepository = driverRepository;
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
    public Driver getDriver(long id) {
//        Optional<Driver> driver = driverRepository.findById(id);
//
//        if(driver.isPresent()) {
//            return driver.get();
//        } else {
//            throw new ResourceNotFoundException("Driver", "ID", id);
//        }

        return driverRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Driver", "ID", id));
    }

    @Override
    public Driver updateDriver(Driver driver, long id) {
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
    public void deleteDriver(long id) {
        Optional<Driver> driver = driverRepository.findById(id);

        // Check if there exists an driver with ID in DB
        if (driver.isPresent()) {
            driverRepository.deleteById(id);
            return;
        } else {
            throw new ResourceNotFoundException("Driver", "ID", id);
        }


    }
}
