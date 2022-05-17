package dev.jacob.a2_draft.service;

import dev.jacob.a2_draft.model.Booking;

import java.util.List;

public interface BookingService {
    Booking saveBooking(Booking booking);
    List<Booking> getAllBookings();
    Booking getBooking(long id);
    Booking updateBooking(Booking booking, long id);
    void deleteBooking(long id);
}