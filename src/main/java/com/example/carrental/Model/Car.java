package com.example.carrental.Model;


public class Car {
    private int id;
    private String brand;
    private String model;
    private String registrationNumber;
    private int year;
    private int pricePerDay;
    private int seats;
    private String transmission;
    private String fuelType;
    private boolean isAvailable;



    private int engineCapacity;

    public Car(int id, String brand, String model, String registrationNumber, int year, int pricePerDay, int seats, String transmission, String fuelType, boolean isAvailable, int engineCapacity) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.seats = seats;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.isAvailable = isAvailable;
        this.engineCapacity=engineCapacity;
    }

    public Car() {

    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }
}