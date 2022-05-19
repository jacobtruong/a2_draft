package dev.jacob.a2_draft.service;

import dev.jacob.a2_draft.model.Booking;
import dev.jacob.a2_draft.model.Customer;
import dev.jacob.a2_draft.model.Driver;
import dev.jacob.a2_draft.model.Invoice;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.ParseException;
import java.util.List;

public interface InvoiceService {
    Invoice saveInvoice(Invoice invoice);
    List<Invoice> getAllInvoices();
    List<Invoice> searchInvoices(Long customer_id, Long driver_id, String start_date, String end_date);

    float getRevenue(Long customer_id, Long driver_id, String start_date, String end_date);

    List<Invoice> searchInvoices(String date, String start_date, String end_date);
    Invoice getInvoice(Long id);
    Invoice updateInvoice(Invoice invoice, Long id);
    void deleteInvoice(Long id);
    Invoice createInvoice(Long customer_id, Long driver_id, float cost);
}