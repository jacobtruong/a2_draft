package dev.jacob.a2_draft.booking;

import dev.jacob.a2_draft.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
