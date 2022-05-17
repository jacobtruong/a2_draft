package dev.jacob.a2_draft.service;

import dev.jacob.a2_draft.model.Car;

import java.util.List;

public interface CarService {
    Car saveCar(Car car);
    List<Car> getAllCars();
    Car getCar(Long id);
    Car updateCar(Car car, Long id);
    void deleteCar(Long id);
}