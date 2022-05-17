package dev.jacob.a2_draft.service;

import dev.jacob.a2_draft.model.Customer;
import dev.jacob.a2_draft.model.Driver;
import dev.jacob.a2_draft.model.Invoice;

import java.util.List;

public interface InvoiceService {
    Invoice saveInvoice(Invoice invoice);
    List<Invoice> getAllInvoices();
    Invoice getInvoice(Long id);
    Invoice updateInvoice(Invoice invoice, Long id);
    void deleteInvoice(Long id);
    Invoice createInvoice(Long customer_id, Long driver_id, float cost);
}