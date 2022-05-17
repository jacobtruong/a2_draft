package dev.jacob.a2_draft.service;

import dev.jacob.a2_draft.model.Invoice;

import java.util.List;

public interface InvoiceService {
    Invoice saveInvoice(Invoice invoice);
    List<Invoice> getAllInvoices();
    Invoice getInvoice(long id);
    Invoice updateInvoice(Invoice invoice, long id);
    void deleteInvoice(long id);
}