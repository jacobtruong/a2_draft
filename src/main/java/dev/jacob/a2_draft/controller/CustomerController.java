package dev.jacob.a2_draft.controller;

import dev.jacob.a2_draft.model.Customer;
import dev.jacob.a2_draft.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        super();
        this.customerService = customerService;
    }

    // Build create customer REST API
    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
    }

    // Build get all customers REST API
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Build get customer by specific ID REST API
    // api/customers/1
    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id) {
        return new ResponseEntity<Customer>(customerService.getCustomer(id), HttpStatus.OK);
    }

    // Build update customer REST API
    @PutMapping("{id}")
    public ResponseEntity<Customer>updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
        return new ResponseEntity<Customer>(customerService.updateCustomer(customer, id), HttpStatus.OK);
    }

    // Build delete customer REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") long id) {

        String name = customerService.getCustomer(id).getFirst_name();

        // Deleting customer from db
        customerService.deleteCustomer(id);

        return new ResponseEntity<String>(String.format("Customer %s (ID: %d) deleted successfully!", name, id), HttpStatus.OK);
    }
}
