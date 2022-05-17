package dev.jacob.a2_draft.service.impl;

import dev.jacob.a2_draft.exception.ResourceNotFoundException;
import dev.jacob.a2_draft.model.Car;
import dev.jacob.a2_draft.repository.CarRepository;
import dev.jacob.a2_draft.service.CarService;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        super();
        this.carRepository = carRepository;
    }

    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCar(long id) {
//        Optional<Car> car = carRepository.findById(id);
//
//        if(car.isPresent()) {
//            return car.get();
//        } else {
//            throw new ResourceNotFoundException("Car", "ID", id);
//        }

        return carRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Car", "ID", id));
    }

    @Override
    public Car updateCar(Car car, long id) {
        // Check if there exists an car with ID in DB
        Car existingCar = carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car", "ID", id));

        // Update using data from body

        existingCar.setVin(car.getVin());
        existingCar.setMake(car.getMake());
        existingCar.setColour(car.getColour());
        existingCar.setConvertible(car.isConvertible());
        existingCar.setRating(car.getRating());
        existingCar.setLicense_plate(car.getLicense_plate());
        existingCar.setRpk(car.getRpk());

        // Save existing cars to DB
        carRepository.save(existingCar);

        return existingCar;
    }

    @Override
    public void deleteCar(long id) {
        Optional<Car> car = carRepository.findById(id);

        // Check if there exists an car with ID in DB
        if (car.isPresent()) {
            carRepository.deleteById(id);
            return;
        } else {
            throw new ResourceNotFoundException("Car", "ID", id);
        }


    }
}
