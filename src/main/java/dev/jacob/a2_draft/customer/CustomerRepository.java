package dev.jacob.a2_draft.customer;

import dev.jacob.a2_draft.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
