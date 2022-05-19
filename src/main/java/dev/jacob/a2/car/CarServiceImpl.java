package dev.jacob.a2.car;

import dev.jacob.a2.exception.ResourceNotFoundException;
import dev.jacob.a2.booking.Booking;
import dev.jacob.a2.booking.BookingService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    private BookingService bookingService;

    public CarServiceImpl(CarRepository carRepository, BookingService bookingService) {
        super();
        this.carRepository = carRepository;
        this.bookingService = bookingService;
    }

    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Page<Car> getAllCars(int page) {
        if (page < 1) {
            page = 1;
        }
        return carRepository.findAll(PageRequest.of(page - 1, 5));
    }

    @Override
    public List<Car> getAllAvailableCars() {
        List<Car> tmp = new ArrayList<>();
        
        for (Car car : carRepository.findAll()) {
            if (car.isAvailable()) {
                tmp.add(car);
            }
        }

        return tmp;
    }

    @Override
    public Car getCar(Long id) {
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
    public Car updateCar(Car car, Long id) {
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
    public String getMonthlyCarStatistics(String month) {
        if (Objects.equals(month, "")) {
            throw new RuntimeException("No month was inputted!\n");
        }

        String starting_date = "01 " + month;

        LocalDate query_starting_date = LocalDate.parse(starting_date, DateTimeFormatter.ofPattern("dd MM yyyy"));
        LocalDate query_ending_date = query_starting_date.withDayOfMonth(query_starting_date.getMonth().length(query_starting_date.isLeapYear()));

        String end_date = query_ending_date.format(DateTimeFormatter.ofPattern("dd MM yyyy"));

        List<Booking> tmp = bookingService.searchBookings("", starting_date, end_date);

        int month_length = Integer.parseInt(end_date.substring(0,2));

        HashSet<Car>[] cars_2d = new HashSet[month_length];

        for (int i = 0; i < month_length; i++) {
            cars_2d[i] = new HashSet<>();
        }

        

        for (Booking booking : tmp) {
            int index = Integer.parseInt(booking.getDateCreated().toString().substring(8,10));
            Car tmp_car = booking.getCar();
            cars_2d[index].add(tmp_car);
        }

        HashMap<Car, Integer> map = new HashMap<Car, Integer>();

        for (int i = 0; i < month_length; i++) {
            if (cars_2d[i] != null) {
                for (Car car : cars_2d[i]) {
                    if (!map.containsKey(car)) {
                        map.put(car, 1);
                    } else {
                        map.put(car, map.get(car) + 1);
                    }
                }
            }
        }

        Set<Car> cars = map.keySet();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Month: %s\n", month));
        sb.append("Car             |             Days\n");

        for (Car car : cars) {
            sb.append(String.format("%s       |             %d\n", car.getLicense_plate(), map.get(car)));
        }

        return sb.toString();
    }

    @Override
    public void deleteCar(Long id) {
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
