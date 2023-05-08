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
    DashboardController dashboardController;
    ValidationService validationService = new ValidationService();
    AlertService alertService = new AlertService();
    DatabaseService databaseService = new DatabaseService();

    private User userLogIn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panelSignIn.toFront();

    }

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
                System.out.println(userLogIn.getName());

                System.out.println(userLogIn);
                try {

//                    Stage loginStage = (Stage) btnSignIn.getScene().getWindow();
//                    loginStage.close();

                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("dashboard.fxml"));
                    Parent root = loader.load();
                    dashboardController = loader.getController();
                    dashboardController.user = userLogIn;
                    Scene scene = new Scene(root);
                    Stage dashboardStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    dashboardStage.setScene(scene);
                    dashboardStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //deschide dashboard
            } else {
                alertService.newAlert("Eroare", "Cont inexistent!");
            }
        }
        if (event.getSource().equals(btnSignUp)) {
            if (registerUser()) {
                panelSignIn.toFront();
            }
        }
    }

    private User getAuthenticatedUser(String email, String password) {

//        User user1 = null;
//
//        final String DB_URL = "jdbc:postgresql://localhost:5432/rent-car";
//        final String USERNAME = "postgres";
//        final String PASSWORD = "postgres";
//        try {
//            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
//            statement.setString(1, email);
//            statement.setString(2, password);
//
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                user1 = new User();
//                user1.name = resultSet.getString("name");
//                user1.email = resultSet.getString("email");
//                user1.phone = resultSet.getString("phone");
//                user1.address = resultSet.getString("address");
//            }
//
//            statement.close();
//            connection.close();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
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
            alertService.newAlert("Eroare", "Completati toate campurile!");
            return false;
        }

        if (!validationService.emailValidation(email)) {
            alertService.newAlert("Eroare", "Email invalid!\nForma acceptata: example@gmail.com");
            return false;
        }
        if (databaseService.getUserEmail(email)) {
            alertService.newAlert("Eroare", "Email este deja utilizat!");
            return false;
        }
        if (!validationService.nameValidation(name)) {
            alertService.newAlert("Eroare", "Nume invalid!\nForma acceptata: Popescu Ion");
            return false;
        }
        if (!validationService.phoneValidation(phone)) {
            alertService.newAlert("Eroare", "Telefon invalid!\nForma acceptata: 0712312312");
            return false;
        }
        if (!validationService.addressValidation(address)) {
            alertService.newAlert("Eroare", "Adresa invalida!\nForma acceptata: Romania, Bucuresti str. Oituz nr. 7 etc.");
            return false;
        }
        if (!validationService.passwordValidation(password)) {
            alertService.newAlert("Eroare", "Parola invalida!\nParola trebuie sa contina minim un caracter mic, un caracter mare, o cifra, un caracter special\nMinim 8 caracter");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            alertService.newAlert("Eroare", "Parola nu corespunde!");
            return false;
        }

        userLogIn = addUser(name, email, phone, address, password);
        if (userLogIn != null) {
            alertService.newConfirmation("Reusit", "V-ati inregistrat cu succes!");
            //close();
            return true;
        } else {
            alertService.newAlert("Eroare", "Inregistrare esuata");
            return false;
        }
        //return succes;
    }

    private User addUser(String name, String email, String phone, String address, String password) {
//        User user = null;
//        final String DB_URL = "jdbc:postgresql://localhost:5432/rent-car";
//        final String USERNAME = "postgres";
//        final String PASSWORD = "postgres";
//
//        try {
//            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
//            // Connected to database successfully...
//
//            Statement stmt = conn.createStatement();
//            String sql = "INSERT INTO users (name, email, phone, address, password) " +
//                    "VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, email);
//            preparedStatement.setString(3, phone);
//            preparedStatement.setString(4, address);
//            preparedStatement.setString(5, password);
//
//            //Insert row into the table
//            int addedRows = preparedStatement.executeUpdate();
//            if (addedRows > 0) {
//                user = new User();
//                user.name = name;
//                user.email = email;
//                user.phone = phone;
//                user.address = address;
//                user.password = password;
//            }
//
//            stmt.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return databaseService.addUserToDB(name, email, phone, address, password);
    }
}
