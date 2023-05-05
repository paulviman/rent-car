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

    public ArrayList<Rent> populateListRentFromDB() {
        return databaseService.getAllRent();
    }


    public Node createCardNode(Rent rent) {
        //obtin datele dspre clinet si car facand un query in db
        Client client = databaseService.getClient(rent.getClientId());
        Car car = databaseService.getCar(rent.getCarId());
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

            return root;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
