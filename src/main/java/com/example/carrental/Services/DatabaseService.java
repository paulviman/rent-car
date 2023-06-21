package com.example.carrental.Services;

import com.example.carrental.Model.Car;
import com.example.carrental.Model.Client;
import com.example.carrental.Model.Rent;
import com.example.carrental.Model.User;
import com.example.carrental.Services.Encrypt;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseService {
    final String DB_URL = "jdbc:postgresql://localhost:5432/rent-car";
    final String USERNAME = "postgres";
    final String PASSWORD = "postgres";

    Encrypt encrypt = new Encrypt();

    public void updateCarAvailabilityDaily() {
        LocalDate currentDate = LocalDate.now();
        System.out.println("Am actualizat tabela de masini");

        ArrayList<Rent> rents = this.getAllRent(null, null);
        for (Rent rent : rents) {
            if (rent.isAvailable()) {
                if (rent.getEndDaterRent().isBefore(currentDate)) {
                    int carId = rent.getCarId();
                    this.setCarAvailability(carId, true);
                    //this.setRentAvailability(rent.getId(), false);
                    // setRentAvailability(rent.getId(),false);
                }
            }
        }
    }

    public void setRentAvailability(int id, boolean b) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("UPDATE rent SET is_available = ? WHERE id = ?");

            statement.setBoolean(1, b);
            statement.setInt(2, id);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void setCarAvailability(int carId, boolean b) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("UPDATE car SET is_available = ? WHERE id = ?");

            statement.setBoolean(1, b);
            statement.setInt(2, carId);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

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

    public boolean addCarToDatabase(Car car) {
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
            statement.setFloat(10, car.getEngineCapacity());

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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clients ORDER BY name");
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


    public ArrayList getAllRent(ArrayList<Car> cars, ArrayList<Client> clients) {
        ArrayList rents = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rent order by id desc ");
            ResultSet resultSet = statement.executeQuery();
            Rent rent;
            while (resultSet.next()) {
                rent = new Rent();
                rent.setId(resultSet.getInt("id"));
                rent.setClientId(resultSet.getInt("client_id"));
                rent.setCarId(resultSet.getInt("car_id"));
                rent.setStartDateRent(resultSet.getDate("start_date_rent").toLocalDate());
                rent.setEndDaterRent(resultSet.getDate("end_date_rent").toLocalDate());
                rent.setPickUpAddress(resultSet.getString("pick_up_address"));
                rent.setReturnAddress(resultSet.getString("return_address"));
                rent.setTotalPrice(resultSet.getInt("total_price"));
                rent.setAvailable(resultSet.getBoolean("is_available"));

                if ((cars != null) && (clients != null)) {
                    for (Car car : cars) {
                        if (rent.getCarId() == car.getId()) {
                            rent.setRentCar(car);
                            System.out.println(rent.getRentCar().getRegistrationNumber() + car.getId());
                            break;
                        }
                    }
                    for (Client client : clients) {
                        if (rent.getClientId() == client.getId()) {
                            rent.setRentClient(client);
                            System.out.println(rent.getRentClient().getName() + client.getId());
                            break;
                        }
                    }
                }

                rents.add(rent);

            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rents;

    }

    public boolean addClientToDatabase(Client client) {
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
                return true;
                // afiseaza mesaj de succes
//                JOptionPane.showMessageDialog(addCarPanel, "The car has been added successfully!");
//                dispose();
            } else {
                // afiseaza mesaj de eroare
//                JOptionPane.showMessageDialog(addCarPanel, "Error adding the car to the database !");
                System.out.println("Nu am putut adauga clientul");
                return false;
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

    public boolean saveRent(Rent newRent) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("insert into rent (client_id, car_id, start_date_rent, end_date_rent, pick_up_address, return_address, total_price,is_available)\n" +
                    "values (?, ?, ?, ?, ?, ?, ?, ?);");

            statement.setInt(1, newRent.getClientId());
            statement.setInt(2, newRent.getCarId());
            statement.setDate(3, java.sql.Date.valueOf(newRent.getStartDateRent()));
            statement.setDate(4, java.sql.Date.valueOf(newRent.getEndDaterRent()));
            statement.setString(5, newRent.getPickUpAddress());
            statement.setString(6, newRent.getReturnAddress());
            statement.setInt(7, (int) newRent.getTotalPrice());
            statement.setBoolean(8, true);


            int rowInserted = statement.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("Am adaugat cu succes rent");
                return true;
                // afiseaza mesaj de succes
//                JOptionPane.showMessageDialog(addCarPanel, "The car has been added successfully!");
//                dispose();
            } else {
                // afiseaza mesaj de eroare
//                JOptionPane.showMessageDialog(addCarPanel, "Error adding the car to the database !");
                System.out.println("Nu am putut adauga rent");
                return false;
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public boolean getUserEmail(String email) {
        User user = new User();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users where email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                user.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (user.getEmail() == null) {
            return false;
        } else return user.getEmail().equals(email);
    }

    public User getAuthenticatedUserFromDB(String email, String password) {
        User user1 = null;

//        final String DB_URL = "jdbc:postgresql://localhost:5432/rent-car";
//        final String USERNAME = "postgres";
//        final String PASSWORD = "postgres";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, encrypt.encrypt(password));
            //statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user1 = new User();
                user1.id = resultSet.getInt("id");
                user1.name = resultSet.getString("name");
                user1.email = resultSet.getString("email");
                user1.phone = resultSet.getString("phone");
                user1.address = resultSet.getString("address");
                user1.role = resultSet.getInt("role");
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user1;
    }

    public User addUserToDB(String name, String email, String phone, String address, String password) {
        User user = null;
//        final String DB_URL = "jdbc:postgresql://localhost:5432/rent-car";
//        final String USERNAME = "postgres";
//        final String PASSWORD = "postgres";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (name, email, phone, address, password)" +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, password);
            //preparedStatement.setString(5, role);

            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPhone(phone);
                user.setPassword(password);

//                user.name = name;
//                user.email = email;
//                user.phone = phone;
//                user.address = address;
//                user.password = password;
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public ArrayList<Car> getAllCarsAvailable() {
        ArrayList cars = new ArrayList<Car>();

//        final String DB_URL = "jdbc:postgresql://localhost:5432/rent-car";
//        final String USERNAME = "postgres";
//        final String PASSWORD = "postgres";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM car where is_available = true order by brand ");
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


    public void deleteCar(int carId) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM car WHERE id=?");

            statement.setInt(1, carId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Am sters masina cu id-ul " + carId);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteClient(int clientId) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM client WHERE id=?");

            statement.setInt(1, clientId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Am sters clientul cu id-ul " + clientId);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(int clientId) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?");

            statement.setInt(1, clientId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Am sters userul cu id-ul " + clientId);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean editCar(Car car) {
        int rowsAffected;

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("UPDATE car SET brand = ?, model = ?, registration_number = ?, year = ?, price_day = ?, seats = ?, transmission = ?, fuel_type = ?, engine_capacity = ?, is_available = ? WHERE id = ?");

            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getRegistrationNumber());
            statement.setInt(4, car.getYear());
            statement.setInt(5, car.getPricePerDay());
            statement.setInt(6, car.getSeats());
            statement.setString(7, car.getTransmission());
            statement.setString(8, car.getFuelType());
            statement.setFloat(9, car.getEngineCapacity());
            statement.setBoolean(10, car.isAvailable());
            statement.setInt(11, car.getId());

            rowsAffected = statement.executeUpdate();


//            if (rowInserted > 0) {
//                System.out.println("Am adaugat cu succes");
//
//                // afiseaza mesaj de succes
////                JOptionPane.showMessageDialog(addCarPanel, "The car has been added successfully!");
////                dispose();
//            } else {
//                // afiseaza mesaj de eroare
////                JOptionPane.showMessageDialog(addCarPanel, "Error adding the car to the database !");
//                System.out.println("Nu am putut adauga masina");
//
//            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        if (rowsAffected > 0) {
            System.out.println("Update realizat cu succes. Numarul de randuri afectate: " + rowsAffected);
            return true;
        } else {
            System.out.println("Update esuat sau nu s-a modificat niciun rand.");
            return false;
        }
    }

    public void setRentPriceTo0ForDiscard(int id) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("UPDATE rent SET total_price=? WHERE id = ?");

            statement.setInt(1, 0);
            statement.setInt(2, id);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Car> getAllCarsAvailableForASpecificDate(LocalDate start, LocalDate end) {
        ArrayList cars = new ArrayList<Car>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
//            PreparedStatement statement = connection.prepareStatement("SELECT *\n" +
//                    "FROM car\n" +
//                    "WHERE id NOT IN (\n" +
//                    "    SELECT car_id\n" +
//                    "    FROM rent\n" +
//                    "    WHERE (start_date_rent < ? AND end_date_rent > ?)\n" +
//                    "       OR (start_date_rent > ? AND end_date_rent < ?)\n" +
//                    "  )");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM car WHERE id NOT IN (SELECT car_id FROM rent WHERE ? < end_date_rent AND ? > start_date_rent AND is_available = true)");

            statement.setDate(1, Date.valueOf(start));
            statement.setDate(2, Date.valueOf(end));
//            statement.setDate(3, Date.valueOf(start));
//            statement.setDate(4, Date.valueOf(end));
//            statement.setDate(5, Date.valueOf(start));
//            statement.setDate(6, Date.valueOf(end));
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

    public boolean editClient(Client clientToEdit) {
        int rowsAffected;

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("UPDATE clients SET name = ?, email = ?, phone = ? WHERE id = ?");

            statement.setString(1, clientToEdit.getName());
            statement.setString(2, clientToEdit.getEmail());
            statement.setInt(3, clientToEdit.getPhone());
            statement.setInt(4, clientToEdit.getId());

            rowsAffected = statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException(ex);
        }
        if (rowsAffected > 0) {
            System.out.println("Update realizat cu succes. Numarul de randuri afectate: " + rowsAffected);
            return true;
        } else {
            System.out.println("Update esuat sau nu s-a modificat niciun rand.");
            return false;
        }
    }

    public ArrayList<User> getAllUsers() {
        ArrayList users = new ArrayList<User>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users order by name ");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(String.valueOf(resultSet.getInt("phone")));
                user.setAddress(resultSet.getString("address"));
                user.setRole(resultSet.getInt("role"));
                user.setPassword(resultSet.getString("password"));
                user.setNo_rents(resultSet.getInt("no_rents"));

                users.add(user);

            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public boolean addEmployeeToDatabase(User user1) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, email, phone, address, password, role) VALUES (?, ?, ?, ?, ?, ?)");

            statement.setString(1, user1.getName());
            statement.setString(2, user1.getEmail());
            statement.setInt(3, Integer.parseInt(user1.getPhone()));
            statement.setString(4, user1.getAddress());
            statement.setString(5, user1.getPassword());
            statement.setInt(6, user1.getRole());

            int rowInserted = statement.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("Am adaugat cu succes user");
                return true;
                // afiseaza mesaj de succes
//                JOptionPane.showMessageDialog(addCarPanel, "The car has been added successfully!");
//                dispose();
            } else {
                // afiseaza mesaj de eroare
//                JOptionPane.showMessageDialog(addCarPanel, "Error adding the car to the database !");
                System.out.println("Nu am putut adauga userul");
                return false;
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean editUser(User user1) {
        int rowsAffected;

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET name = ?, email = ?, phone = ?, address = ?, role = ?, password = ? WHERE id = ?");

            statement.setString(1, user1.getName());
            statement.setString(2, user1.getEmail());
            statement.setInt(3, Integer.parseInt(user1.getPhone()));
            statement.setString(4, user1.getAddress());
            statement.setInt(5, user1.getRole());
            statement.setString(6, user1.getPassword());
            statement.setInt(7, user1.getId());
            System.out.println(user1.getId() + "userrr idddddddddddddddddddddd");

            rowsAffected = statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException(ex);
        }
        if (rowsAffected > 0) {
            System.out.println("Update realizat cu succes. Numarul de randuri afectate: " + rowsAffected);
            return true;
        } else {
            System.out.println("Update esuat sau nu s-a modificat niciun rand.");
            return false;
        }
    }

    public void addUserNoRents(User user) {
        int rowsAffected;

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET no_rents = ? WHERE id = ?");

            statement.setInt(1, user.getNo_rents());
            statement.setInt(2, user.getId());
            rowsAffected = statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException(ex);
        }
        if (rowsAffected > 0) {
            System.out.println("Update realizat cu succes. Numarul de randuri afectate: " + rowsAffected);
        } else {
            System.out.println("Update esuat sau nu s-a modificat niciun rand.");
        }
    }
}
