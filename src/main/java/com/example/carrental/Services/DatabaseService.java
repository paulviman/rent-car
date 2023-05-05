package com.example.carrental.Services;

import com.example.carrental.Model.Car;
import com.example.carrental.Model.Client;
import com.example.carrental.Model.Rent;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseService {
    final String DB_URL = "jdbc:postgresql://localhost:5432/rent-car";
    final String USERNAME = "postgres";
    final String PASSWORD = "postgres";

    public ArrayList getAllCars() {
        ArrayList cars = new ArrayList<Car>();


//        final String DB_URL = "jdbc:postgresql://localhost:5432/rent-car";
//        final String USERNAME = "postgres";
//        final String PASSWORD = "postgres";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM car order by brand");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getInt("id"));
                car.setBrand(resultSet.getString("brand"));
                car.setModel(resultSet.getString("model"));
                car.setRegistrationNumber(resultSet.getString("registration_number"));
                car.setYear(resultSet.getInt("year"));
                car.setPricePerDay(resultSet.getInt("price_day"));
                car.setSeats(resultSet.getInt("seats"));
                car.setTransmission(resultSet.getString("transmission"));
                car.setFuelType(resultSet.getString("fuel_type"));
                car.setAvailable(resultSet.getBoolean("is_available"));
                car.setEngineCapacity((resultSet.getInt("engine_capacity")));

                cars.add(car);

            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }

    public Boolean addCarToDatabase(Car car) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("insert into car (brand, model, registration_number, \"year\", price_day, seats, transmission, fuel_type, is_available, \"engine_capacity\")\n" +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getRegistrationNumber());
            statement.setInt(4, car.getYear());
            statement.setInt(5, car.getPricePerDay());
            statement.setInt(6, car.getSeats());
            statement.setString(7, car.getTransmission());
            statement.setString(8, car.getFuelType());
            statement.setBoolean(9, car.isAvailable());
            statement.setInt(10, car.getEngineCapacity());

            int rowInserted = statement.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("Am adaugat cu succes");
                return true;
                // afiseaza mesaj de succes
//                JOptionPane.showMessageDialog(addCarPanel, "The car has been added successfully!");
//                dispose();
            } else {
                // afiseaza mesaj de eroare
//                JOptionPane.showMessageDialog(addCarPanel, "Error adding the car to the database !");
                System.out.println("Nu am putut adauga masina");
                return false;
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList getAllClients() {
        ArrayList clients = new ArrayList<Client>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clients");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setName(resultSet.getString("name"));
                client.setEmail(resultSet.getString("email"));
                client.setPhone(Integer.parseInt(resultSet.getString("phone")));

                clients.add(client);

            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }


    public ArrayList getAllRent() {
        ArrayList rents = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rent");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Rent rent = new Rent();
                rent.setId(resultSet.getInt("id"));
                rent.setClientId(resultSet.getInt("client_id"));
                rent.setCarId(resultSet.getInt("car_id"));
                rent.setStartDateRent(resultSet.getDate("start_date_rent").toLocalDate());
                rent.setEndDaterRent(resultSet.getDate("end_date_rent").toLocalDate());
                rent.setPickUpAddress(resultSet.getString("pick_up_address"));
                rent.setReturnAddress(resultSet.getString("return_address"));
                rent.setTotalPrice(resultSet.getInt("total_price"));

                rents.add(rent);

            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rents;

    }

    public void addClientToDatabase(Client client) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("insert into clients (name, email, phone)\n" +
                    "values (?, ?, ?);");

            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setInt(3, client.getPhone());

            int rowInserted = statement.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("Am adaugat cu succes clientul");
                //  return true;
                // afiseaza mesaj de succes
//                JOptionPane.showMessageDialog(addCarPanel, "The car has been added successfully!");
//                dispose();
            } else {
                // afiseaza mesaj de eroare
//                JOptionPane.showMessageDialog(addCarPanel, "Error adding the car to the database !");
                System.out.println("Nu am putut adauga clientul");
                //return false;
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Client getClient(int clientId) {
        Client client = new Client();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clients where id = ?");
            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                client.setId(resultSet.getInt("id"));
                client.setName(resultSet.getString("name"));
                client.setEmail(resultSet.getString("email"));
                client.setPhone(Integer.parseInt(resultSet.getString("phone")));

            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return client;

    }

    public Car getCar(int carId) {
        Car car = new Car();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM car where id = ?");
            statement.setInt(1, carId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                car.setId(resultSet.getInt("id"));
                car.setBrand(resultSet.getString("brand"));
                car.setModel(resultSet.getString("model"));
                car.setRegistrationNumber(resultSet.getString("registration_number"));
                car.setYear(resultSet.getInt("year"));
                car.setPricePerDay(resultSet.getInt("price_day"));
                car.setSeats(resultSet.getInt("seats"));
                car.setTransmission(resultSet.getString("transmission"));
                car.setFuelType(resultSet.getString("fuel_type"));
                car.setAvailable(resultSet.getBoolean("is_available"));
                car.setEngineCapacity((resultSet.getInt("engine_capacity")));
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return car;
    }
}
