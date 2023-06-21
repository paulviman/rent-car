package com.example.carrental.Controller;

import com.example.carrental.Model.Client;
import com.example.carrental.Model.User;
import com.example.carrental.Services.AlertService;
import com.example.carrental.Services.DatabaseService;
import com.example.carrental.Services.ValidationService;
import com.google.api.client.util.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditUser implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button btnSaveEditUser;
    @FXML
    private Button btnCancelEditUser;

    User user;
    @FXML
    private ComboBox comboBoxEditUser;
    DatabaseService databaseService = new DatabaseService();

    public void setUser(User user) {
        this.user = user;

        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        phoneField.setText("0" + user.getPhone());
        addressField.setText(user.getAddress());
        if (user.getRole() == 1) {
            comboBoxEditUser.setValue("admin");
        } else {
            comboBoxEditUser.setValue("user");
        }
        passwordField.setText(user.getPassword());
    }

    ValidationService validationService = new ValidationService();
    AlertService alertService = new AlertService();

    @FXML
    public void saveUser(ActionEvent actionEvent) {

        User user1 = new User();
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String address = addressField.getText();
        int role=0;
        if (comboBoxEditUser.getValue() != null && comboBoxEditUser.getValue().equals("admin")) {
            role = 1;
        } else if(comboBoxEditUser.getValue().equals("user")) {
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

        user1.setId(user.getId());
        user1.setName(name);
        user1.setEmail(email);
        user1.setPhone(phone);
        user1.setRole(role);
        user1.setAddress(address);
        user1.setPassword(password);
        System.out.println(user1.getRole()+ "rollllllllllllll");

        if (databaseService.editUser(user1)) {
            alertService.editConfirmation(btnSaveEditUser);
//            actionBtnEmployee(actionEvent);
//            Tab tab = paneEmployee.getTabs().get(0);
//            paneEmployee.getSelectionModel().select(tab);
        } else {
            alertService.newAlert("Error", "The user could not be added!");
        }

//        addEmplyeEmail.setText(null);
//        addEmplyeName.setText(null);
//        addEmplyePhone.setText(null);
//        addEmplyeAddress.setText(null);
//        addEmplyePassword.setText(null);
//        comboboxRole.setValue("role");
    }

    @FXML
    public void actionCancelEditUser(ActionEvent actionEvent) {
        // Obține obiectul Stage asociat scenei curente
        Stage stage = (Stage) btnCancelEditUser.getScene().getWindow();

        // Închide fereastra
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxEditUser.getItems().addAll("admin", "user");


    }
}
