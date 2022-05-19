package dev.jacob.a2_draft.car;

import dev.jacob.a2_draft.car.Car;
import dev.jacob.a2_draft.car.CarService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        super();
        this.carService = carService;
    }

    // Build create car REST API
    @PostMapping
    public ResponseEntity<Car> saveCar(@RequestBody Car car) {
        return new ResponseEntity<>(carService.saveCar(car), HttpStatus.CREATED);
    }

    // Build get all cars REST API
    @GetMapping
    public Page<Car> getAllCars(@RequestParam(defaultValue = "1") int page) {
        return carService.getAllCars(page);
    }

    @GetMapping("/allAvailable")
    public List<Car> getAllAvailableCars() {
        return carService.getAllAvailableCars();
    }

    @GetMapping("/getMonthlyStatistics")
    public ResponseEntity<String> getMonthlyStatistics(@RequestParam(defaultValue = "") String month) {
        return new ResponseEntity<>(carService.getMonthlyCarStatistics(month), HttpStatus.OK);
    }

    // Build get car by specific ID REST API
    // api/cars/1
    @GetMapping("{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") Long id) {
        return new ResponseEntity<Car>(carService.getCar(id), HttpStatus.OK);
    }

    // Build update car REST API
    @PutMapping("{id}")
    public ResponseEntity<Car>updateCar(@PathVariable("id") Long id, @RequestBody Car car) {
        return new ResponseEntity<Car>(carService.updateCar(car, id), HttpStatus.OK);
    }

    // Build delete car REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCar(@PathVariable("id") Long id) {
        // Deleting car from db
        carService.deleteCar(id);

        return new ResponseEntity<String>(String.format("Car %d deleted successfully!", id), HttpStatus.OK);
    }
}
