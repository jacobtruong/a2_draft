package dev.jacob.a2_draft.repository;

import dev.jacob.a2_draft.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
