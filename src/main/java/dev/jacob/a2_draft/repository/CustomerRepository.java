package dev.jacob.a2_draft.repository;

import dev.jacob.a2_draft.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
