package dev.jacob.a2_draft.service;

import dev.jacob.a2_draft.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomer(long id);
    Customer updateCustomer(Customer customer, long id);
    void deleteCustomer(long id);
}