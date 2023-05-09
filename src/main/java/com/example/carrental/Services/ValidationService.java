package com.example.carrental.Services;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService {

    public static boolean emailValidation(String email) {
        Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
                ")*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public boolean nameValidation(String name) {
        Pattern pattern = Pattern.compile("^[a-zA-Z\\s]*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    public boolean phoneValidation(String phone) {
        Pattern pattern = Pattern.compile("^\\+?[0-9]{10,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phone);
        return matcher.find();
    }

    public boolean addressValidation(String address) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\s,-]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(address);
        return matcher.find();
    }

    public boolean passwordValidation(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,20}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
    public boolean brandValidation(String brand) {
        Pattern pattern = Pattern.compile("^[a-zA-Z\\s]*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(brand);
        return matcher.find();
    }

    public boolean modelValidation(String model) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\s]*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(model);
        return matcher.find();
    }

    public boolean regNumbValidation(String regNumb) {
        Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{3}[A-Z]{3}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(regNumb);
        return matcher.find();
    }

    public boolean yearValidation(String year) {
        Pattern pattern = Pattern.compile("^\\d{4}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(year);
        return matcher.find();
    }

    public boolean priceValidation(String priceDay) {
        Pattern pattern = Pattern.compile("^\\d+(?:\\.\\d+)?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(priceDay);
        return matcher.find();
    }

    public boolean seatsValidation(String seats) {
        Pattern pattern = Pattern.compile("^[1-9][0-9]*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(seats);
        return matcher.find();
    }

    public boolean transmissionValidation(String transmission) {
        Pattern pattern = Pattern.compile("^(manual|automat)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(transmission);
        return matcher.find();
    }

    public boolean fuelTypeValidation(String fuelType) {
        Pattern pattern = Pattern.compile("^(disel|benzina|electric)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fuelType);
        return matcher.find();
    }

    public boolean engineCapacityValidation(String engineCapacity) {
        Pattern pattern = Pattern.compile("^[0-9]+([.][0-9]+)?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(engineCapacity);
        return matcher.find();
    }

    public boolean dateValidation(LocalDate pickUpDate, LocalDate returnDate) {
        LocalDate currentDate = LocalDate.now();
        if(pickUpDate.isAfter(currentDate) && returnDate.isAfter(pickUpDate)){
            System.out.println("date valide");
            return true;
        }else {
            System.out.println("date invalide");
            return false;
        }
    }
}
