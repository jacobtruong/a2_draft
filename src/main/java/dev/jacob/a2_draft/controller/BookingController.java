package dev.jacob.a2_draft.controller;

import dev.jacob.a2_draft.model.Booking;
import dev.jacob.a2_draft.model.Invoice;
import dev.jacob.a2_draft.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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

//     Build create booking REST API
    @PostMapping
    public ResponseEntity<Booking> saveBooking(@RequestBody Booking booking) {
        return new ResponseEntity<>(bookingService.saveBooking(booking), HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestParam(name = "cus_id") Long customer_id, @RequestParam Long car_id,
                                                 @RequestParam(name = "start") String starting_location, @RequestParam(name = "end") String end_location, @RequestParam float distance) {
        return new ResponseEntity<>(bookingService.createBooking(customer_id, car_id, starting_location, end_location, distance), HttpStatus.CREATED);
    }

    @PutMapping("/finishBooking")
    public ResponseEntity<Booking> finishBooking(@RequestParam Long booking_id) {
        return new ResponseEntity<>(bookingService.finishBooking(booking_id), HttpStatus.OK);
    }

    // Build get all bookings REST API
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/searchAll")
    public ResponseEntity<List<Booking>> searchInvoice(@RequestParam(defaultValue = "") String date, @RequestParam(defaultValue = "") String start_date, @RequestParam(defaultValue = "") String end_date) throws ParseException {
        return new ResponseEntity<List<Booking>>(bookingService.searchBookings(date, start_date, end_date), HttpStatus.OK);
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
