package dev.jacob.a2_draft.driver;

import dev.jacob.a2_draft.driver.Driver;
import org.springframework.data.domain.Page;

public interface DriverService {
    Driver saveDriver(Driver driver);
    Page<Driver> getAllDrivers(int page);
    Driver getDriver(Long id);
    Driver updateDriver(Driver driver, Long id);
    void deleteDriver(Long id);
    Driver bookCar(Long driver_id, Long car_id);
    String returnCar(Long driver_id);
}