package com.example.carrental.Controller;

import com.example.carrental.Model.Car;
import com.example.carrental.Services.AlertService;
import com.example.carrental.Services.DatabaseService;
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

        carToEdit.setId(this.car.getId());
        carToEdit.setBrand(brandTextField.getText());
        carToEdit.setModel(modelTextField.getText());
        carToEdit.setRegistrationNumber(regNumbTextField.getText());
        carToEdit.setYear(Integer.parseInt(yearTextField.getText()));
        carToEdit.setPricePerDay(Integer.parseInt(pricePerDayTextField.getText()));
        carToEdit.setEngineCapacity(Float.parseFloat(engineCapacityTextField.getText()));
        carToEdit.setSeats(Integer.parseInt(seatsTextField.getText()));
        carToEdit.setFuelType(fuelTypeTextField.getText());
        carToEdit.setTransmission(transmissionTextField.getText());
        carToEdit.setAvailable((Boolean) availableComboCox.getValue());

        if (databaseService.editCar(carToEdit)) {
            alertService.editConfirmation(btnEdit);
            //alertService.newConfirmation("Reusit", "Ati editat cu succes");
        } else {
            alertService.newAlert("Eroare", "Nu s-au putut salva modificariel");
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
