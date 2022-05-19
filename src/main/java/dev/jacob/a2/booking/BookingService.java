package dev.jacob.a2.booking;

import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {
    Booking saveBooking(Booking booking);
    Page<Booking> getAllBookings(int page);
    List<Booking> searchBookings(String date, String start_date, String end_date);
    Booking getBooking(Long id);
    Booking updateBooking(Booking booking, Long id);
    void deleteBooking(Long id);
    Booking createBooking(Long customer_id, Long car_id, String starting_location, String end_location, float distance);
    Booking finishBooking(Long booking_id);
}