package dev.jacob.a2_draft;

import java.util.Objects;

public class Customer {
    private String id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;

    public Customer() {
    }

    public Customer(String id, String first_name, String last_name, String phone_number, String email) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) && first_name.equals(customer.first_name) && last_name.equals(customer.last_name) && phone_number.equals(customer.phone_number) && email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, phone_number, email);
    }
}
