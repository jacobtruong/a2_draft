package dev.jacob.a2;

import dev.jacob.a2.exception.ResourceNotFoundException;
import dev.jacob.a2.customer.Customer;
import dev.jacob.a2.customer.CustomerRepository;
import dev.jacob.a2.customer.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerServiceImplTests {

    @MockBean
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    @Test
    @Rollback(value = false)
    public void testSave() {
        // Create Mock customer
        Customer customer1 = new Customer();
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");


        // Define behaviour of repository
        when (customerRepository.save(customer1)).thenReturn(customer1);

        // Call service method
        Customer actualCustomer = customerService.saveCustomer(customer1);

        // Assert the result
        assertEquals(actualCustomer, customer1);
    }

    @Test
    @Rollback(value = false)
    public void testGetAll() {
        // Create Mock customer
        List<Customer> mockCustomers = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Customer customer2 = new Customer();
        customer2.setFirst_name("Katie");
        customer2.setLast_name("Tran");
        customer2.setPhone_number("0909090909");
        customer2.setEmail("s3878146@rmit.edu.vn");
        customer2.setAddress("M7 Midtown Saigon");

        mockCustomers.add(customer1);
        mockCustomers.add(customer2);

        Page<Customer> mock = new PageImpl<>(mockCustomers);

        // Define behaviour of repository
        when (customerRepository.findAll(PageRequest.of(0, 5))).thenReturn(mock);

        // Call service method
        Page<Customer> actualCustomers = customerService.getAllCustomers(0);

        // Assert the result
        assertEquals(actualCustomers.getContent(), mockCustomers);
    }

    @Test
    @Rollback(value = false)
    public void testGetCustomer() {
        // Create Mock customer
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        // Define behaviour of repository
        when (customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        // Call service method
        Customer actualCustomer = customerService.getCustomer(1L);

        // Assert the result
        assertEquals(customer1, actualCustomer);
        assertThatThrownBy(() -> customerService.getCustomer(2L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @Rollback(value = false)
    public void testUpdateCustomer() {
        // Create Mock customer
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        // Define behaviour of repository
        when (customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        // Create customer2
        Customer customer2 = new Customer();
        customer2.setId(1L);
        customer2.setFirst_name(customer1.getFirst_name());
        customer2.setLast_name(customer1.getLast_name());
        customer2.setPhone_number(customer1.getPhone_number());
        customer2.setEmail(customer1.getEmail());
        customer2.setAddress(customer1.getAddress());


        // Call service method
        Customer actualCustomer = customerService.updateCustomer(customer2, 1L);

        // Assert the result
        assertEquals(customer2, actualCustomer);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteCustomer() {
        // Create Mock customer
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        // Define behaviour of repository
        when (customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        // Call service method
        customerService.deleteCustomer(1L);

        // Assert the result
        assertThatThrownBy(() -> customerService.deleteCustomer(2L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @Rollback(value = false)
    public void testSearchCustomer() {
        // Create Mock customer
        List<Customer> mockCustomers = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirst_name("Jacob");
        customer2.setLast_name("Tran");
        customer2.setPhone_number("0909090909");
        customer2.setEmail("s3878146@rmit.edu.vn");
        customer2.setAddress("M7 Midtown Saigon");

        mockCustomers.add(customer1);
        mockCustomers.add(customer2);

        List<Customer> list = new ArrayList<>();
        list.add(customer1);
        list.add(customer2);

        // Define behaviour of repository
        when (customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when (customerRepository.findById(2L)).thenReturn(Optional.of(customer2));
        when (customerRepository.findAll()).thenReturn(list);

        List<Customer> test_list = new ArrayList<>();


        // Call service method and Assert the result
        // Test searching by ID
        List<Customer> actualCustomers = customerService.searchCustomer(1L, "", "", "");
        test_list.add(customer1);
        assertEquals(test_list, actualCustomers);

        actualCustomers = customerService.searchCustomer(0L, "Jacob", "", "");
        test_list.add(customer2);
        assertEquals(test_list, actualCustomers);
        test_list.remove(customer2);

        actualCustomers = customerService.searchCustomer(0L, "", "Eco Green Saigon", "");
        assertEquals(test_list, actualCustomers);

        actualCustomers = customerService.searchCustomer(0L, "", "", "0963429312");
        assertEquals(test_list, actualCustomers);

        actualCustomers = customerService.searchCustomer(0L, "Jacob", "Eco Green Saigon", "");
        assertEquals(test_list, actualCustomers);

        actualCustomers = customerService.searchCustomer(0L, "", "Eco Green Saigon", "0963429312");
        assertEquals(test_list, actualCustomers);

        actualCustomers = customerService.searchCustomer(0L, "Jacob", "", "0963429312");
        assertEquals(test_list, actualCustomers);

        actualCustomers = customerService.searchCustomer(0L, "Jacob", "Eco Green Saigon", "0963429312");
        assertEquals(test_list, actualCustomers);

        actualCustomers = customerService.searchCustomer(0L, "", "", "");
        assertNull(actualCustomers);
    }

}
