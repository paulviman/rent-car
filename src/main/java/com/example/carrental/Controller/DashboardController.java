package com.example.carrental.Controller;

import com.example.carrental.Main;
import com.example.carrental.Model.Car;
import com.example.carrental.Model.Client;
import com.example.carrental.Model.Rent;
import com.example.carrental.Model.User;
import com.example.carrental.Services.AlertService;
import com.example.carrental.Services.DatabaseService;
import com.example.carrental.Services.ValidationService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import com.gluonhq.charm.glisten.control.CardPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DashboardController {
    private User user;
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
    private FlowPane panelDashboard;
    @FXML
    private TabPane panelRent;
    @FXML
    private TabPane panelClients;
    private static ArrayList<Car> cars = new ArrayList<>();
    private static ArrayList<Rent> rents = new ArrayList<>();
    private static ArrayList<Client> clients = new ArrayList<>();
    private static ArrayList<User> employe = new ArrayList<>();
    private CarCardController cardController = new CarCardController();
    private ClientController clientsController = new ClientController();
    private RentController rentController = new RentController();
    private EmployeeController employeController = new EmployeeController();
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
    private final ValidationService validationService = new ValidationService();
    ArrayList<Car> carsAvailable;
    @FXML
    private TextField textFieldSearchRents;
    @FXML
    private Button btnSearchRents;
    @FXML
    private ComboBox filterFuelType;
    @FXML
    private ComboBox filterTransmission;
    @FXML
    private ComboBox filterAvailability;
    @FXML
    private Tab tabRents;
    @FXML
    private GridPane cardGridRent;
    @FXML
    private PieChart pieChart;
    int numberOfAvailableCars = 0;
    int numberOfNotAvailableCars = 0;
    @FXML
    private LineChart lineChart;


    private LinkedHashMap<String, Float> yearTotalIncome = new LinkedHashMap<>() {{
        put("January", 0f);
        put("February", 0f);
        put("March", 0f);
        put("April", 0f);
        put("May", 0f);
        put("June", 0f);
        put("July", 0f);
        put("August", 0f);
        put("September", 0f);
        put("October", 0f);
        put("November", 0f);
        put("December", 0f);
    }};

    private LinkedHashMap<String, Integer> numberOfRentsPerUser = new LinkedHashMap<>();
    @FXML
    private TabPane panelCars;
    @FXML
    private Button btnRefreshCars;
    @FXML
    private Button rentRefresh;
    //    @FXML
//    private StackPane stackCreateRent;
    @FXML
    private Button btnRefreshClient;
    @FXML
    private StackPane stackCreateRent;
    @FXML
    private Button btnEmployee;
    @FXML
    private TabPane paneEmployee;
    @FXML
    private TextField searchEmplye;
    @FXML
    private Button btnSearchEmploye;
    @FXML
    private Button btnRefreshEmploye;
    @FXML
    private TextField addEmplyeName;
    @FXML
    private TextField addEmplyeEmail;
    @FXML
    private TextField addEmplyePhone;
    @FXML
    private TextField addEmplyeAddress;
    @FXML
    private ComboBox comboboxRole;
    @FXML
    private PasswordField addEmplyePassword;
    @FXML
    private CardPane cardPaneUsers;
    @FXML
    private Button btnAddUser;
    @FXML
    private ComboBox comboboxRentStatus;
    @FXML
    private BarChart barChart;
//    @FXML
//    private StackPane stackCreateRent;

    public void carRefresh() {
        cars = cardController.populateListCarFromDB();
    }

    public void setUser(User user1) {
        this.user = user1;
    }

    private ObjectProperty<User> userProperty = new SimpleObjectProperty<>();

    public ObjectProperty<User> userProperty() {
        return userProperty;
    }

    public final User getUser() {
        return userProperty.get();
    }

//    public final void setUser(User user) {
//        userProperty.set(user);
//    }

    @Deprecated
    public void initialize(User userLogIn) {

        panelDashboard.toFront();
        this.user = userLogIn;

//        userProperty.addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                int userRole = newValue.getRole();
//                if (userRole == 2) {
//                    // Ascundeți butoanele destinate rolului 1
//                    btnEmployee.setVisible(false);
//                } else if (userRole == 1) {
//                    // Afișați toate butoanele pentru rolul 2
//                    btnEmployee.setVisible(true);
//                }
//            }
//        });

        carsAvailable = databaseService.getAllCarsAvailable();
        cars = cardController.populateListCarFromDB();
        clients = clientsController.populateListClientsFromDB();
        rents = rentController.populateListRentFromDB(cars, clients);
        employe = employeController.populateListEmployeFromDB();

        for (Car car : cars) {
            if (car.isAvailable()) {
                numberOfAvailableCars++;
            } else numberOfNotAvailableCars++;
        }
        ObservableList<PieChart.Data> piChartData = FXCollections.observableArrayList(
                new PieChart.Data("Available cars", numberOfAvailableCars),
                new PieChart.Data("Not availabel cars", numberOfNotAvailableCars));


        pieChart.setData(piChartData);


        // Setarea etichetelor personalizate pentru fiecare secțiune
        pieChart.getData().forEach(data -> {
            double value = data.getPieValue();
            String label = String.format("%s (%d cars)", data.getName(), (int) value);
            data.setName(label);
        });

        for (Rent rent : rents) {
            System.out.println(rent.getEndDaterRent().getMonth());
            switch (rent.getEndDaterRent().getMonth()) {
                case JANUARY:
                    yearTotalIncome.put("January", yearTotalIncome.get("January") + rent.getTotalPrice());
                    break;
                case FEBRUARY:
                    yearTotalIncome.put("February", yearTotalIncome.get("February") + rent.getTotalPrice());
                    break;
                case MARCH:
                    yearTotalIncome.put("March", yearTotalIncome.get("March") + rent.getTotalPrice());
                    break;
                case APRIL:
                    yearTotalIncome.put("April", yearTotalIncome.get("April") + rent.getTotalPrice());
                    break;
                case MAY:
                    yearTotalIncome.put("May", yearTotalIncome.get("May") + rent.getTotalPrice());
                    break;
                case JUNE:
                    yearTotalIncome.put("June", yearTotalIncome.get("June") + rent.getTotalPrice());
                    break;
                case JULY:
                    yearTotalIncome.put("July", yearTotalIncome.get("July") + rent.getTotalPrice());
                    break;
                case AUGUST:
                    yearTotalIncome.put("August", yearTotalIncome.get("August") + rent.getTotalPrice());
                    break;
                case SEPTEMBER:
                    yearTotalIncome.put("September", yearTotalIncome.get("September") + rent.getTotalPrice());
                    break;
                case OCTOBER:
                    yearTotalIncome.put("October", yearTotalIncome.get("October") + rent.getTotalPrice());
                    break;
                case NOVEMBER:
                    yearTotalIncome.put("November", yearTotalIncome.get("November") + rent.getTotalPrice());
                    break;
                case DECEMBER:
                    yearTotalIncome.put("December", yearTotalIncome.get("December") + rent.getTotalPrice());
                    break;
                default:
                    break;

            }
        }
        XYChart.Series<String, Float> seriesLineChart = new XYChart.Series<>();

        for (Map.Entry<String, Float> entry : yearTotalIncome.entrySet()) {
            String month = entry.getKey();
            Float income = entry.getValue();

            XYChart.Data<String, Float> data = new XYChart.Data<>(month, income);
            seriesLineChart.getData().add(data);
        }

        lineChart.getData().add(seriesLineChart);

        for (User user : employe) {
            numberOfRentsPerUser.put(user.getName(), user.getNo_rents());
        }
        System.out.println("listaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + numberOfRentsPerUser);
        XYChart.Series<String, Integer> seriesBarChart = new XYChart.Series<>();

        for (Map.Entry<String, Integer> entryBar : numberOfRentsPerUser.entrySet()) {
            String name = entryBar.getKey();
            Integer rents = entryBar.getValue();

            XYChart.Data<String, Integer> dataBarChar = new XYChart.Data<>(name, rents);
            seriesBarChart.getData().add(dataBarChar);
        }

        barChart.getData().add(seriesBarChart);
        if (user.isAdmin()) {
            barChart.setVisible(true);
            btnEmployee.setVisible(true);
        }else {
            barChart.setVisible(false);
            btnEmployee.setVisible(false);
        }


        System.out.println(yearTotalIncome);

        carTabelForSelect.setItems(FXCollections.observableArrayList(carsAvailable));
        clientTabelForSelect.setItems(FXCollections.observableArrayList(clients));

        setTableCarsForRent();
        setTableClientsForRent();

        filterFuelType.getItems().addAll("Diesel", "Benzina", "Electric");
        filter.getItems().addAll("Ascending", "Descending");
        filterTransmission.getItems().addAll("Manual", "Automat");
        filterAvailability.getItems().addAll("Available", "Not available");
        comboboxRole.getItems().addAll("admin", "user");
        comboboxRentStatus.getItems().addAll("Completed", "Ongoing", "Waiting", "Discarded");
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
            Node cardNode = cardController.createCardNode(car, true);
            //cardContainer.getChildren().add(cardNode);
            //scrolPane.getItems().add(cardNode);
            cardPane.getItems().add(cardNode);
        }
        // cardPane.getItems().add(cardContainer);
    }

    private void addListRentToCard(ArrayList<Rent> rentDisplay) {
        // cardPaneRent.getItems().clear();
        cardGridRent.getChildren().clear();
        int row = 1;

        for (Rent rent : rentDisplay) {
            Node cardNode = rentController.createCardNode(rent);
            //cardPaneRent.getItems().add(cardNode);
            cardGridRent.addRow(row, cardNode);
            cardGridRent.setFillWidth(cardNode, true);
            row++;
        }

    }

    private void addListClientToCard(ArrayList<Client> clientsDisplay) {
        cardPaneClients.getItems().clear();

        // VBox cardContainer = new VBox();
        for (Client client : clientsDisplay) {
            Node cardNode = clientsController.createCardClientNode(client, true);
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
    public void actionBtnCars(ActionEvent actionEvent) throws Exception {
        cars = cardController.populateListCarFromDB();
        addListCarToCard(cars);
        panelCars.toFront();
    }

    @FXML
    public void actionBtnRent(ActionEvent actionEvent) {

        rents = rentController.populateListRentFromDB(cars, clients);
        System.out.println("userul este ::::::::::::::::" + user.getName());
        rentController.setUser(user);
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
            alertService.newAlert("Error", "Completati toate campurile!");
            return;
        }

        if (!validationService.brandValidation(brand)) {
            alertService.newAlert("Error", "Invalid Brand!\nIt must contain only letters");
            return;
        }
        if (!validationService.modelValidation(model)) {
            alertService.newAlert("Error", "Invalid Model!\nIt can only contain letters and numbers");
            return;
        }
        if (!validationService.regNumbValidation(regNumb)) {
            alertService.newAlert("Error", "Invalid registration number!\nIt must be of the form: MM11ABC");
            return;
        }
        if (!validationService.yearValidation(year)) {
            alertService.newAlert("Error", "Invalid manufacturing year!\nIt must be of the form: 2000");
            return;
        }
        if (!validationService.priceValidation(priceDay)) {
            alertService.newAlert("Error", "Invalid price!\nIt must contain only numbers");
            return;
        }
        if (!validationService.seatsValidation(seats)) {
            alertService.newAlert("Error", "Invalid number of seats!\nIt must contain only numbers");
            return;
        }
        if (!validationService.transmissionValidation(transmission)) {
            alertService.newAlert("Error", "Invalid transmission!\nIt must contain only letters");
            return;
        }
        if (!validationService.fuelTypeValidation(fuelType)) {
            alertService.newAlert("Error", "Invalid fuel type!\nIt must contain only letters");
            return;
        }
        if (!validationService.engineCapacityValidation(engineCapacity)) {
            alertService.newAlert("Error", "Invalid engine capacity!\nTit must contain only numbers in the form:1.9");
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

        try {
            databaseService.addCarToDatabase(car);
            alertService.newConfirmation("Successful", "You have successfully added a car!");

            addCarBrand.setText("");
            addCarModel.setText("");
            addCarRegNumb.setText("");
            addCarYear.setText("");
            addCarPrice.setText("");
            addCarSeats.setText("");
            addCarTransmission.setText("");
            addCarFuelType.setText("");
            addCarEngineCapacity.setText("");

            actionBtnRefreshCars(actionEvent);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            alertService.newAlert("Error", "The car could not be added!\nRegistration number already exists in db");
        }
        carsAvailable = databaseService.getAllCarsAvailable();
        carTabelForSelect.setItems(FXCollections.observableArrayList(carsAvailable));

        Tab tab = panelCars.getTabs().get(0);
        panelCars.getSelectionModel().select(tab);


//        if (databaseService.addCarToDatabase(car)) {
//            alertService.newConfirmation("Reusit", "Ati adugat o masina cu succes!");
//        } else {
//            alertService.newAlert("Eroare", "Masina nu am putu fi adaugata!");
//        }

    }

    @FXML
    public void actionBtnAddClient(ActionEvent actionEvent) {
        Client client = new Client();

        String name = addClientName.getText();
        String email = addClientEmail.getText();
        String phone = addClientPhone.getText();

        if (!validationService.nameValidation(name)) {
            alertService.newAlert("Error", "Invalid name!\n" +
                    "Accepted form: Popescu Ion");
            return;
        }
        if (!validationService.emailValidation(email)) {
            alertService.newAlert("Error", "Invalid email!\n" +
                    "Accepted form: example@gmail.com");
            return;
        }
        if (!validationService.phoneValidation(phone)) {
            alertService.newAlert("Error", "Invalid phone!\n" +
                    "Accepted form: 0712312312");
            return;
        }

        client.setName(addClientName.getText());
        client.setEmail(addClientEmail.getText());
        client.setPhone(Integer.parseInt(addClientPhone.getText()));

        if (databaseService.addClientToDatabase(client)) {
            alertService.newConfirmation("Successful", "Client successfully added!");
            actionBtnRefreshClient(actionEvent);
            Tab tab = panelClients.getTabs().get(0);
            panelClients.getSelectionModel().select(tab);
        } else {
            alertService.newAlert("Error", "The client could not be added!");
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

            alertService.newAlert("Error", "Please fill in all the fields before continuing!");
            return;

        }
        if (!validationService.dateValidation(pickUpDateLabel.getValue(), returnDateLabel.getValue())) {
            alertService.newAlert("Error", "PickUpDate must be greater than the current day\n" +
                    "ReturnDate must be greater than PickUpDate");
            return;
        }
        if (!validationService.addressValidation(pickUpAddressLabel.getText()) && !validationService.addressValidation(returnAddressLabel.getText())) {
            alertService.newAlert("Error", "Invalid pickup or delivery address!");
            return;
        }

        System.out.println(pickUpDateLabel.getValue());
        newRent.setStartDateRent(pickUpDateLabel.getValue());
        newRent.setEndDaterRent(returnDateLabel.getValue());
        newRent.setPickUpAddress(pickUpAddressLabel.getText());
        newRent.setReturnAddress(returnAddressLabel.getText());

        carsAvailable = databaseService.getAllCarsAvailableForASpecificDate(pickUpDateLabel.getValue(), returnDateLabel.getValue());
        carTabelForSelect.setItems(FXCollections.observableArrayList(carsAvailable));

        paneCarRent.toFront();


    }

    @FXML
    public void actionBtnBackToSelectCar(ActionEvent actionEvent) {
        paneCarRent.toFront();
    }

    @FXML
    public void actionBtnSaveRent(ActionEvent actionEvent) {


        databaseService.saveRent(newRent);
        databaseService.setCarAvailability(newRent.getCarId(), false);

        //eliberez memorie
        pickUpDateLabel.setValue(null);
        returnDateLabel.setValue(null);
        pickUpAddressLabel.setText("");
        returnAddressLabel.setText("");
        selectedCar = null;
        selectedClient = null;
        newRent = new Rent();
        user.setNo_rents(user.getNo_rents() + 1);
        databaseService.addUserNoRents(user);


        alertService.newConfirmation("Successful", "Successful rental!");

        paneDateRent.toFront();

        Tab tab = panelRent.getTabs().get(0);
        panelRent.getSelectionModel().select(tab);

//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Ok");
//        alert.setHeaderText("Inchirierea a fost salvata!");
//        alert.setContentText("Inchiriere efectuata cu succes");
//
//        alert.showAndWait();

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
            Node cardNode = cardController.createCardNode(car, false);
//            Button editButton = (Button) cardNode.lookup(".edit-button");
//            Button deleteButton = (Button) cardNode.lookup(".delete-button");
//            editButton.setVisible(false);
//            deleteButton.setVisible(false);

            paneCardCarSelected.getItems().add(cardNode);
        } else if (node.getId().equals(clientTabelForSelect.getId())) {

            System.out.println("am accesat tabelul clientiiii");
            Client client = (Client) clientTabelForSelect.getSelectionModel().getSelectedItem();
            newRent.setClientId(client.getId());

            selectedClient = clients.stream().filter(client1 -> client1.getId() == client.getId()).findFirst();

            paneCardClientSelected.getItems().clear();
            Node cardNode = clientsController.createCardClientNode(client, false);
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
            alertService.newAlert("You have not selected any car!", "Please select the desired car before continuing!");
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Eroare");
//            alert.setHeaderText("Nu ati selectat nicio masina");
//            alert.setContentText("Va rog sa selectati masina dorita inainte de a continua!");
//
//            alert.showAndWait();
        } else {
            newRent.setRentCar(selectedCar.get());
            System.out.println(newRent.getRentCar().getModel());
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
        if (selectedClient == null) {
            alertService.newAlert("Error", "You have not selected any client!");
        } else {
            setLabelsToSave();
            paneSaveRent.toFront();
        }
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

    @FXML
    public void actionBtnSearchRents(ActionEvent actionEvent) {
        String searchText = textFieldSearchRents.getText().toLowerCase();

        ArrayList<Rent> searchResults = new ArrayList<>();

        for (Rent rent : rents) {
            if (rent.getRentClient().getName().toLowerCase().contains(searchText) ||
                    rent.getRentCar().getRegistrationNumber().toLowerCase().contains(searchText)) {
                searchResults.add(rent);
            }
        }
        System.out.println(searchResults);

        addListRentToCard(searchResults);
    }

    @FXML
    public void actionFilter(ActionEvent actionEvent) {
        ArrayList carsFiltred = new ArrayList();

        if (filter.getValue().equals("Ascending")) {
            Collections.sort(cars, Comparator.comparing(Car::getPricePerDay));
        } else if (filter.getValue().equals("Descending")) {
            Collections.sort(cars, Comparator.comparing(Car::getPricePerDay).reversed());
        }
        addListCarToCard(cars);
    }

    @FXML
    public void actionFilterFuelType(ActionEvent actionEvent) {
        ArrayList carsFiltred = new ArrayList();

        if (filterFuelType.getValue().equals("Diesel")) {
            for (Car car : cars) {
                if (car.getFuelType().equals("diesel")) {
                    carsFiltred.add(car);
                }
            }

        } else if (filterFuelType.getValue().equals("Benzina")) {
            for (Car car : cars) {
                if (car.getFuelType().equals("benzina")) {
                    carsFiltred.add(car);
                }
            }

        } else if (filterFuelType.getValue().equals("Electric")) {
            for (Car car : cars) {
                if (car.getFuelType().equals("electric")) {
                    carsFiltred.add(car);
                }
            }
        }
        addListCarToCard(carsFiltred);
        //filterFuelType.setValue("Fuel Type");
    }

    @FXML
    public void actionFilterTransmission(ActionEvent actionEvent) {
        ArrayList carsFiltred = new ArrayList();

        if (filterTransmission.getValue().equals("Manual")) {
            for (Car car : cars) {
                if (car.getTransmission().equals("manual")) {
                    carsFiltred.add(car);
                }
            }

        } else if (filterTransmission.getValue().equals("Automat")) {
            for (Car car : cars) {
                if (car.getTransmission().equals("automat")) {
                    carsFiltred.add(car);
                }
            }
        }
        addListCarToCard(carsFiltred);
        // filterTransmission.setValue("Transmission");
    }

    @FXML
    public void actionFilterAvailability(ActionEvent actionEvent) {
        ArrayList carsFiltred = new ArrayList();

        if (filterAvailability.getValue().equals("Available")) {
            for (Car car : cars) {
                if (car.isAvailable()) {
                    carsFiltred.add(car);
                }
            }

        } else if (filterAvailability.getValue().equals("Not available")) {
            for (Car car : cars) {
                if (!car.isAvailable()) {
                    carsFiltred.add(car);
                }
            }
        }
        addListCarToCard(carsFiltred);
    }

    @FXML
    public void actionBtnRefreshCars(ActionEvent actionEvent) {
        try {
            actionBtnCars(actionEvent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void actionBtnRentRefresh(ActionEvent actionEvent) {
        actionBtnRent(actionEvent);
    }

    @FXML
    public void actionBtnRefreshClient(ActionEvent actionEvent) {
        actionBtnClients(actionEvent);
    }

    @FXML
    public void actionBtnEmployee(ActionEvent actionEvent) {
        employe = employeController.populateListEmployeFromDB();
        addListEmployeToCard(employe);

        paneEmployee.toFront();
    }

    private void addListEmployeToCard(ArrayList<User> employe) {
        cardPaneUsers.getItems().clear();

        // VBox cardContainer = new VBox();
        for (User user1 : employe) {
            Node cardNode = employeController.createCardEmployeNode(user1);
            //cardContainer.getChildren().add(cardNode);
            //scrolPane.getItems().add(cardNode);
            cardPaneUsers.getItems().add(cardNode);
        }
    }

    @FXML
    public void actionBtnSearchEmploye(ActionEvent actionEvent) {

        String searchText = searchEmplye.getText().toLowerCase();
        System.out.println("caut useriiiiiiiiiiiii" + searchText);
        ArrayList<User> searchResults = new ArrayList<>();

        for (User user : employe) {
            if (user.getName().toLowerCase().contains(searchText)) {
                searchResults.add(user);
            }
        }

        addListEmployeToCard(searchResults);

    }

    @FXML
    public void actionBtnRefreshEmploye(ActionEvent actionEvent) {
        actionBtnEmployee(actionEvent);
    }

    @FXML
    public void actionBtnAddUser(ActionEvent actionEvent) {
        User user1 = new User();

        String name = addEmplyeName.getText();
        String email = addEmplyeEmail.getText();
        String phone = addEmplyePhone.getText();
        String password = addEmplyePassword.getText();
        String address = addEmplyeAddress.getText();
        int role;
        if (comboboxRole.getItems().toString().equals("admin")) {
            role = 1;
        } else {
            role = 2;
        }


        if (!validationService.nameValidation(name)) {
            alertService.newAlert("Error", "Invalid name!\n" +
                    "Accepted form: Popescu Ion");
            return;
        }
        if (!validationService.emailValidation(email)) {
            alertService.newAlert("Error", "Invalid email!\n" +
                    "Accepted form: example@gmail.com");
            return;
        }
        if (!validationService.phoneValidation(phone)) {
            alertService.newAlert("Error", "Invalid phone!\n" +
                    "Accepted form: 0712312312");
            return;
        }
        if (!validationService.addressValidation(address)) {
            alertService.newAlert("Error", "Invalid address!");
            return;
        }
        if (!validationService.passwordValidation(password)) {
            alertService.newAlert("Error", "Invalid password!");
            return;
        }

        user1.setName(addEmplyeName.getText());
        user1.setEmail(addEmplyeEmail.getText());
        user1.setPhone(addEmplyePhone.getText());
        user1.setRole(role);
        user1.setAddress(address);
        user1.setPassword(password);

        if (databaseService.addEmployeeToDatabase(user1)) {
            alertService.newConfirmation("Successful", "User successfully added!");
            actionBtnEmployee(actionEvent);
            Tab tab = paneEmployee.getTabs().get(0);
            paneEmployee.getSelectionModel().select(tab);
        } else {
            alertService.newAlert("Error", "The user could not be added!");
        }

        addEmplyeEmail.setText(null);
        addEmplyeName.setText(null);
        addEmplyePhone.setText(null);
        addEmplyeAddress.setText(null);
        addEmplyePassword.setText(null);
        comboboxRole.setValue("role");
    }

    @FXML
    public void actionComboboxRentStatus(ActionEvent actionEvent) {

        ArrayList rentFiltred = new ArrayList();
        LocalDate now = LocalDate.now();

        if (comboboxRentStatus.getValue().equals("Completed")) {
            for (Rent rent : rents) {
                if (rent.getEndDaterRent().isBefore(now) && rent.isAvailable()) {
                    rentFiltred.add(rent);
                }
            }
        } else if (comboboxRentStatus.getValue().equals("Ongoing")) {
            for (Rent rent : rents) {
                if (rent.getEndDaterRent().isAfter(now) && rent.getStartDateRent().isBefore(now) && rent.isAvailable()) {
                    rentFiltred.add(rent);
                }
            }
        } else if (comboboxRentStatus.getValue().equals("Waiting")) {
            for (Rent rent : rents) {
                if (rent.getStartDateRent().isAfter(now) && rent.isAvailable()) {
                    rentFiltred.add(rent);
                }
            }
        } else if (comboboxRentStatus.getValue().equals("Discarded")) {
            for (Rent rent : rents) {
                if (!rent.isAvailable()) {
                    rentFiltred.add(rent);
                }
            }
        }
        addListRentToCard(rentFiltred);
    }
}
