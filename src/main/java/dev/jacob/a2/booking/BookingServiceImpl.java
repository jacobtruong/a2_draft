package dev.jacob.a2.booking;

import dev.jacob.a2.car.Car;
import dev.jacob.a2.car.CarRepository;
import dev.jacob.a2.customer.Customer;
import dev.jacob.a2.customer.CustomerRepository;
import dev.jacob.a2.driver.Driver;
import dev.jacob.a2.driver.DriverRepository;
import dev.jacob.a2.exception.ResourceNotFoundException;
import dev.jacob.a2.invoice.Invoice;
import dev.jacob.a2.invoice.InvoiceRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private InvoiceRepository invoiceRepository;
    private CustomerRepository customerRepository;
    private DriverRepository driverRepository;
    private CarRepository carRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, InvoiceRepository invoiceRepository, CustomerRepository customerRepository, DriverRepository driverRepository, CarRepository carRepository) {
        super();
        this.bookingRepository = bookingRepository;
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Page<Booking> getAllBookings(int page) {
        if (page < 1) {
            page = 1;
        }
        return bookingRepository.findAll(PageRequest.of(page - 1, 5));
    }

    @Override
    public List<Booking> searchBookings(String date, String start_date, String end_date) {
        List<Booking> tmp = new ArrayList<>();

        if (!Objects.equals(date, "")) {
            LocalDate query_date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MM yyyy"));

            for (Booking booking : bookingRepository.findAll()) {
                LocalDate tmp_date = LocalDate.parse(booking.getDateCreated().toString().substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (tmp_date.isEqual(query_date)) {
                    tmp.add(booking);
                }
            }

            return tmp;
        }

        if (Objects.equals(start_date, "") && Objects.equals(end_date, "")) {
            return tmp;
        }

        if (!Objects.equals(start_date, "") && Objects.equals(end_date, "")) {
            LocalDate query_date = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("dd MM yyyy"));

            for (Booking booking : bookingRepository.findAll()) {
                LocalDate tmp_date = LocalDate.parse(booking.getDateCreated().toString().substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (tmp_date.isAfter(query_date) || tmp_date.isEqual(query_date)) {
                    tmp.add(booking);
                }
            }

        }

        if (!Objects.equals(start_date, "") && !Objects.equals(end_date, "")) {
            LocalDate query_start_date = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("dd MM yyyy"));
            LocalDate query_end_date = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("dd MM yyyy"));

            for (Booking booking : bookingRepository.findAll()) {
                LocalDate tmp_date = LocalDate.parse(booking.getDateCreated().toString().substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if ((tmp_date.isAfter(query_start_date) || tmp_date.isEqual(query_start_date)) && ((tmp_date.isBefore(query_end_date) || tmp_date.isEqual(query_end_date)))) {
                    tmp.add(booking);
                }
            }

        }

        if (Objects.equals(start_date, "") && !Objects.equals(end_date, "")) {
            LocalDate query_date = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("dd MM yyyy"));

            for (Booking booking : bookingRepository.findAll()) {
                LocalDate tmp_date = LocalDate.parse(booking.getDateCreated().toString().substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (tmp_date.isBefore(query_date) || tmp_date.isEqual(query_date)) {
                    tmp.add(booking);
                }
            }

        }
        return tmp;
    }

    @Override
    public Booking getBooking(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);

        if(booking.isPresent()) {
            return booking.get();
        } else {
            throw new ResourceNotFoundException("Booking", "ID", id);
        }

//        return bookingRepository.findById(id).orElseThrow(() ->
//                new ResourceNotFoundException("Booking", "ID", id));
    }

    @Override
    public Booking updateBooking(Booking booking, Long id) {
        // Check if there exists an booking with ID in DB
        Booking existingBooking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking", "ID", id));

        // Update using data from body
        existingBooking.setStarting_location(booking.getStarting_location());
        existingBooking.setEnd_location(booking.getEnd_location());
        existingBooking.setPick_up_time(booking.getPick_up_time());
        existingBooking.setDrop_off_time(booking.getDrop_off_time());
        existingBooking.setDistance(booking.getDistance());
        existingBooking.setInvoice(booking.getInvoice());

        // Save existing bookings to DB
        bookingRepository.save(existingBooking);

        return existingBooking;
    }

    @Override
    public void deleteBooking(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);

        // Check if there exists an booking with ID in DB
        if (booking.isPresent()) {
            bookingRepository.deleteById(id);
            return;
        } else {
            throw new ResourceNotFoundException("Booking", "ID", id);
        }


    }

    @Override
    public Booking createBooking(Long customer_id, Long car_id, String starting_location, String end_location, float distance) {
        Car car = carRepository.findById(car_id).orElseThrow(() ->
                new ResourceNotFoundException("Car", "ID", car_id));

        if(!car.isAvailable()) {
            throw new RuntimeException(String.format("Car ID %d is unavailable\n", car_id));
        }

        Driver driver = car.getDriver();

        Customer customer = customerRepository.findById(customer_id).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "ID", customer_id));

        Booking booking = new Booking();

        booking.setStarting_location(starting_location);
        booking.setEnd_location(end_location);
        booking.setPick_up_time(ZonedDateTime.now());
        booking.setDistance(distance);

        Invoice invoice = new Invoice();


        invoice.setCustomer(customer);
        invoice.setDriver(driver);
        invoice.setTotal_charge(car.getRpk() * distance);

        car.setAvailable(false);

        booking.setCar(car);

//        invoiceRepository.save(invoice);

        booking.setInvoice(invoice);

        bookingRepository.save(booking);

        return booking;
    }

    @Override
    public Booking finishBooking(Long booking_id) {
        Booking booking = bookingRepository.findById(booking_id).orElseThrow(() -> new ResourceNotFoundException("Booking", "ID", booking_id));

        if (booking.getDrop_off_time() != null) {
            throw new RuntimeException(String.format("Booking %d has already been completed.\n", booking_id));
        }

        booking.setDrop_off_time(ZonedDateTime.now());

        Car car = booking.getInvoice().getDriver().getCar();

        car.setAvailable(true);

        bookingRepository.save(booking);

        return booking;
    }

}
