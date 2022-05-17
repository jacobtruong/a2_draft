package dev.jacob.a2_draft.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "drivers")
@EntityListeners(AuditingEntityListener.class)
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "license_number")
    private String license_number;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "rating")
    private float rating;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private ZonedDateTime dateCreated;
}
