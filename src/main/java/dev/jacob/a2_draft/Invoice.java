package dev.jacob.a2_draft;

import java.util.Objects;

public class Invoice {
    private String id;
    private Customer customer;
    private Driver driver;
    private float total_charge;

    public Invoice() {
    }

    public Invoice(String id, Customer customer, Driver driver, float total_charge) {
        this.id = id;
        this.customer = customer;
        this.driver = driver;
        this.total_charge = total_charge;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public float getTotal_charge() {
        return total_charge;
    }

    public void setTotal_charge(float total_charge) {
        this.total_charge = total_charge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Float.compare(invoice.total_charge, total_charge) == 0 && id.equals(invoice.id) && customer.equals(invoice.customer) && driver.equals(invoice.driver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, driver, total_charge);
    }
}
