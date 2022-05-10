package dev.jacob.a2_draft;

import java.util.Objects;

public class Driver {
    private String id;
    private String first_name;
    private String last_name;
    private String license_number;
    private String phone_number;
    private float rating;

    public Driver() {
    }

    public Driver(String id, String first_name, String last_name, String license_number, String phone_number, float rating) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.license_number = license_number;
        this.phone_number = phone_number;
        this.rating = rating;
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

    public String getLicense_number() {
        return license_number;
    }

    public void setLicense_number(String license_number) {
        this.license_number = license_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Float.compare(driver.rating, rating) == 0 && id.equals(driver.id) && first_name.equals(driver.first_name) && last_name.equals(driver.last_name) && license_number.equals(driver.license_number) && phone_number.equals(driver.phone_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, license_number, phone_number, rating);
    }
}
