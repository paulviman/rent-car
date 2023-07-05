package com.example.carrental.Controller;

import com.example.carrental.Model.Car;
import com.example.carrental.Services.AlertService;
import com.example.carrental.Services.DatabaseService;
import com.example.carrental.Services.ValidationService;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditCarController {
    @javafx.fxml.FXML
    private TextField brandTextField;
    @javafx.fxml.FXML
    private TextField modelTextField;
    @javafx.fxml.FXML
    private TextField pricePerDayTextField;
    @javafx.fxml.FXML
    private TextField seatsTextField;
    @javafx.fxml.FXML
    private TextField engineCapacityTextField;
    @javafx.fxml.FXML
    private TextField fuelTypeTextField;
    @javafx.fxml.FXML
    private TextField transmissionTextField;

    private Car car;
    @javafx.fxml.FXML
    private Button btnCancelEdit;
    @javafx.fxml.FXML
    private Button btnEdit;

    DatabaseService databaseService = new DatabaseService();
    AlertService alertService = new AlertService();
    ValidationService validationService = new ValidationService();
    @javafx.fxml.FXML
    private TextField regNumbTextField;
    @FXML
    private TextField yearTextField;
    @javafx.fxml.FXML
    private ComboBox availableComboCox = new ComboBox<>();

    ObservableList<Boolean> option = FXCollections.observableArrayList(
            true,
            false
    );

    @javafx.fxml.FXML
    public void initialize() {
        availableComboCox.setItems(option);
    }

    public void setCar(Car car) {
        this.car = car;

        brandTextField.setText(car.getBrand());
        modelTextField.setText(car.getModel());
        regNumbTextField.setText(car.getRegistrationNumber());
        yearTextField.setText(String.valueOf(car.getYear()));
        pricePerDayTextField.setText(String.valueOf(car.getPricePerDay()));
        seatsTextField.setText(String.valueOf(car.getSeats()));
        engineCapacityTextField.setText(String.valueOf(car.getEngineCapacity()));
        fuelTypeTextField.setText(car.getFuelType());
        transmissionTextField.setText(car.getTransmission());
        availableComboCox.setValue(car.isAvailable());


    }


    @javafx.fxml.FXML
    public void actionBtnEdit(ActionEvent actionEvent) {
        Car carToEdit = new Car();

        String brand = brandTextField.getText();
        String model = modelTextField.getText();
        String regNumb = regNumbTextField.getText();
        String year = yearTextField.getText();
        String priceDay = pricePerDayTextField.getText();
        String seats = seatsTextField.getText();
        String transmission = transmissionTextField.getText();
        String fuelType = fuelTypeTextField.getText();
        String engineCapacity = engineCapacityTextField.getText();

        if (brand.isEmpty() || model.isEmpty() || regNumb.isEmpty() || year.isEmpty() ||
                priceDay.isEmpty() || seats.isEmpty() || transmission.isEmpty() || fuelType.isEmpty() || engineCapacity.isEmpty()) {
            alertService.newAlert("Error", "Fill in all the fields!");
            return;
        }

        if (!validationService.brandValidation(brand)) {
            alertService.newAlert("Error", "Invalid brand!\n" +
                    "It must contain only letters");
            return;
        }
        if (!validationService.modelValidation(model)) {
            alertService.newAlert("Error", "Invalid model!\n" +
                    "It can only contain letters and numbers");
            return;
        }
        if (!validationService.regNumbValidation(regNumb)) {
            alertService.newAlert("Error", "Invalid registration number!\n" +
                    "It must be of the form: MM111ABC");
            return;
        }
        if (!validationService.yearValidation(year)) {
            alertService.newAlert("Error", "Invalid manufacturing year!\n" +
                    "It must be of the form: 2000");
            return;
        }
        if (!validationService.priceValidation(priceDay)) {
            alertService.newAlert("Error", "Invalid price!\n" +
                    "It must contain only numbers");
            return;
        }
        if (!validationService.seatsValidation(seats)) {
            alertService.newAlert("Error", "Invalid number of seats!\n" +
                    "It must contain only numbers");
            return;
        }
        if (!validationService.transmissionValidation(transmission)) {
            alertService.newAlert("Error", "Invalid transmission!\n" +
                    "It must contain only letters");
            return;
        }
        if (!validationService.fuelTypeValidation(fuelType)) {
            alertService.newAlert("Error", "Invalid fuel type!\n" +
                    "It must contain only letters");
            return;
        }
        if (!validationService.engineCapacityValidation(engineCapacity)) {
            alertService.newAlert("Error", "Invalid motor capacity!\n" +
                    "It must contain only numbers in the form:1.9");
            return;
        }
        carToEdit.setId(this.car.getId());
        carToEdit.setBrand(brandTextField.getText());
        carToEdit.setModel(modelTextField.getText());
        carToEdit.setRegistrationNumber(regNumbTextField.getText());
        carToEdit.setYear(Integer.parseInt(yearTextField.getText()));
        carToEdit.setPricePerDay(Integer.parseInt(pricePerDayTextField.getText()));
        carToEdit.setEngineCapacity(Double.parseDouble(engineCapacityTextField.getText()));
        carToEdit.setSeats(Integer.parseInt(seatsTextField.getText()));
        carToEdit.setFuelType(fuelTypeTextField.getText());
        carToEdit.setTransmission(transmissionTextField.getText());
        carToEdit.setAvailable((Boolean) availableComboCox.getValue());

        if (databaseService.editCar(carToEdit)) {
            alertService.editConfirmation(btnEdit);
            //alertService.newConfirmation("Reusit", "Ati editat cu succes");
        } else {
            alertService.newAlert("Error", "Changes could not be saved!");
        }

    }


    @javafx.fxml.FXML
    public void actionBtnCancelEdit(ActionEvent actionEvent) {
        // Obține obiectul Stage asociat scenei curente
        Stage stage = (Stage) btnCancelEdit.getScene().getWindow();

        // Închide fereastra
        stage.close();
    }
}
