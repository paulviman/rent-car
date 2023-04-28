package com.example.carrental.Controller;

import com.example.carrental.Main;
import com.example.carrental.Model.Car;
import com.example.carrental.Model.Client;
import com.example.carrental.Services.DatabaseService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ClientController {
    @javafx.fxml.FXML
    private Label clientName;
    @javafx.fxml.FXML
    private Label carModel;
    @javafx.fxml.FXML
    private Label clientEmail;
    @javafx.fxml.FXML
    private Label clientPhone;
    @javafx.fxml.FXML
    private Label carSeats;
    DatabaseService databaseService = new DatabaseService();

    public ArrayList<Client> populateListClientsFromDB() {
        return databaseService.getAllClients();
//        ArrayList clients = new ArrayList<Client>();
//
//
//        final String DB_URL = "jdbc:postgresql://localhost:5432/rent-car";
//        final String USERNAME = "postgres";
//        final String PASSWORD = "postgres";
//        try {
//            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clients");
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                Client client = new Client();
//                client.setId(resultSet.getInt("id"));
//                client.setName(resultSet.getString("name"));
//                client.setEmail(resultSet.getString("email"));
//                client.setPhone(Integer.parseInt(resultSet.getString("phone")));
//
//                clients.add(client);
//
//            }
//
//            statement.close();
//            connection.close();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return clients;
    }

    public Node createCardClientNode(Client client) {
        try {
            // încărcarea fișierului FXML pentru cardul de mașină
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("client-card.fxml"));
            Parent root = loader.load();

            // obținerea controllerului pentru fișierul FXML
            ClientController controller = loader.getController();

            controller.clientName.setText(client.getName());
            controller.clientEmail.setText(client.getEmail());
            controller.clientPhone.setText(String.valueOf(client.getPhone()));

            // inițializarea elementelor vizuale din card
            // ImageView carImage = car.getCarImage();
//            String carName = car.getBrand();
//            Label carYear = controller.getCarYear();
//            Label carPrice = controller.getCarPrice();

            // setarea valorilor corespunzătoare pentru informațiile despre mașină
            //  carImage.setImage(new Image(car.getImageUrl()));
//            carName.setText(car.getMake() + " " + car.getModel());
//            carYear.setText(Integer.toString(car.getYear()));
//            carPrice.setText("$" + Double.toString(car.getPrice()));

            // returnarea nodului reprezentând cardul de mașină
            return root;
        } catch (
                IOException e) {
            e.printStackTrace();
            return null;

        }
    }
}
