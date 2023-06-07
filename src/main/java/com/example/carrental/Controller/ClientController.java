package com.example.carrental.Controller;

import com.example.carrental.Main;
import com.example.carrental.Model.Car;
import com.example.carrental.Model.Client;
import com.example.carrental.Services.DatabaseService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

import com.example.carrental.Controller.EditClientController;

public class ClientController {
    DatabaseService databaseService = new DatabaseService();

    Client client;
    @javafx.fxml.FXML
    private Label clientName;
    @javafx.fxml.FXML
    private Label clientEmail;
    @javafx.fxml.FXML
    private Label clientPhone;
    @javafx.fxml.FXML
    private Button btnEditClient;
    @javafx.fxml.FXML
    private Button bntDeleteClient;

    public void setClient(Client client) {
        this.client = client;
    }

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

    public Node createCardClientNode(Client client, Boolean displayButtons) {
        try {
            // încărcarea fișierului FXML pentru cardul de mașină
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("client-card.fxml"));
            Parent root = loader.load();

            // obținerea controllerului pentru fișierul FXML
            ClientController controller = loader.getController();

            controller.setClient(client);

            controller.clientName.setText(client.getName());
            controller.clientEmail.setText(client.getEmail());
            controller.clientPhone.setText("0" + client.getPhone());

            if (!displayButtons){
                controller.btnEditClient.setVisible(false);
                controller.bntDeleteClient.setVisible(false);
            }

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

    public void actionBtnEditClient(javafx.event.ActionEvent actionEvent) {
        try {
            // Crează un obiect FXMLLoader pentru fișierul FXML de editare a mașinilor
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("editClient.fxml"));
            Parent root = loader.load();

            // Obține controller-ul pentru fereastra de editare a mașinilor
            EditClientController controller = loader.getController();
            controller.setClient(client);

            // Obține mașina corespunzătoare acestei cărți
            //Car car = (Car) btnEdit.getUserData();

            // Inițializează formularul de editare cu informațiile despre mașină
            //controller.initialize(car);

            // Creează o nouă scenă pentru fereastra de editare a mașinilor și o afișează
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionBtnDeleteClient(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare ștergere");
        alert.setContentText("Sigur doriți să ștergeți acest client?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            databaseService.deleteClient(client.getId());
        }
    }

//    @Deprecated
//    public void actionBtnEditClient(ActionEvent actionEvent) {
//    }
//
//    @javafx.fxml.FXML
//    public void actionBtnDeleteClient(ActionEvent actionEvent) {
//    }
//
//    @javafx.fxml.FXML
//    public void actionBtnEditClient(ActionEvent actionEvent) {
//    }
}
