package dev.jacob.a2_draft.repository;

import dev.jacob.a2_draft.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
