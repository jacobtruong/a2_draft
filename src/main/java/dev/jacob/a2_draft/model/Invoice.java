package dev.jacob.a2_draft.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "invoices")
@EntityListeners(AuditingEntityListener.class)
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "customer_id", nullable = false)
    private Long customer_id;

    @Column(name = "driver_id", nullable = false)
    private Long driver_id;

    @Column(name = "total_charge", nullable = false)
    private float total_charge;
}
