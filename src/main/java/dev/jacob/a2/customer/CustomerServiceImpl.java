package dev.jacob.a2.customer;

import dev.jacob.a2.exception.ResourceNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        var tmp = customerRepository.save(customer);
        return tmp;
    }

    @Override
    public Page<Customer> getAllCustomers(int page) {
        if (page < 1) {
            page = 1;
        }
        return customerRepository.findAll(PageRequest.of(page - 1, 5));
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

    @Override
    public List<Customer> searchCustomer(Long id, String name, String address, String phone) {
        List<Customer> list = new ArrayList<>();

        if (id > 0) {
            list.add(customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "ID", id)));
            return list;
        }
        
        name = name.toUpperCase(Locale.ROOT);
        address = address.toUpperCase(Locale.ROOT);
        
        if (!Objects.equals(name, "") && Objects.equals(address, "") && Objects.equals(phone, "")) {
            for (Customer customer : customerRepository.findAll()) {
                if (Objects.equals(customer.getFirst_name().toUpperCase(Locale.ROOT), name) || Objects.equals(customer.getLast_name().toUpperCase(Locale.ROOT), name)) {
                    list.add(customer);
                }
            }
            return list;
        }

        if (Objects.equals(name, "") && !Objects.equals(address, "") && Objects.equals(phone, "")) {
            for (Customer customer : customerRepository.findAll()) {
                if (Objects.equals(customer.getAddress().toUpperCase(Locale.ROOT), address)) {
                    list.add(customer);
                }
            }
            return list;
        }

        if (Objects.equals(name, "") && Objects.equals(address, "") && !Objects.equals(phone, "")) {
            for (Customer customer : customerRepository.findAll()) {
                if (Objects.equals(customer.getPhone_number(), phone)) {
                    list.add(customer);
                }
            }
            return list;
        }

        if (!Objects.equals(name, "") && !Objects.equals(address, "") && Objects.equals(phone, "")) {
            for (Customer customer : customerRepository.findAll()) {
                if ((Objects.equals(customer.getFirst_name().toUpperCase(Locale.ROOT), name) || Objects.equals(customer.getLast_name().toUpperCase(Locale.ROOT), name)) && Objects.equals(customer.getAddress().toUpperCase(Locale.ROOT), address)) {
                    list.add(customer);
                }
            }
            return list;
        }

        if (!Objects.equals(name, "") && Objects.equals(address, "") && !Objects.equals(phone, "")) {
            for (Customer customer : customerRepository.findAll()) {
                if ((Objects.equals(customer.getFirst_name().toUpperCase(Locale.ROOT), name) || Objects.equals(customer.getLast_name().toUpperCase(Locale.ROOT), name)) && Objects.equals(customer.getPhone_number(), phone)) {
                    list.add(customer);
                }
            }
            return list;
        }

        if (Objects.equals(name, "") && !Objects.equals(address, "") && !Objects.equals(phone, "")) {
            for (Customer customer : customerRepository.findAll()) {
                if (Objects.equals(customer.getAddress().toUpperCase(Locale.ROOT), address) && Objects.equals(customer.getPhone_number(), phone)) {
                    list.add(customer);
                }
            }
            return list;
        }

        if (!Objects.equals(name, "") && !Objects.equals(address, "") && !Objects.equals(phone, "")) {
            for (Customer customer : customerRepository.findAll()) {
                if ((Objects.equals(customer.getFirst_name().toUpperCase(Locale.ROOT), name) || Objects.equals(customer.getLast_name().toUpperCase(Locale.ROOT), name)) && Objects.equals(customer.getAddress().toUpperCase(Locale.ROOT), address) && Objects.equals(customer.getPhone_number(), phone)) {
                    list.add(customer);
                }
            }
            return list;
        }

        return null;
    }
}
