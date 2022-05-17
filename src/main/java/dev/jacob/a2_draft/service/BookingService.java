package dev.jacob.a2_draft.service;

import dev.jacob.a2_draft.model.Booking;

import java.time.ZonedDateTime;
import java.util.List;

public interface BookingService {
    Booking saveBooking(Booking booking);
    List<Booking> getAllBookings();
    Booking getBooking(Long id);
    Booking updateBooking(Booking booking, Long id);
    void deleteBooking(Long id);

    Booking createBooking(Long customer_id, Long car_id, String starting_location, String end_location, ZonedDateTime pick_up_time, ZonedDateTime drop_off_time, float distance);
}