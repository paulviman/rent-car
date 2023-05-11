package com.example.carrental.Controller;

import com.example.carrental.Main;
import com.example.carrental.Model.Car;
import com.example.carrental.Model.Client;
import com.example.carrental.Model.Rent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import com.example.carrental.Services.DatabaseService;
import javafx.scene.layout.Pane;

public class RentController {

    @javafx.fxml.FXML
    private Label rentDate;
    @javafx.fxml.FXML
    private Label rentAddress;
    @javafx.fxml.FXML
    private Label rentClient;
    @javafx.fxml.FXML
    private Label rentCar;
    @javafx.fxml.FXML
    private Label rentPrice;

    DatabaseService databaseService = new DatabaseService();
    @javafx.fxml.FXML
    private Pane frontColorPane = new Pane();
    @javafx.fxml.FXML
    private Pane backColorPane = new Pane();

    public ArrayList<Rent> populateListRentFromDB(ArrayList<Car> cars, ArrayList<Client> clients) {
        return databaseService.getAllRent(cars, clients);
    }


    public Node createCardNode(Rent rent) {
        //obtin datele dspre clinet si car facand un query in db
        Client client = databaseService.getClient(rent.getClientId());
        Car car = databaseService.getCar(rent.getCarId());
        LocalDate currentDate = LocalDate.now();
        //calculez cate zile este inchiriata masina
        //long days = ChronoUnit.DAYS.between(rent.getStartDateRent().toInstant(), rent.getEndDaterRent().toInstant());
        //calculez pretul total pe zile
        //long total_price = days * car.getPricePerDay();

        try {
            // încărcarea fișierului FXML pentru cardul de mașină
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("rent.fxml"));
            Parent root = loader.load();

            // obținerea controllerului pentru fișierul FXML
            RentController controller = loader.getController();

            controller.rentDate.setText(String.valueOf(rent.getStartDateRent()));
            controller.rentAddress.setText(rent.getPickUpAddress());
            controller.rentClient.setText(client.getName());
            controller.rentCar.setText(car.getBrand() + " " + car.getModel());
            //controller.rentPrice.setText(String.valueOf(total_price));
            controller.rentPrice.setText(String.valueOf(rent.getTotalPrice()));
            if (rent.getEndDaterRent().isBefore(currentDate)) {
                controller.frontColorPane.setStyle("-fx-background-color: red;");
                controller.backColorPane.setStyle("-fx-background-color: red;");
            }
//            controller.frontColorPane.setStyle("-fx-background-color:  80CBC4;");
//            controller.backColorPane.setStyle("-fx-background-color:  #80CBC4;");


            return root;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
