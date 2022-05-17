package dev.jacob.a2_draft.controller;

import dev.jacob.a2_draft.model.Booking;
import dev.jacob.a2_draft.model.Invoice;
import dev.jacob.a2_draft.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        super();
        this.bookingService = bookingService;
    }

    // Build create booking REST API
//    @PostMapping
//    public ResponseEntity<Booking> saveBooking(@RequestBody Booking booking) {
//        return new ResponseEntity<>(bookingService.saveBooking(booking), HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestParam(name = "cus_id") Long customer_id, @RequestParam Long car_id,
                                                 @RequestParam(name = "start") String starting_location, @RequestParam(name = "end") String end_location,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime pick_up_time,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime drop_off_time, @RequestParam float distance) {
        return new ResponseEntity<>(bookingService.createBooking(customer_id, car_id, starting_location, end_location, pick_up_time, drop_off_time, distance), HttpStatus.CREATED);
    }

    // Build get all bookings REST API
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Build get booking by specific ID REST API
    // api/bookings/1
    @GetMapping("{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") long id) {
        return new ResponseEntity<Booking>(bookingService.getBooking(id), HttpStatus.OK);
    }

    // Build update booking REST API
    @PutMapping("{id}")
    public ResponseEntity<Booking>updateBooking(@PathVariable("id") long id, @RequestBody Booking booking) {
        return new ResponseEntity<Booking>(bookingService.updateBooking(booking, id), HttpStatus.OK);
    }

    // Build delete booking REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") long id) {
        // Deleting booking from db
        bookingService.deleteBooking(id);

        return new ResponseEntity<String>(String.format("Booking %d deleted successfully!", id), HttpStatus.OK);
    }
}
