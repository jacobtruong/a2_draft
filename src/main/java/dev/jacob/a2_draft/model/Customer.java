package dev.jacob.a2_draft.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "email")
    private String email;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private ZonedDateTime dateCreated;
}
