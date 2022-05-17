package dev.jacob.a2_draft.service.impl;

import dev.jacob.a2_draft.exception.ResourceNotFoundException;
import dev.jacob.a2_draft.model.Booking;
import dev.jacob.a2_draft.repository.BookingRepository;
import dev.jacob.a2_draft.service.BookingService;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        super();
        this.bookingRepository = bookingRepository;
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
    public Booking getBooking(long id) {
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
    public Booking updateBooking(Booking booking, long id) {
        // Check if there exists an booking with ID in DB
        Booking existingBooking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking", "ID", id));

        // Update using data from body
        existingBooking.setStarting_location(booking.getStarting_location());
        existingBooking.setEnd_location(booking.getEnd_location());
        existingBooking.setPick_up_time(booking.getPick_up_time());
        existingBooking.setDrop_off_time(booking.getDrop_off_time());
        existingBooking.setDistance(booking.getDistance());
        existingBooking.setInvoice_id(booking.getInvoice_id());

        // Save existing bookings to DB
        bookingRepository.save(existingBooking);

        return existingBooking;
    }

    @Override
    public void deleteBooking(long id) {
        Optional<Booking> booking = bookingRepository.findById(id);

        // Check if there exists an booking with ID in DB
        if (booking.isPresent()) {
            bookingRepository.deleteById(id);
            return;
        } else {
            throw new ResourceNotFoundException("Booking", "ID", id);
        }


    }
}
