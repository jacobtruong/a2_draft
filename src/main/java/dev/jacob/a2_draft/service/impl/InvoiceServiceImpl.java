package dev.jacob.a2_draft.service.impl;

import dev.jacob.a2_draft.exception.ResourceNotFoundException;
import dev.jacob.a2_draft.model.Customer;
import dev.jacob.a2_draft.model.Driver;
import dev.jacob.a2_draft.model.Invoice;
import dev.jacob.a2_draft.repository.CustomerRepository;
import dev.jacob.a2_draft.repository.DriverRepository;
import dev.jacob.a2_draft.repository.InvoiceRepository;
import dev.jacob.a2_draft.service.InvoiceService;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    public List<Invoice> searchInvoices(String date, String start_date, String end_date) {
        List<Invoice> tmp = new ArrayList<>();

        if (!Objects.equals(date, "")) {
            LocalDate query_date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MM yyyy"));

            for (Invoice invoice : invoiceRepository.findAll()) {
                LocalDate tmp_date = LocalDate.parse(invoice.getDateCreated().toString().substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (tmp_date.isEqual(query_date)) {
                    tmp.add(invoice);
                }
            }

            return tmp;
        }

        if (Objects.equals(start_date, "") && Objects.equals(end_date, "")) {
            return tmp;
        }

        if (!Objects.equals(start_date, "") && Objects.equals(end_date, "")) {
            LocalDate query_date = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("dd MM yyyy"));

            for (Invoice invoice : invoiceRepository.findAll()) {
                LocalDate tmp_date = LocalDate.parse(invoice.getDateCreated().toString().substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (tmp_date.isAfter(query_date) || tmp_date.isEqual(query_date)) {
                    tmp.add(invoice);
                }
            }

        }

        if (!Objects.equals(start_date, "") && !Objects.equals(end_date, "")) {
            LocalDate query_start_date = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("dd MM yyyy"));
            LocalDate query_end_date = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("dd MM yyyy"));

            for (Invoice invoice : invoiceRepository.findAll()) {
                LocalDate tmp_date = LocalDate.parse(invoice.getDateCreated().toString().substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if ((tmp_date.isAfter(query_start_date) || tmp_date.isEqual(query_start_date)) && ((tmp_date.isBefore(query_end_date) || tmp_date.isEqual(query_end_date)))) {
                    tmp.add(invoice);
                }
            }

        }

        if (Objects.equals(start_date, "") && !Objects.equals(end_date, "")) {
            LocalDate query_date = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("dd MM yyyy"));

            for (Invoice invoice : invoiceRepository.findAll()) {
                LocalDate tmp_date = LocalDate.parse(invoice.getDateCreated().toString().substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (tmp_date.isBefore(query_date) || tmp_date.isEqual(query_date)) {
                    tmp.add(invoice);
                }
            }

        }
        
        return tmp;
    }

    @Override
    public List<Invoice> searchInvoices(Long customer_id, Long driver_id, String start_date, String end_date) {
        if (customer_id > 0 && driver_id > 0) {
            throw new RuntimeException("Invalid Params!\n");
        }

        if (Objects.equals(start_date, "") || Objects.equals(end_date, "")) {
            throw new RuntimeException("Invalid Params!\n");
        }

        List<Invoice> tmp = new ArrayList<>();
        List<Invoice> result = new ArrayList<>();

        LocalDate query_start_date = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("dd MM yyyy"));
        LocalDate query_end_date = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("dd MM yyyy"));

        if (customer_id > 0) {
            Customer customer = customerRepository.findById(customer_id).orElseThrow(() -> new ResourceNotFoundException("Customer", "ID", customer_id));
            tmp = customer.getInvoices();
        }

        if (driver_id > 0) {
            Driver driver = driverRepository.findById(driver_id).orElseThrow(() -> new ResourceNotFoundException("Driver", "ID", driver_id));
            tmp = driver.getInvoices();
        }

        for (Invoice invoice : tmp) {
            LocalDate tmp_date = LocalDate.parse(invoice.getDateCreated().toString().substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            if ((tmp_date.isAfter(query_start_date) || tmp_date.isEqual(query_start_date)) && ((tmp_date.isBefore(query_end_date) || tmp_date.isEqual(query_end_date)))) {
                result.add(invoice);
            }
        }

        return result;
    }

    @Override
    public float getRevenue(Long customer_id, Long driver_id, String start_date, String end_date) {
        float revenue = 0;

        List<Invoice> tmp = searchInvoices(customer_id, driver_id, start_date, end_date);

        for (Invoice invoice : tmp) {
            revenue = revenue + invoice.getTotal_charge();
        }

        return revenue;
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
