package dev.jacob.a2_draft.driver;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private DriverService driverService;

    public DriverController(DriverService driverService) {
        super();
        this.driverService = driverService;
    }

    // Build create driver REST API
    @PostMapping
    public ResponseEntity<Driver> saveDriver(@RequestBody Driver driver) {
        return new ResponseEntity<>(driverService.saveDriver(driver), HttpStatus.CREATED);
    }

    @PostMapping("{id}/bookCar")
    public ResponseEntity<Driver> bookCar(@PathVariable("id") Long driver_id, @RequestParam Long car_id) {
        return new ResponseEntity<>(driverService.bookCar(driver_id, car_id), HttpStatus.OK);
    }

    @PutMapping("{id}/returnCar")
    public ResponseEntity<String> returnCar(@PathVariable("id") Long driver_id) {
        return new ResponseEntity<String>(driverService.returnCar(driver_id), HttpStatus.OK);
    }

    // Build get all drivers REST API
    @GetMapping
    public Page<Driver> getAllDrivers(@RequestParam(defaultValue = "1") int page) {
        return driverService.getAllDrivers(page);
    }

    // Build get driver by specific ID REST API
    // api/drivers/1
    @GetMapping("{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable("id") long id) {
        return new ResponseEntity<Driver>(driverService.getDriver(id), HttpStatus.OK);
    }

    // Build update driver REST API
    @PutMapping("{id}")
    public ResponseEntity<Driver>updateDriver(@PathVariable("id") long id, @RequestBody Driver driver) {
        return new ResponseEntity<Driver>(driverService.updateDriver(driver, id), HttpStatus.OK);
    }

    // Build delete driver REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDriver(@PathVariable("id") long id) {

        String name = driverService.getDriver(id).getFirst_name();

        // Deleting driver from db
        driverService.deleteDriver(id);

        return new ResponseEntity<String>(String.format("Driver %s (ID: %d) deleted successfully!", name, id), HttpStatus.OK);
    }
}
