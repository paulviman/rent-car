package com.example.carrental.Controller;

import com.example.carrental.Main;
import com.example.carrental.Model.Car;
import com.example.carrental.Model.Client;
import com.example.carrental.Model.Rent;
import com.example.carrental.Model.User;
import com.example.carrental.Services.DatabaseService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import com.gluonhq.charm.glisten.control.CardPane;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class DashboardController {
    User user = new User();
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnCars;
    @FXML
    private Button btnRent;
    @FXML
    private Button btnClients;
    @FXML
    private Button btnSignOut;
    @FXML
    private TabPane panelCars;
    @FXML
    private FlowPane panelDashboard;
    @FXML
    private TabPane panelRent;
    @FXML
    private TabPane panelClients;
    ArrayList<Car> cars = new ArrayList<>();
    ArrayList<Rent> rents = new ArrayList<>();
    ArrayList<Client> clients = new ArrayList<>();
    private CarCardController cardController = new CarCardController();
    private ClientController clientsController = new ClientController();
    private RentController rentController = new RentController();
    @FXML
    private CardPane cardPane;
    @FXML
    private Button btnCarSearch;
    @FXML
    private TextField searchTextFieldCar;
    LoginController loginController;
    @FXML
    private CardPane cardPaneClients;
    @FXML
    private ComboBox filter;
    @FXML
    private TextField addCarBrand;
    @FXML
    private TextField addCarModel;
    @FXML
    private TextField addCarRegNumb;
    @FXML
    private TextField addCarYear;
    @FXML
    private TextField addCarPrice;
    @FXML
    private TextField addCarSeats;
    @FXML
    private TextField addCarTransmission;
    @FXML
    private TextField addCarFuelType;
    @FXML
    private TextField addCarEngineCapacity;
    @FXML
    private Button addCar;
    DatabaseService databaseService = new DatabaseService();
    @FXML
    private TextField addClientName;
    @FXML
    private TextField addClientEmail;
    @FXML
    private TextField addClientPhone;
    @FXML
    private Button btnAddClient;
    @FXML
    private TextField searchClient;
    @FXML
    private Button btnSearchClient;
    @FXML
    private ComboBox filter1;
    @FXML
    private CardPane cardPaneRent;
    @FXML
    private TextField searchTextFieldCar1;
    @FXML
    private Button btnCarSearch1;
    @FXML
    private ComboBox filter2;
    @FXML
    private BorderPane paneCarRent;
    @FXML
    private BorderPane paneDateRent;
    @FXML
    private DatePicker pickUpDateLabel;
    @FXML
    private DatePicker returnDateLabel;
    @FXML
    private TextField pickUpAddressLabel;
    @FXML
    private TextField returnAddressLabel;
    @FXML
    private Button btnNextToSelectCar;
    @FXML
    private BorderPane paneClientRent;
    @FXML
    private Button btnBackToSelectCar;
    @FXML
    private Button btnSaveRent;
    @FXML
    private TableView carTabelForSelect;
    @FXML
    private CardPane paneCardCarSelected;


    @FXML
    public void initialize() {
        panelDashboard.toFront();

        cars = cardController.populateListCarFromDB();

        setTableCarsForRent();

        filter.getItems().addAll("Price Ascending", "Price Descending", "Transmission manual");
    }

    public void setTableCarsForRent() {
        TableColumn<Car, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Car, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Car, String> priceCol = new TableColumn<>("Price per day");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));

        TableColumn<Car, String> transCol = new TableColumn<>("Transmission");
        transCol.setCellValueFactory(new PropertyValueFactory<>("transmission"));

        TableColumn<Car, String> regCol = new TableColumn<>("Registration number");
        regCol.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));

        TableColumn<Car, String> fuelCol = new TableColumn<>("Fuel Type");
        fuelCol.setCellValueFactory(new PropertyValueFactory<>("fuelType"));

        TableColumn<Car, String> seatCol = new TableColumn<>("Seats");
        seatCol.setCellValueFactory(new PropertyValueFactory<>("seats"));

        TableColumn<Car, String> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));


        carTabelForSelect.getColumns().addAll(brandCol, modelCol, priceCol,transCol,regCol,fuelCol,seatCol,yearCol);
    }

    private void addListCarToCard(ArrayList<Car> carsDisplay) {
        cardPane.getItems().clear();

        // VBox cardContainer = new VBox();
        for (Car car : carsDisplay) {
            Node cardNode = cardController.createCardNode(car);
            //cardContainer.getChildren().add(cardNode);
            //scrolPane.getItems().add(cardNode);
            cardPane.getItems().add(cardNode);
        }
        // cardPane.getItems().add(cardContainer);
    }

    private void addListRentToCard(ArrayList<Rent> rentDisplay) {
        cardPaneRent.getItems().clear();

        for (Rent rent : rentDisplay) {
            Node cardNode = rentController.createCardNode(rent);
            cardPaneRent.getItems().add(cardNode);
        }
    }

    private void addListClientToCard(ArrayList<Client> clientsDisplay) {
        cardPaneClients.getItems().clear();

        // VBox cardContainer = new VBox();
        for (Client client : clientsDisplay) {
            Node cardNode = clientsController.createCardClientNode(client);
            //cardContainer.getChildren().add(cardNode);
            //scrolPane.getItems().add(cardNode);
            cardPaneClients.getItems().add(cardNode);
        }
        // cardPane.getItems().add(cardContainer);
    }


    @FXML
    public void actionBtnDashboard(ActionEvent actionEvent) {
        panelDashboard.toFront();
    }

    @FXML
    public void actionBtnCars(ActionEvent actionEvent) {

        addListCarToCard(cars);

        panelCars.toFront();
    }

    @FXML
    public void actionBtnRent(ActionEvent actionEvent) {

        rents = rentController.populateListRentFromDB();
        addListRentToCard(rents);

        panelRent.toFront();
        paneDateRent.toFront();
    }

    @FXML
    public void actionBtnClients(ActionEvent actionEvent) {
        clients = clientsController.populateListClientsFromDB();
        addListClientToCard(clients);

        panelClients.toFront();
    }


    @FXML
    public void actionBtnCarSearch(ActionEvent actionEvent) {
        String searchText = searchTextFieldCar.getText().toLowerCase();

        ArrayList<Car> searchResults = new ArrayList<>();

        for (Car car : cars) {
            if (car.getBrand().toLowerCase().contains(searchText) ||
                    car.getRegistrationNumber().toLowerCase().contains(searchText)) {
                searchResults.add(car);
            }
        }

        addListCarToCard(searchResults);
    }

    @FXML
    public void actionBtnSignOut(ActionEvent actionEvent) {
        cars = null;
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            loginStage.setScene(scene);
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void actionFilter(ActionEvent actionEvent) {
        ArrayList carsFiltred = new ArrayList();

        if (filter.getValue().equals("Price Ascending")) {
            Collections.sort(cars, Comparator.comparing(Car::getPricePerDay));
        } else if (filter.getValue().equals("Price Descending")) {
            Collections.sort(cars, Comparator.comparing(Car::getPricePerDay).reversed());
        } else if (filter.getValue().equals("Transmission manual")) {
            //cardPane.getChildren().clear(); // sterge cardurile existente
            for (Car car : cars) {
                if (car.getTransmission().equals("manual")) {
                    carsFiltred.add(car);
//                    CarCardController card = addCa(car);
//                    cardPane.getChildren().add(card);
                }
            }
            addListCarToCard(carsFiltred);
        }
        addListCarToCard(cars);
    }

    @FXML
    public void actionBtnAddCar(ActionEvent actionEvent) {
        Car car = new Car();

        if (addCarBrand.getText().isEmpty() || addCarModel.getText().isEmpty() ||
                addCarRegNumb.getText().isEmpty() || addCarYear.getText().isEmpty() ||
                addCarPrice.getText().isEmpty() || addCarSeats.getText().isEmpty() ||
                addCarTransmission.getText().isEmpty() || addCarFuelType.getText().isEmpty() ||
                addCarEngineCapacity.getText().isEmpty()) {

            //Fereastra cu mesaj de eroare

//            JOptionPane.showMessageDialog(addCarPanel,
//                    "Please enter all fields",
//                    "Try again",
//                    JOptionPane.ERROR_MESSAGE);

            return;
        }

        try {
            int verific = Integer.parseInt(addCarYear.getText());
            verific = Integer.parseInt(addCarPrice.getText());
            verific = Integer.parseInt(addCarSeats.getText());
            verific = Integer.parseInt(addCarEngineCapacity.getText());
        } catch (NumberFormatException exception) {

            //fereastra cu mesaj de eroare

//            JOptionPane.showMessageDialog(addCarPanel,
//                    "Please enter correct number fields for  YEAR, PRICE, SEATS, ENGINE CAPACITY!",
//                    "Try again",
//                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        car.setBrand(addCarBrand.getText());
        car.setModel(addCarModel.getText());
        car.setRegistrationNumber(addCarRegNumb.getText());
        car.setYear(Integer.valueOf(addCarYear.getText()));
        car.setPricePerDay(Integer.valueOf(addCarPrice.getText()));
        car.setSeats(Integer.valueOf(addCarSeats.getText()));
        car.setTransmission(addCarTransmission.getText());
        car.setFuelType(addCarFuelType.getText());
        car.setAvailable(true);
        car.setEngineCapacity(Integer.valueOf(addCarEngineCapacity.getText()));

        databaseService.addCarToDatabase(car);

    }

    @FXML
    public void actionBtnAddClient(ActionEvent actionEvent) {
        Client client = new Client();

        client.setName(addClientName.getText());
        client.setEmail(addClientEmail.getText());
        client.setPhone(Integer.parseInt(addClientPhone.getText()));

        databaseService.addClientToDatabase(client);

        addClientName.setText(null);
        addClientEmail.setText(null);
        addClientPhone.setText(null);
    }

    @Deprecated
    public void actionBtnSearchClient(ActionEvent actionEvent) {
        String searchText = searchClient.getText().toLowerCase();

        ArrayList<Client> searchResults = new ArrayList<>();

        for (Client client : clients) {
            if (client.getName().toLowerCase().contains(searchText) ||
                    client.getEmail().toLowerCase().contains(searchText)) {
                searchResults.add(client);
            }
        }

        addListClientToCard(searchResults);
    }

    Rent newRent = new Rent();

    @Deprecated
    public void actionBtnNextToSelectClient(ActionEvent actionEvent) {


    }

    @FXML
    public void actionBtnNextToSelectCar(ActionEvent actionEvent) {

        System.out.println(pickUpDateLabel.getValue());
        newRent.setStartDateRent(pickUpDateLabel.getValue());
        newRent.setEndDaterRent(returnDateLabel.getValue());
        newRent.setPickUpAddress(pickUpAddressLabel.getText());
        newRent.setReturnAddress(returnAddressLabel.getText());


        carTabelForSelect.setItems(FXCollections.observableArrayList(cars));


        paneCarRent.toFront();
    }

    @FXML
    public void actionBtnBackToSelectCar(ActionEvent actionEvent) {
    }

    @FXML
    public void actionBtnSaveRent(ActionEvent actionEvent) {
    }

    @FXML
    public void onMouseClickedTabel(Event event) {
        Car car = (Car) carTabelForSelect.getSelectionModel().getSelectedItem();
        newRent.setCarId(car.getId());

        Node cardNode = cardController.createCardNode(car);
        paneCardCarSelected.getItems().add(cardNode);
    }
}