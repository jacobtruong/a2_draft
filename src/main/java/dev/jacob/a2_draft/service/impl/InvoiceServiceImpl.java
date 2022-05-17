package dev.jacob.a2_draft.service.impl;

import dev.jacob.a2_draft.exception.ResourceNotFoundException;
import dev.jacob.a2_draft.model.Invoice;
import dev.jacob.a2_draft.repository.CustomerRepository;
import dev.jacob.a2_draft.repository.DriverRepository;
import dev.jacob.a2_draft.repository.InvoiceRepository;
import dev.jacob.a2_draft.service.InvoiceService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository;
    private CustomerRepository customerRepository;
    private DriverRepository driverRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, CustomerRepository customerRepository, DriverRepository driverRepository) {
        super();
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
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
    public Invoice getInvoice(Long id) {
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
    public Invoice updateInvoice(Invoice invoice, Long id) {
        // Check if there exists an invoice with ID in DB
        Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice", "ID", id));

        // Update using data from body
        existingInvoice.setCustomer(invoice.getCustomer());
        existingInvoice.setDriver(invoice.getDriver());
        existingInvoice.setTotal_charge(invoice.getTotal_charge());

        // Save existing invoice to DB
        invoiceRepository.save(existingInvoice);

        return existingInvoice;
    }

    @Override
    public void deleteInvoice(Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);

        // Check if there exists an invoice with ID in DB
        if (invoice.isPresent()) {
            invoiceRepository.deleteById(id);
            return;
        } else {
            throw new ResourceNotFoundException("Invoice", "ID", id);
        }
    }

    @Override
    public Invoice createInvoice(Long customer_id, Long driver_id, float cost) {
        Invoice invoice = new Invoice();

        invoice.setCustomer(customerRepository.getById(customer_id));
        invoice.setDriver(driverRepository.getById(driver_id));
        invoice.setTotal_charge(cost);

        invoiceRepository.save(invoice);

        return invoice;
    }
}
