package dev.jacob.a2.car;

import org.springframework.data.domain.Page;

import java.util.List;

public interface CarService {
    Car saveCar(Car car);
    Page<Car> getAllCars(int page);
    List<Car> getAllAvailableCars();
    Car getCar(Long id);
    Car updateCar(Car car, Long id);
    String getMonthlyCarStatistics(String month);
    void deleteCar(Long id);
}