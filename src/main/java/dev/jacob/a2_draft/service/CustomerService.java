package dev.jacob.a2_draft.service;

import dev.jacob.a2_draft.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomer(Long id);
    Customer updateCustomer(Customer customer, Long id);
    void deleteCustomer(Long id);
}