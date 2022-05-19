package dev.jacob.a2.car;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.jacob.a2.driver.Driver;
import dev.jacob.a2.booking.Booking;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "cars")
@EntityListeners(AuditingEntityListener.class)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identification_number")
    private String vin;

    @Column(name = "make")
    private String make;

    @Column(name = "colour")
    private String colour;

    @Column(name = "convertible")
    private boolean convertible; // If true, it is a convertible

    @Column(name = "rating")
    private float rating;

    @Column(name = "license_plate")
    private String license_plate;

    @Column(name = "rpk")
    private float rpk; // Rate per Kilometre

    @Column(name = "having_driver")
    private boolean having_driver; // If true, car is booked by a driver

    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(cascade = CascadeType.ALL)
    private Driver driver;

    @OneToMany(mappedBy = "car")
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Booking> bookings;

    @Column(name = "available")
    private boolean available; // Rate per Kilometre

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private ZonedDateTime dateCreated;
}
