package dev.jacob.a2_draft.service.impl;

import dev.jacob.a2_draft.exception.ResourceNotFoundException;
import dev.jacob.a2_draft.model.*;
import dev.jacob.a2_draft.repository.*;
import dev.jacob.a2_draft.service.BookingService;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.ZonedDateTime;
import java.util.List;
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
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBooking(Long id) {
//        Optional<Booking> booking = bookingRepository.findById(id);
//
//        if(booking.isPresent()) {
//            return booking.get();
//        } else {
//            throw new ResourceNotFoundException("Booking", "ID", id);
//        }

        return bookingRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Booking", "ID", id));
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
    public Booking createBooking(Long customer_id, Long car_id, String starting_location, String end_location, ZonedDateTime pick_up_time, ZonedDateTime drop_off_time, float distance) {
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
        booking.setPick_up_time(pick_up_time);
        booking.setDrop_off_time(drop_off_time);
        booking.setDistance(distance);

        Invoice invoice = new Invoice();


        invoice.setCustomer(customer);
        invoice.setDriver(driver);
        invoice.setTotal_charge(car.getRpk() * distance);

        car.setAvailable(false);

//        invoiceRepository.save(invoice);

        booking.setInvoice(invoice);

        bookingRepository.save(booking);

        return booking;
    }
}
