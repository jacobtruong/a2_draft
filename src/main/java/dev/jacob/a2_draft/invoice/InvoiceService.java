package dev.jacob.a2_draft.invoice;

import org.springframework.data.domain.Page;

import java.util.List;

public interface InvoiceService {
    Invoice saveInvoice(Invoice invoice);
    Page<Invoice> getAllInvoices(int page);
    List<Invoice> searchInvoices(Long customer_id, Long driver_id, String start_date, String end_date);

    float getRevenue(Long customer_id, Long driver_id, String start_date, String end_date);

    List<Invoice> searchInvoices(String date, String start_date, String end_date);
    Invoice getInvoice(Long id);
    Invoice updateInvoice(Invoice invoice, Long id);
    void deleteInvoice(Long id);
    Invoice createInvoice(Long customer_id, Long driver_id, float cost);
}