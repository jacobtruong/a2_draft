package dev.jacob.a2_draft.service.impl;

import dev.jacob.a2_draft.exception.ResourceNotFoundException;
import dev.jacob.a2_draft.model.Customer;
import dev.jacob.a2_draft.repository.CustomerRepository;
import dev.jacob.a2_draft.service.CustomerService;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(Long id) {
//        Optional<Customer> customer = customerRepository.findById(id);
//
//        if(customer.isPresent()) {
//            return customer.get();
//        } else {
//            throw new ResourceNotFoundException("Customer", "ID", id);
//        }

        return customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "ID", id));
    }

    @Override
    public Customer updateCustomer(Customer customer, Long id) {
        // Check if there exists an customer with ID in DB
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "ID", id));

        // Update using data from body
        existingCustomer.setFirst_name(customer.getFirst_name());
        existingCustomer.setLast_name(customer.getLast_name());
        existingCustomer.setEmail(customer.getEmail());

        // Save existing customer to DB
        customerRepository.save(existingCustomer);

        return existingCustomer;
    }

    @Override
    public void deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        // Check if there exists an customer with ID in DB
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
            return;
        } else {
            throw new ResourceNotFoundException("Customer", "ID", id);
        }


    }
}
