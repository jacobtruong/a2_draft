package dev.jacob.a2_draft.service.impl;

import dev.jacob.a2_draft.exception.ResourceNotFoundException;
import dev.jacob.a2_draft.model.Invoice;
import dev.jacob.a2_draft.repository.InvoiceRepository;
import dev.jacob.a2_draft.service.InvoiceService;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        super();
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getInvoice(long id) {
//        Optional<Invoice> invoice = invoiceRepository.findById(id);
//
//        if(invoice.isPresent()) {
//            return invoice.get();
//        } else {
//            throw new ResourceNotFoundException("Invoice", "ID", id);
//        }

        return invoiceRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Invoice", "ID", id));
    }

    @Override
    public Invoice updateInvoice(Invoice invoice, long id) {
        // Check if there exists an invoice with ID in DB
        Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice", "ID", id));

        // Update using data from body
        existingInvoice.setCustomer_id(invoice.getCustomer_id());
        existingInvoice.setDriver_id(invoice.getDriver_id());
        existingInvoice.setTotal_charge(invoice.getTotal_charge());

        // Save existing invoice to DB
        invoiceRepository.save(existingInvoice);

        return existingInvoice;
    }

    @Override
    public void deleteInvoice(long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);

        // Check if there exists an invoice with ID in DB
        if (invoice.isPresent()) {
            invoiceRepository.deleteById(id);
            return;
        } else {
            throw new ResourceNotFoundException("Invoice", "ID", id);
        }


    }
}
