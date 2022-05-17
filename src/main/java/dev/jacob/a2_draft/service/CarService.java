package dev.jacob.a2_draft.service;

import dev.jacob.a2_draft.model.Car;

import java.util.List;

public interface CarService {
    Car saveCar(Car car);
    List<Car> getAllCars();
    Car getCar(long id);
    Car updateCar(Car car, long id);
    void deleteCar(long id);
}