package dev.jacob.a2_draft.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "bookings")
@EntityListeners(AuditingEntityListener.class)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "starting_location")
    private String starting_location;

    @Column(name = "end_location")
    private String end_location;

    @Column(name = "pick_up_time")
    private ZonedDateTime pick_up_time;

    @Column(name = "drop_off_time")
    private ZonedDateTime drop_off_time;

    @Column(name = "distance")
    private float distance;

    @Column(name = "invoice_id")
    private Long invoice_id;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private ZonedDateTime dateCreated;
}
