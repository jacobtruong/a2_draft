package dev.jacob.a2_draft.service;

import dev.jacob.a2_draft.model.Driver;

import java.util.List;

public interface DriverService {
    Driver saveDriver(Driver driver);
    List<Driver> getAllDrivers();
    Driver getDriver(Long id);
    Driver updateDriver(Driver driver, Long id);
    void deleteDriver(Long id);
    Driver bookCar(Long driver_id, Long car_id);
    String returnCar(Long driver_id);
}