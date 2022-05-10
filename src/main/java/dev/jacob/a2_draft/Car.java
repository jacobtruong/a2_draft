package dev.jacob.a2_draft;

import java.util.Objects;

public class Car {
    private String vin;
    private String make;
    private String colour;
    private boolean convertible; // If true, it is a convertible
    private float rating;
    private String license_plate;
    private float rpk; // Rate per Kilometre

    public Car() {
    }

    public Car(String vin, String make, String colour, boolean convertible, float rating, String license_plate, float rpk) {
        this.vin = vin;
        this.make = make;
        this.colour = colour;
        this.convertible = convertible;
        this.rating = rating;
        this.license_plate = license_plate;
        this.rpk = rpk;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public boolean isConvertible() {
        return convertible;
    }

    public void setConvertible(boolean convertible) {
        this.convertible = convertible;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public float getRpk() {
        return rpk;
    }

    public void setRpk(float rpk) {
        this.rpk = rpk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return convertible == car.convertible && Float.compare(car.rating, rating) == 0 && Float.compare(car.rpk, rpk) == 0 && vin.equals(car.vin) && make.equals(car.make) && colour.equals(car.colour) && license_plate.equals(car.license_plate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, make, colour, convertible, rating, license_plate, rpk);
    }
}
