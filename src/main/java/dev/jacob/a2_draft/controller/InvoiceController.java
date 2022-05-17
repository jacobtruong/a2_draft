package dev.jacob.a2_draft.controller;

import dev.jacob.a2_draft.model.Invoice;
import dev.jacob.a2_draft.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        super();
        this.invoiceService = invoiceService;
    }

    // Build create invoice REST API
    @PostMapping
    public ResponseEntity<Invoice> saveInvoice(@RequestBody Invoice invoice) {
        return new ResponseEntity<>(invoiceService.saveInvoice(invoice), HttpStatus.CREATED);
    }

    // Build get all invoices REST API
    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    // Build get invoice by specific ID REST API
    // api/invoices/1
    @GetMapping("{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable("id") long id) {
        return new ResponseEntity<Invoice>(invoiceService.getInvoice(id), HttpStatus.OK);
    }

    // Build update invoice REST API
    @PutMapping("{id}")
    public ResponseEntity<Invoice>updateInvoice(@PathVariable("id") long id, @RequestBody Invoice invoice) {
        return new ResponseEntity<Invoice>(invoiceService.updateInvoice(invoice, id), HttpStatus.OK);
    }

    // Build delete invoice REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable("id") long id) {
        // Deleting invoice from db
        invoiceService.deleteInvoice(id);

        return new ResponseEntity<String>(String.format("Invoice %d deleted successfully!", id), HttpStatus.OK);
    }
}
