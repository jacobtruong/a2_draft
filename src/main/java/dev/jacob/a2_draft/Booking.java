package dev.jacob.a2_draft;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Booking {
    private String id;
    private String starting_location;
    private String end_location;
    private ZonedDateTime pick_up_time;
    private ZonedDateTime drop_off_time;
    private float distance;
    private Invoice invoice;

    public Booking() {

    }

    public Booking(String id, String starting_location, String end_location, ZonedDateTime pick_up_time, ZonedDateTime drop_off_time, float distance, Invoice invoice) {
        this.id = id;
        this.starting_location = starting_location;
        this.end_location = end_location;
        this.pick_up_time = pick_up_time;
        this.drop_off_time = drop_off_time;
        this.distance = distance;
        this.invoice = invoice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStarting_location() {
        return starting_location;
    }

    public void setStarting_location(String starting_location) {
        this.starting_location = starting_location;
    }

    public String getEnd_location() {
        return end_location;
    }

    public void setEnd_location(String end_location) {
        this.end_location = end_location;
    }

    public ZonedDateTime getPick_up_time() {
        return pick_up_time;
    }

    public void setPick_up_time(ZonedDateTime pick_up_time) {
        this.pick_up_time = pick_up_time;
    }

    public ZonedDateTime getDrop_off_time() {
        return drop_off_time;
    }

    public void setDrop_off_time(ZonedDateTime drop_off_time) {
        this.drop_off_time = drop_off_time;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Float.compare(booking.distance, distance) == 0 && id.equals(booking.id) && starting_location.equals(booking.starting_location) && end_location.equals(booking.end_location) && pick_up_time.equals(booking.pick_up_time) && drop_off_time.equals(booking.drop_off_time) && invoice.equals(booking.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, starting_location, end_location, pick_up_time, drop_off_time, distance, invoice);
    }
}
