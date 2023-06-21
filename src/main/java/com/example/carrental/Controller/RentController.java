package com.example.carrental.Controller;

import com.example.carrental.Main;
import com.example.carrental.Model.Car;
import com.example.carrental.Model.Client;
import com.example.carrental.Model.Rent;
import com.example.carrental.Model.User;
import com.example.carrental.Services.AlertService;
import com.example.carrental.Services.PdfService;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import com.example.carrental.Services.DatabaseService;
import javafx.scene.layout.Pane;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.*;

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
    @javafx.fxml.FXML
    private Label returnDate;
    @javafx.fxml.FXML
    private Label returnAddress;
    @javafx.fxml.FXML
    private Label rentClientPhone;
    @javafx.fxml.FXML
    private Label rentCarRegNumb;
    @javafx.fxml.FXML
    private Label idRent;
    @javafx.fxml.FXML
    private Button btnGenerateInvoicePdf;
    @javafx.fxml.FXML
    private Button btnEditRent;

    Rent rent;
    Client client;
    Car car;
    User user;

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Rent> populateListRentFromDB(ArrayList<Car> cars, ArrayList<Client> clients) {
        return databaseService.getAllRent(cars, clients);
    }


    public Node createCardNode(Rent rent) {
        //obtin datele dspre clinet si car facand un query in db
        this.rent = rent;
        this.client = databaseService.getClient(rent.getClientId());
        this.car = databaseService.getCar(rent.getCarId());
        LocalDate currentDate = LocalDate.now();
        //calculez cate zile este inchiriata masina
        //long days = ChronoUnit.DAYS.between(rent.getStartDateRent().toInstant(), rent.getEndDaterRent().toInstant());
        //calculez pretul total pe zile
        //long total_price = days * car.getPricePerDay();

        try {
            // încărcarea fișierului FXML pentru cardul de rent
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("rent.fxml"));
            Parent root = loader.load();

            // obținerea controllerului pentru fișierul FXML
            RentController controller = loader.getController();

            controller.rent = rent;
            controller.client = databaseService.getClient(rent.getClientId());
            controller.car = databaseService.getCar(rent.getCarId());
            controller.user = user;

            controller.idRent.setText(String.valueOf(rent.getId()));

            controller.rentDate.setText(String.valueOf(rent.getStartDateRent()));
            controller.returnDate.setText(String.valueOf(rent.getEndDaterRent()));

            controller.rentAddress.setText(rent.getPickUpAddress());
            controller.returnAddress.setText(rent.getReturnAddress());

            controller.rentClient.setText(client.getName());
            controller.rentClientPhone.setText("0" + client.getPhone());

            controller.rentCar.setText(car.getBrand() + " " + car.getModel());
            controller.rentCarRegNumb.setText(car.getRegistrationNumber());

            //controller.rentPrice.setText(String.valueOf(total_price));
            controller.rentPrice.setText(String.valueOf(rent.getTotalPrice()));
            if (!rent.isAvailable()) {
                controller.frontColorPane.setStyle("-fx-background-color: #9403fc;");
                controller.backColorPane.setStyle("-fx-background-color: #9403fc;");

                controller.btnGenerateInvoicePdf.setDisable(true);
                controller.btnEditRent.setDisable(true);
            } else if ((currentDate.isAfter(rent.getEndDaterRent()))) {
                controller.frontColorPane.setStyle("-fx-background-color: #EF5350;");
                controller.backColorPane.setStyle("-fx-background-color: #EF5350;");
            } else if ((currentDate.isAfter(rent.getStartDateRent())) && (currentDate.isBefore(rent.getEndDaterRent()))) {
                controller.frontColorPane.setStyle("-fx-background-color:  #FFEE58;");
                controller.backColorPane.setStyle("-fx-background-color:  #FFEE58;");
            } else if (currentDate.isBefore(rent.getStartDateRent())) {
                controller.frontColorPane.setStyle("-fx-background-color:  #80CBC4;");
                controller.backColorPane.setStyle("-fx-background-color:  #80CBC4;");
            }
//            if (rent.getEndDaterRent().isBefore(currentDate)) {
//                controller.frontColorPane.setStyle("-fx-background-color: #FFEE58;");
//                controller.backColorPane.setStyle("-fx-background-color: #FFEE58;");
//                controller.btnEditRent.setDisable(true);
//            }


            return root;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actionBtnGenerateInvoicePdf(javafx.event.ActionEvent actionEvent) {
        PdfService.generateInvoice(rent, car, client, user);
    }

    public void actionBtnEditRent(javafx.event.ActionEvent actionEvent) {
        btnGenerateInvoicePdf.setDisable(true);
        btnEditRent.setDisable(true);
        this.frontColorPane.setStyle("-fx-background-color: #FFEE58;");
        this.backColorPane.setStyle("-fx-background-color: #FFEE58;");
        databaseService.setCarAvailability(rent.getCarId(), true);
        databaseService.setRentAvailability(rent.getId(), false);
        databaseService.setRentPriceTo0ForDiscard(rent.getId());
    }

    @javafx.fxml.FXML
    public void actionBtnGenerateInvoicePdf(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void actionBtnEditRent(ActionEvent actionEvent) {
    }
}
