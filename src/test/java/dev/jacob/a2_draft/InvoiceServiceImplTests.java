package dev.jacob.a2_draft;

import dev.jacob.a2_draft.exception.ResourceNotFoundException;
import dev.jacob.a2_draft.model.*;
import dev.jacob.a2_draft.model.Invoice;
import dev.jacob.a2_draft.repository.*;
import dev.jacob.a2_draft.repository.InvoiceRepository;
import dev.jacob.a2_draft.service.impl.BookingServiceImpl;
import dev.jacob.a2_draft.service.impl.InvoiceServiceImpl;
import dev.jacob.a2_draft.service.impl.InvoiceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InvoiceServiceImplTests {

    @MockBean
    InvoiceRepository invoiceRepository;

    @InjectMocks
    InvoiceServiceImpl invoiceService;

    @Test
    @Rollback(value = false)
    public void testSave() {
        // Create Mock invoice
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);
        driver1.setCar(car1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        // Define behaviour of repository
        when (invoiceRepository.save(invoice1)).thenReturn(invoice1);

        // Call service method
        Invoice actualInvoice = invoiceService.saveInvoice(invoice1);

        // Assert the result
        assertEquals(actualInvoice, invoice1);
    }

    @Test
    @Rollback(value = false)
    public void testGetAll() {
        // Create Mock invoice
        List<Invoice> mockInvoices = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);
        driver1.setCar(car1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Invoice invoice2 = new Invoice();
        invoice2.setId(2L);
        invoice2.setCustomer(customer1);
        invoice2.setDriver(driver1);
        invoice2.setTotal_charge(15 * car1.getRpk());
        invoice2.setDateCreated(ZonedDateTime.now());

        mockInvoices.add(invoice1);
        mockInvoices.add(invoice2);

        // Define behaviour of repository
        when (invoiceRepository.findAll()).thenReturn(mockInvoices);

        // Call service method
        List<Invoice> actualInvoices = invoiceService.getAllInvoices();

        // Assert the result
        assertEquals(actualInvoices, mockInvoices);
    }

    @Test
    @Rollback(value = false)
    public void testSearchInvoices() {
        // Create Mock invoice
        List<Invoice> mockInvoices = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);
        driver1.setCar(car1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Invoice invoice2 = new Invoice();
        invoice2.setId(2L);
        invoice2.setCustomer(customer1);
        invoice2.setDriver(driver1);
        invoice2.setTotal_charge(15 * car1.getRpk());
        invoice2.setDateCreated(ZonedDateTime.now());

        mockInvoices.add(invoice1);
        mockInvoices.add(invoice2);

        String date = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd MM yyyy"));

        // Define behaviour of repository
        when (invoiceRepository.findAll()).thenReturn(mockInvoices);

        // Call service method
        List<Invoice> actualInvoices = invoiceService.searchInvoices(date, "", "");
        assertEquals(actualInvoices, mockInvoices);

        actualInvoices = invoiceService.searchInvoices("", date, "");
        assertEquals(actualInvoices, mockInvoices);

        actualInvoices = invoiceService.searchInvoices("", "", date);
        assertEquals(actualInvoices, mockInvoices);

        actualInvoices = invoiceService.searchInvoices("", date, date);
        assertEquals(actualInvoices, mockInvoices);

        actualInvoices = invoiceService.searchInvoices("", "", "");
        mockInvoices.clear();
        assertEquals(actualInvoices, mockInvoices);
    }

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    DriverRepository driverRepository;

    @Test
    @Rollback(value = false)
    public void testSearchInvoicesByID() {
        // Create Mock invoice
        List<Invoice> mockInvoices = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);
        driver1.setCar(car1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Invoice invoice2 = new Invoice();
        invoice2.setId(2L);
        invoice2.setCustomer(customer1);
        invoice2.setDriver(driver1);
        invoice2.setTotal_charge(15 * car1.getRpk());
        invoice2.setDateCreated(ZonedDateTime.now());

        Invoice invoice3 = new Invoice();
        invoice3.setId(3L);
        invoice3.setCustomer(customer1);
        invoice3.setDriver(driver1);
        invoice3.setTotal_charge(20 * car1.getRpk());
        invoice3.setDateCreated(ZonedDateTime.now());

        mockInvoices.add(invoice1);
        mockInvoices.add(invoice2);
        mockInvoices.add(invoice3);

        customer1.setInvoices(new ArrayList<>());
        customer1.getInvoices().add(invoice1);
        customer1.getInvoices().add(invoice2);
        customer1.getInvoices().add(invoice3);

        driver1.setInvoices(new ArrayList<>());
        driver1.getInvoices().add(invoice1);
        driver1.getInvoices().add(invoice2);
        driver1.getInvoices().add(invoice3);

        String date = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd MM yyyy"));

        // Define behaviour of repository
        when (customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when (driverRepository.findById(1L)).thenReturn(Optional.of(driver1));

        // Call service method
        assertThatThrownBy(() -> invoiceService.searchInvoices(1L,1L,"", "")).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> invoiceService.searchInvoices(1L,0L,"", "")).isInstanceOf(RuntimeException.class);

        List<Invoice> actualInvoices = invoiceService.searchInvoices(1L,0L,date, date);
        assertEquals(actualInvoices, mockInvoices);

        actualInvoices = invoiceService.searchInvoices(0L,1L,date, date);
        assertEquals(actualInvoices, mockInvoices);
    }

    @Test
    @Rollback(value = false)
    public void testGetRevenue() {
        // Create Mock invoice
        List<Invoice> mockInvoices = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);
        driver1.setCar(car1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        Invoice invoice2 = new Invoice();
        invoice2.setId(2L);
        invoice2.setCustomer(customer1);
        invoice2.setDriver(driver1);
        invoice2.setTotal_charge(15 * car1.getRpk());
        invoice2.setDateCreated(ZonedDateTime.now());

        Invoice invoice3 = new Invoice();
        invoice3.setId(3L);
        invoice3.setCustomer(customer1);
        invoice3.setDriver(driver1);
        invoice3.setTotal_charge(20 * car1.getRpk());
        invoice3.setDateCreated(ZonedDateTime.now());

        mockInvoices.add(invoice1);
        mockInvoices.add(invoice2);
        mockInvoices.add(invoice3);

        customer1.setInvoices(new ArrayList<>());
        customer1.getInvoices().add(invoice1);
        customer1.getInvoices().add(invoice2);
        customer1.getInvoices().add(invoice3);

        driver1.setInvoices(new ArrayList<>());
        driver1.getInvoices().add(invoice1);
        driver1.getInvoices().add(invoice2);
        driver1.getInvoices().add(invoice3);

        String date = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd MM yyyy"));

        // Define behaviour of repository
        when (customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when (driverRepository.findById(1L)).thenReturn(Optional.of(driver1));

        // Call service method
        assertEquals(112.5f, invoiceService.getRevenue(1L, 0L, date, date));
    }

    @Test
    @Rollback(value = false)
    public void testGetInvoice() {
        // Create Mock invoice
        List<Invoice> mockInvoices = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        customer1.setInvoices(new ArrayList<>());
        customer1.getInvoices().add(invoice1);

        driver1.setInvoices(new ArrayList<>());
        driver1.getInvoices().add(invoice1);

        String date = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd MM yyyy"));

        // Define behaviour of repository
        when (invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice1));

        // Call service method
        assertEquals(invoice1, invoiceService.getInvoice(1L));
        assertThatThrownBy(() -> invoiceService.getInvoice(2L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @Rollback(value = false)
    public void testUpdateInvoice() {
        // Create Mock invoice
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        // Define behaviour of repository
        when (invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice1));

        // Create invoice2
        Invoice invoice2 = new Invoice();
        invoice2.setId(1L);
        invoice2.setCustomer(customer1);
        invoice2.setDriver(driver1);
        invoice2.setTotal_charge(15 * car1.getRpk());
        invoice2.setDateCreated(ZonedDateTime.now());

        // Call service method
        Invoice actualInvoice = invoiceService.updateInvoice(invoice2, 1L);

        // Assert the result
        assertEquals(invoice2, actualInvoice);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteInvoice() {
        // Create Mock invoice
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());
        invoice1.setDateCreated(ZonedDateTime.now());

        customer1.setInvoices(new ArrayList<>());
        customer1.getInvoices().add(invoice1);

        driver1.setInvoices(new ArrayList<>());
        driver1.getInvoices().add(invoice1);

        // Define behaviour of repository
        when (invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice1));

        // Call service method
        invoiceService.deleteInvoice(1L);

        // Assert the result
        assertThatThrownBy(() -> invoiceService.deleteInvoice(2L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @Rollback(value = false)
    public void testCreateInvoice() {
        // Create Mock invoice
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirst_name("Jacob");
        customer1.setLast_name("Truong");
        customer1.setPhone_number("0963429312");
        customer1.setEmail("s3878145@rmit.edu.vn");
        customer1.setAddress("Eco Green Saigon");

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFirst_name("Valentino");
        driver1.setLast_name("Rossi");
        driver1.setPhone_number("0909090946");
        driver1.setLicense_number("123456789012");
        driver1.setRating(4.67f);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setVin("1ZVHT82H485113456");
        car1.setMake("Liberty");
        car1.setColour("Grey");
        car1.setConvertible(false);
        car1.setRating(4.23f);
        car1.setLicense_plate("50E-23122");
        car1.setRpk(2.5f);
        car1.setHaving_driver(true);
        car1.setAvailable(true);
        car1.setDriver(driver1);

        Invoice invoice1 = new Invoice();
        invoice1.setCustomer(customer1);
        invoice1.setDriver(driver1);
        invoice1.setTotal_charge(10 * car1.getRpk());

        // Define behaviour of repository
        when (customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when (driverRepository.findById(1L)).thenReturn(Optional.of(driver1));

        Invoice invoice_tmp = invoiceService.createInvoice(1L, 1L, 10 * car1.getRpk());

        // Call service method
        assertEquals(invoice1, invoice_tmp);
    }
}
