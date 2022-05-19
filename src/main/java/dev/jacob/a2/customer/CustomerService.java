package dev.jacob.a2.customer;

import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Page<Customer> getAllCustomers(int page);
    Customer getCustomer(Long id);
    Customer updateCustomer(Customer customer, Long id);
    void deleteCustomer(Long id);
    List<Customer> searchCustomer(Long id, String name, String address, String phone);
}