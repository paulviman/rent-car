package com.example.carrental.Controller;

//import com.example.carrental.Database.ConnectionPool;

import com.example.carrental.Main;
import com.example.carrental.Model.User;
import com.example.carrental.Services.AlertService;
import com.example.carrental.Services.DatabaseService;
import com.example.carrental.Services.ValidationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import com.example.carrental.Services.Encrypt;

public class LoginController extends Component implements Initializable {

    @FXML
    private Pane panelSignIn;
    @FXML
    private Pane panelSignUp;
    @FXML
    private TextField tfSignInEmail;
    @FXML
    private PasswordField tfSignInPassword;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnSignInToSignUp;
    @FXML
    private Button btnSignUp;
    @FXML
    private TextField tfSignUpEmail;
    @FXML
    private TextField tfSignUpName;
    @FXML
    private TextField thSignUpPhone;
    @FXML
    private TextField thSignUpAddress;
    @FXML
    private PasswordField tfSignUpPassword;
    @FXML
    private PasswordField tfSignUpConfirmPassword;
    ValidationService validationService = new ValidationService();
    AlertService alertService = new AlertService();
    DatabaseService databaseService = new DatabaseService();
    Encrypt encrypt = new Encrypt();

    private User userLogIn;
    @FXML
    private Button btnBackToSignIn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panelSignIn.toFront();

    }

    //    private boolean isEnterKeyPressed(ActionEvent event) {
//        if (event.equals(KeyCode.ENTER)) {
//            KeyEvent keyEvent = (KeyEvent) event;
//            return keyEvent.getCode() == KeyCode.ENTER;
//        }
//        return false;
//    }
    @FXML
    private void handleButtonEvent(ActionEvent event) {
        if (event.getSource().equals(btnSignInToSignUp)) {
            panelSignUp.toFront();
//            new ZoomIn(panelSignUp).play();
//            panelSignUp.toFront();
        }
        if (event.getSource().equals(btnSignIn)) {
            //panelSignIn.toFront();

            String email = tfSignInEmail.getText();
            String password = tfSignInPassword.getText();

            userLogIn = getAuthenticatedUser(email, password);

            if (userLogIn != null) {
                System.out.println(userLogIn.getName() + userLogIn.getRole());

                System.out.println(userLogIn);
                try {

//                    Stage loginStage = (Stage) btnSignIn.getScene().getWindow();
//                    loginStage.close();

                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("dashboard.fxml"));
                    Parent root = loader.load();
                    DashboardController dashboardController = loader.getController();
                    //dashboardController.setUser(userLogIn);
                    dashboardController.initialize(userLogIn);
                    Scene scene = new Scene(root);
                    Stage dashboardStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    dashboardStage.setScene(scene);
                    dashboardStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //deschide dashboard
            } else {
                alertService.newAlert("Error", "Incorrect credentials");
            }
        }
        if (event.getSource().equals(btnSignUp)) {
            if (registerUser()) {
                panelSignIn.toFront();
            }
        }
    }

    private User getAuthenticatedUser(String email, String password) {
        return databaseService.getAuthenticatedUserFromDB(email, password);
    }

    private boolean registerUser() {
        String name = tfSignUpName.getText();
        String email = tfSignUpEmail.getText();
        String phone = thSignUpPhone.getText();
        String address = thSignUpAddress.getText();
        String password = tfSignUpPassword.getText();
        String confirmPassword = tfSignUpConfirmPassword.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            alertService.newAlert("Error", "Fill in all the fields!");
            return false;
        }

        if (!validationService.emailValidation(email)) {
            alertService.newAlert("Error", "Invalid email!\nAccepted form: example@gmail.com");
            return false;
        }
        if (databaseService.getUserEmail(email)) {
            alertService.newAlert("Error", "Email is already in use!");
            return false;
        }
        if (!validationService.nameValidation(name)) {
            alertService.newAlert("Error", "Invalid Name!\nAccepted form: Popescu Ion");
            return false;
        }
        if (!validationService.phoneValidation(phone)) {
            alertService.newAlert("Error", "Invalid phone!\nAccepted form: 0712312312");
            return false;
        }
        if (!validationService.addressValidation(address)) {
            alertService.newAlert("Error", "Invalid address!\nAccepted form: Romania, Bucuresti str. Oituz nr. 7 etc.");
            return false;
        }
        if (!validationService.passwordValidation(password)) {
            alertService.newAlert("Error", "Invalid password!\nThe password must contain at least one lowercase character, one uppercase character, one digit, one special character\nMinimum 8 characters");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            alertService.newAlert("Error", "The password does not match!");
            return false;
        }

        userLogIn = addUser(name, email, phone, address, password);
        if (userLogIn != null) {
            alertService.newConfirmation("Successful", "You have successfully registered!");
            //close();
            return true;
        } else {
            alertService.newAlert("Error", "Registration failed!");
            return false;
        }
        //return succes;
    }

    private User addUser(String name, String email, String phone, String address, String password) {
        return databaseService.addUserToDB(name, email, phone, address, encrypt.encrypt(password));
    }

    @FXML
    public void actionBtnBackToSignIn(ActionEvent actionEvent) {

        panelSignIn.toFront();
    }
}
