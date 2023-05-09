package com.example.carrental.Controller;

import com.example.carrental.Main;
import com.example.carrental.Model.Car;
import com.example.carrental.Model.Client;
import com.example.carrental.Model.Rent;
import com.example.carrental.Model.User;
import com.example.carrental.Services.AlertService;
import com.example.carrental.Services.DatabaseService;
import com.example.carrental.Services.ValidationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


import java.io.IOException;
import java.time.temporal.ChronoUnit;
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
    private Button btnBackToDetaileRent;
    @FXML
    private Button btnNextToSelectClient;
    @FXML
    private TableView clientTabelForSelect;
    @FXML
    private CardPane paneCardClientSelected;
    @FXML
    private TextField searchTextFieldCarForRent;
    @FXML
    private Button btnCarSearchForRent;
    @FXML
    private Button btnClientSearchForRent;
    @FXML
    private TextField searchTextFieldClientForRent;
    @FXML
    private Button btnNextToSave;
    @FXML
    private Button btnBackToSelectClient;
    @FXML
    private BorderPane paneSaveRent;
    @FXML
    private Label startDateToSave;
    @FXML
    private Label returnDateToSave;
    @FXML
    private Label pickUpAddressToSave;
    @FXML
    private Label returnAddressToSave;
    @FXML
    private Label totalPriceToSave;
    @FXML
    private Label brandToSave;
    @FXML
    private Label modelToSave;
    @FXML
    private Label regNumbToSave;
    @FXML
    private Label yearToSave;
    @FXML
    private Label seatsToSave;
    @FXML
    private Label transmissionToSave;
    @FXML
    private Label fuelTypeToSave;
    @FXML
    private Label engineCapacityToSave;
    @FXML
    private Label pricePerDayToSave;
    @FXML
    private Label nameToSave;
    @FXML
    private Label emailToSave;
    @FXML
    private Label phoneToSave;
    AlertService alertService = new AlertService();
    private ValidationService validationService = new ValidationService();
    ArrayList<Car> carsAvailable;

    @FXML
    public void initialize() {
        panelDashboard.toFront();

        carsAvailable = databaseService.getAllCarsAvailable();
        cars = cardController.populateListCarFromDB();
        clients = clientsController.populateListClientsFromDB();

        carTabelForSelect.setItems(FXCollections.observableArrayList(carsAvailable));
        clientTabelForSelect.setItems(FXCollections.observableArrayList(clients));

        setTableCarsForRent();
        setTableClientsForRent();

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


        carTabelForSelect.getColumns().addAll(brandCol, modelCol, priceCol, transCol, regCol, fuelCol, seatCol, yearCol);
    }

    public void setTableClientsForRent() {
        TableColumn<Client, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Client, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Client, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));


        clientTabelForSelect.getColumns().addAll(nameCol, emailCol, phoneCol);
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

        String brand = addCarBrand.getText();
        String model = addCarModel.getText();
        String regNumb = addCarRegNumb.getText();
        String year = addCarYear.getText();
        String priceDay = addCarPrice.getText();
        String seats = addCarSeats.getText();
        String transmission = addCarTransmission.getText();
        String fuelType = addCarFuelType.getText();
        String engineCapacity = addCarEngineCapacity.getText();

        if (brand.isEmpty() || model.isEmpty() || regNumb.isEmpty() || year.isEmpty() ||
                priceDay.isEmpty() || seats.isEmpty() || transmission.isEmpty() || fuelType.isEmpty() || engineCapacity.isEmpty()) {
            alertService.newAlert("Eroare", "Completati toate campurile!");
            return;
        }

        if (!validationService.brandValidation(brand)) {
            alertService.newAlert("Eroare", "Brand invalid!\nTrebuie sa contina doar litere");
            return;
        }
        if (!validationService.modelValidation(model)) {
            alertService.newAlert("Eroare", "Model invalid!\nPoate sa contina doar litere si cifre");
            return;
        }
        if (!validationService.regNumbValidation(regNumb)) {
            alertService.newAlert("Eroare", "Numar de inmatriculare invalid!\nTrebuie sa fie de forma: MM111ABC");
            return;
        }
        if (!validationService.yearValidation(year)) {
            alertService.newAlert("Eroare", "An de fabricatie invalid!\nTrebuie sa fie de forma: 2000");
            return;
        }
        if (!validationService.priceValidation(priceDay)) {
            alertService.newAlert("Eroare", "Pret invalid!\nTrebuie contina doar cifre");
            return;
        }
        if (!validationService.seatsValidation(seats)) {
            alertService.newAlert("Eroare", "Numar de locuri invalid!\nTrebuie contina doar cifre");
            return;
        }
        if (!validationService.transmissionValidation(transmission)) {
            alertService.newAlert("Eroare", "Transmisie invalida!\nTrebuie contina doar litere");
            return;
        }
        if (!validationService.fuelTypeValidation(fuelType)) {
            alertService.newAlert("Eroare", "Tip combustibil invalid!\nTrebuie contina doar litere");
            return;
        }
        if (!validationService.engineCapacityValidation(engineCapacity)) {
            alertService.newAlert("Eroare", "Capacitate motorinvalida!\nTrebuie contina doar cifre sub forma:1.9");
            return;
        }


//        if (addCarBrand.getText().isEmpty() || addCarModel.getText().isEmpty() ||
//                addCarRegNumb.getText().isEmpty() || addCarYear.getText().isEmpty() ||
//                addCarPrice.getText().isEmpty() || addCarSeats.getText().isEmpty() ||
//                addCarTransmission.getText().isEmpty() || addCarFuelType.getText().isEmpty() ||
//                addCarEngineCapacity.getText().isEmpty()) {
//
//            //Fereastra cu mesaj de eroare
//
////            JOptionPane.showMessageDialog(addCarPanel,
////                    "Please enter all fields",
////                    "Try again",
////                    JOptionPane.ERROR_MESSAGE);
//
//            return;
//        }
//
//        try {
//            int verific = Integer.parseInt(addCarYear.getText());
//            verific = Integer.parseInt(addCarPrice.getText());
//            verific = Integer.parseInt(addCarSeats.getText());
//            verific = Integer.parseInt(addCarEngineCapacity.getText());
//        } catch (NumberFormatException exception) {
//
//            //fereastra cu mesaj de eroare
//
////            JOptionPane.showMessageDialog(addCarPanel,
////                    "Please enter correct number fields for  YEAR, PRICE, SEATS, ENGINE CAPACITY!",
////                    "Try again",
////                    JOptionPane.ERROR_MESSAGE);
//            return;
//        }

        car.setBrand(addCarBrand.getText());
        car.setModel(addCarModel.getText());
        car.setRegistrationNumber(addCarRegNumb.getText());
        car.setYear(Integer.valueOf(addCarYear.getText()));
        car.setPricePerDay(Integer.valueOf(addCarPrice.getText()));
        car.setSeats(Integer.valueOf(addCarSeats.getText()));
        car.setTransmission(addCarTransmission.getText());
        car.setFuelType(addCarFuelType.getText());
        car.setAvailable(true);
        car.setEngineCapacity(Float.valueOf(addCarEngineCapacity.getText()));

        if (databaseService.addCarToDatabase(car)) {
            alertService.newConfirmation("Reusit", "Ati adugat o masina cu succes!");
        } else {
            alertService.newAlert("Eroare", "Masina nu am putu fi adaugata!");
        }

    }

    @FXML
    public void actionBtnAddClient(ActionEvent actionEvent) {
        Client client = new Client();

        String name = addClientName.getText();
        String email = addClientEmail.getText();
        String phone = addClientPhone.getText();

        if (!validationService.nameValidation(name)) {
            alertService.newAlert("Eroare", "Nume invalid!\nForma acceptata: Popescu Ion");
            return;
        }
        if (!validationService.emailValidation(email)) {
            alertService.newAlert("Eroare", "Email invalid!\nForma acceptata: example@gmail.com");
            return;
        }
        if (!validationService.phoneValidation(phone)) {
            alertService.newAlert("Eroare", "Telefon invalid!\nForma acceptata: 0712312312");
            return;
        }

        client.setName(addClientName.getText());
        client.setEmail(addClientEmail.getText());
        client.setPhone(Integer.parseInt(addClientPhone.getText()));

        if (databaseService.addClientToDatabase(client)) {
            alertService.newConfirmation("Reusit", "Client adaugat cu succes!");
        } else {
            alertService.newAlert("Eroare", "Nu s-a putut aduga clientul!");
        }

        addClientName.setText(null);
        addClientEmail.setText(null);
        addClientPhone.setText(null);
    }

//    @Deprecated
//    public void actionBtnSearchClient(ActionEvent actionEvent) {
//
//    }

    Rent newRent = new Rent();

//    @Deprecated
//    public void actionBtnNextToSelectClient(ActionEvent actionEvent) {
//
//
//
//    }

    @FXML
    public void actionBtnNextToSelectCar(ActionEvent actionEvent) {

        //aici sa pun verificarile sa nu fie goale
        System.out.println(pickUpDateLabel.getValue());

        if ((pickUpDateLabel.getValue() == null) || (returnDateLabel.getValue() == null)
                || (pickUpAddressLabel.getText().isEmpty()) || (returnAddressLabel.getText().isEmpty())) {

            alertService.newAlert("Eroare", "Va rog sa completati toate campurile inainte de a continua!");
            return;

        }
        if (!validationService.dateValidation(pickUpDateLabel.getValue(), returnDateLabel.getValue())) {
            alertService.newAlert("Eroare", "PickUpDate trebuie sa fie mai mare ca ziua curenta\nReturnDate trebuie sa fie mai mare ca PickUpDate");
            return;
        }
        if(!validationService.addressValidation(pickUpAddressLabel.getText()) && !validationService.addressValidation(returnAddressLabel.getText())){
            alertService.newAlert("Eroare","Adresa de ridicare sau predare invalida!");
            return;
        }

        System.out.println(pickUpDateLabel.getValue());
        newRent.setStartDateRent(pickUpDateLabel.getValue());
        newRent.setEndDaterRent(returnDateLabel.getValue());
        newRent.setPickUpAddress(pickUpAddressLabel.getText());
        newRent.setReturnAddress(returnAddressLabel.getText());


        paneCarRent.toFront();


    }

    @FXML
    public void actionBtnBackToSelectCar(ActionEvent actionEvent) {
        paneCarRent.toFront();
    }

    @FXML
    public void actionBtnSaveRent(ActionEvent actionEvent) {

        databaseService.saveRent(newRent);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ok");
        alert.setHeaderText("Inchirierea a fost salvata!");
        alert.setContentText("Inchiriere efectuata cu succes");

        alert.showAndWait();

    }

    Optional<Car> selectedCar;
    Optional<Client> selectedClient;

    @FXML
    public void onMouseClickedTabel(Event event) {
        Node node = (Node) event.getSource();
        System.out.println(node);


        if (node.getId().equals(carTabelForSelect.getId())) {
            System.out.println("am accesat tabelul maisni");
            Car car = (Car) carTabelForSelect.getSelectionModel().getSelectedItem();
            newRent.setCarId(car.getId());

            selectedCar = cars.stream().filter(car1 -> car1.getId() == car.getId()).findFirst();

            paneCardCarSelected.getItems().clear();
            Node cardNode = cardController.createCardNode(car);
            paneCardCarSelected.getItems().add(cardNode);
        } else if (node.getId().equals(clientTabelForSelect.getId())) {

            System.out.println("am accesat tabelul clientiiii");
            Client client = (Client) clientTabelForSelect.getSelectionModel().getSelectedItem();
            newRent.setClientId(client.getId());

            selectedClient = clients.stream().filter(client1 -> client1.getId() == client.getId()).findFirst();

            paneCardClientSelected.getItems().clear();
            Node cardNode = clientsController.createCardClientNode(client);
            paneCardClientSelected.getItems().add(cardNode);
        }
    }

    @FXML
    public void actionBtnBackToDetaileRent(ActionEvent actionEvent) {
        selectedCar = null;
        paneDateRent.toFront();
    }

    @FXML
    public void actionBtnNextToSelectClient(ActionEvent actionEvent) {
        if (selectedCar == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare");
            alert.setHeaderText("Nu ati selectat nicio masina");
            alert.setContentText("Va rog sa selectati masina dorita inainte de a continua!");

            alert.showAndWait();
        } else {
            System.out.println(selectedCar);
//            //calculez cate zile este inchiriata masina
//            long days = ChronoUnit.DAYS.between(newRent.getStartDateRent().toInstant(), newRent.getEndDaterRent().toInstant());
//            //calculez pretul total pe zile
//            long total_price = days * selectedCar.getPricePerDay();
            paneClientRent.toFront();
        }
    }

    @FXML
    public void actionBtnCarSearchForRent(ActionEvent actionEvent) {

        String searchText = searchTextFieldCarForRent.getText().toLowerCase();

        ArrayList<Car> searchResults = new ArrayList<>();

        for (Car car : carsAvailable) {
            if (car.getBrand().toLowerCase().contains(searchText) ||
                    car.getRegistrationNumber().toLowerCase().contains(searchText)) {
                searchResults.add(car);
            }
        }
        System.out.println("dimensiunea cautarilor:" + searchResults.size());

        //addListCarToCard(searchResults);
//        ObservableList<Car> observableSearchResults = FXCollections.observableArrayList(searchResults);
//        carTabelForSelect.setItems(observableSearchResults);
        carTabelForSelect.getItems().setAll(searchResults);
        //setTableCarsForRent();
    }

    @FXML
    public void actionBtnClientSearchForRent(ActionEvent actionEvent) {
        String searchText = searchTextFieldClientForRent.getText().toLowerCase();

        ArrayList<Client> searchResults = new ArrayList<>();

        for (Client client : clients) {
            if (client.getName().toLowerCase().contains(searchText) ||
                    client.getEmail().toLowerCase().contains(searchText)) {
                searchResults.add(client);
            }
        }
        System.out.println("dimensiunea cautarilor:" + searchResults.size());

        //addListCarToCard(searchResults);
//        ObservableList<Car> observableSearchResults = FXCollections.observableArrayList(searchResults);
//        carTabelForSelect.setItems(observableSearchResults);
        clientTabelForSelect.getItems().setAll(searchResults);
        //setTableCarsForRent();
    }

    @FXML
    public void actionBtnNextToSave(ActionEvent actionEvent) {

        setLabelsToSave();

        paneSaveRent.toFront();
    }

    private void setLabelsToSave() {
        Car carToSave = new Car();
        Client clientToSave = new Client();
        for (Car car : cars) {
            if (car.getId() == newRent.getCarId()) {
                carToSave = car;
                break;
            }
        }
        for (Client client : clients) {
            if (client.getId() == newRent.getClientId()) {
                clientToSave = client;
                break;
            }
        }

        //calculez cate zile este inchiriata masina
        long days = ChronoUnit.DAYS.between(newRent.getStartDateRent(), newRent.getEndDaterRent());
        //calculez pretul total pe zile
        long total_price = days * carToSave.getPricePerDay();
        newRent.setTotalPrice((int) total_price);

        startDateToSave.setText(newRent.getStartDateRent().toString());
        returnDateToSave.setText(newRent.getEndDaterRent().toString());
        pickUpAddressToSave.setText(newRent.getPickUpAddress());
        returnAddressToSave.setText(newRent.getReturnAddress());
        totalPriceToSave.setText(String.valueOf(newRent.getTotalPrice()));

        brandToSave.setText(carToSave.getBrand());
        modelToSave.setText(carToSave.getModel());
        regNumbToSave.setText(carToSave.getRegistrationNumber());
        yearToSave.setText(String.valueOf(carToSave.getYear()));
        seatsToSave.setText(String.valueOf(carToSave.getSeats()));
        transmissionToSave.setText(carToSave.getTransmission());
        fuelTypeToSave.setText(carToSave.getFuelType());
        engineCapacityToSave.setText(String.valueOf(carToSave.getEngineCapacity()));
        pricePerDayToSave.setText(String.valueOf(carToSave.getPricePerDay()));

        nameToSave.setText(clientToSave.getName());
        emailToSave.setText(clientToSave.getEmail());
        phoneToSave.setText(String.valueOf(clientToSave.getPhone()));
    }

    @FXML
    public void actionBtnBackToSelectClient(ActionEvent actionEvent) {
        paneClientRent.toFront();
    }

    @FXML
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
}