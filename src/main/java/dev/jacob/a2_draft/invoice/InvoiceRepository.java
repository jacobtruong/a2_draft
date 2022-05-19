package dev.jacob.a2_draft.invoice;

import dev.jacob.a2_draft.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
