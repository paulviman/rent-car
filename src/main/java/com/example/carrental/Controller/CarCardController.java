package com.example.carrental.Controller;

import com.example.carrental.Main;
import com.example.carrental.Model.Car;
import com.example.carrental.Services.AlertService;
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

import javax.swing.text.html.ImageView;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class CarCardController {
    @javafx.fxml.FXML
    Label carModel;

    Car car;
    @javafx.fxml.FXML
    private Label carSeats;
    @javafx.fxml.FXML
    private Label carBrand;
    @javafx.fxml.FXML
    private Label carPriceText;
    @javafx.fxml.FXML
    private Label carPrice;
    @javafx.fxml.FXML
    private Label carTransmisionText;
    @javafx.fxml.FXML
    private Label carTransmission;
    @javafx.fxml.FXML
    private Label carSeatsText;
    @javafx.fxml.FXML
    private Label availableLabel;

    DatabaseService databaseService = new DatabaseService();
    @javafx.fxml.FXML
    private Button btnEditCar;
    @javafx.fxml.FXML
    private Button btnDeleteCar;

    AlertService alertService = new AlertService();
    int carIdForDelete;

    @javafx.fxml.FXML
    public void initialize() {
    }

    public void setCar(Car car1) {
        car = car1;
    }


    public Node createCardNode(Car car) {
        try {
            // încărcarea fișierului FXML pentru cardul de mașină
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("car-card.fxml"));
            Parent root = loader.load();

            // obținerea controllerului pentru fișierul FXML
            CarCardController controller = loader.getController();

            controller.setCar(car);
            //controller.carIdForDelete=car.getId();

            controller.carBrand.setText(car.getBrand());
            controller.carModel.setText(car.getModel());
            controller.carPrice.setText(String.valueOf(car.getPricePerDay()));
            controller.carTransmission.setText(car.getTransmission());
            controller.carSeats.setText(String.valueOf(car.getSeats()));
            if (car.isAvailable()) {
                controller.availableLabel.setText("Available");
                controller.availableLabel.setStyle("-fx-text-fill: green");
            } else {
                controller.availableLabel.setText("Not Available");
                controller.availableLabel.setStyle("-fx-text-fill: red");
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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    ArrayList populateListCarFromDB() {
        return databaseService.getAllCars();
    }

    public void actionBtnEditCar(javafx.event.ActionEvent actionEvent) {
        try {
            // Crează un obiect FXMLLoader pentru fișierul FXML de editare a mașinilor
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("editCar.fxml"));
            Parent root = loader.load();

            // Obține controller-ul pentru fereastra de editare a mașinilor
            EditCarController controller = loader.getController();
            controller.setCar(car);

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

    public void actionBtnDeleteCar(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare ștergere");
        alert.setContentText("Sigur doriți să ștergeți această mașină?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Apelare metoda de ștergere a mașinii din baza de date
            databaseService.deleteCar(car.getId());
            // Actualizare lista de mașini afișate în interfața grafică
        }

    }

    @javafx.fxml.FXML
    public void actionBtnEditCar(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void actionBtnDeleteCar(ActionEvent actionEvent) {
    }
//        ArrayList cars = new ArrayList<Car>();
//
//
//        final String DB_URL = "jdbc:postgresql://localhost:5432/rent-car";
//        final String USERNAME = "postgres";
//        final String PASSWORD = "postgres";
//        try {
//            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM car");
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                Car car = new Car();
//                car.setId(resultSet.getInt("id"));
//                car.setBrand(resultSet.getString("brand"));
//                car.setModel(resultSet.getString("model"));
//                car.setRegistrationNumber(resultSet.getString("registration_number"));
//                car.setYear(resultSet.getInt("year"));
//                car.setPricePerDay(resultSet.getInt("price_day"));
//                car.setSeats(resultSet.getInt("seats"));
//                car.setTransmission(resultSet.getString("transmission"));
//                car.setFuelType(resultSet.getString("fuel_type"));
//                car.setAvailable(resultSet.getBoolean("is_available"));
//                car.setEngineCapacity((resultSet.getInt("engine_capacity")));
//
//                cars.add(car);
//
//            }
//
//            statement.close();
//            connection.close();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return cars;
    // }
}