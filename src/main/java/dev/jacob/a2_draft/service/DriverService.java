package dev.jacob.a2_draft.service;

import dev.jacob.a2_draft.model.Driver;

import java.util.List;

public interface DriverService {
    Driver saveDriver(Driver driver);
    List<Driver> getAllDrivers();
    Driver getDriver(long id);
    Driver updateDriver(Driver driver, long id);
    void deleteDriver(long id);
}