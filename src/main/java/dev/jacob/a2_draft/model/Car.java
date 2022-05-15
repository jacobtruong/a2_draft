package dev.jacob.a2_draft.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

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

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private ZonedDateTime dateCreated;
}
