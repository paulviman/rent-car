package com.example.carrental.Model;

import java.time.LocalDate;
import java.util.Date;

public class Rent {
    private int id;
    private int clientId;
    private int carId;
    private LocalDate startDateRent;
    private LocalDate endDaterRent;
    private String pickUpAddress;
    private String returnAddress;
    private float totalPrice;

    private Car rentCar;
    private Client rentClient;

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    private boolean isAvailable;

    public Client getRentClient() {
        return rentClient;
    }

    public void setRentClient(Client client) {
        this.rentClient = client;
    }

    public Car getRentCar() {
        return rentCar;
    }

    public void setRentCar(Car car) {
        this.rentCar = car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public LocalDate getStartDateRent() {
        return startDateRent;
    }

    public void setStartDateRent(LocalDate startDateRent) {
        this.startDateRent = startDateRent;
    }

    public LocalDate getEndDaterRent() {
        return endDaterRent;
    }

    public void setEndDaterRent(LocalDate endDaterRent) {
        this.endDaterRent = endDaterRent;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public String getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(String returnAddress) {
        this.returnAddress = returnAddress;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
