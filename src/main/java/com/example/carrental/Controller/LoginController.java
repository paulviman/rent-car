package com.example.carrental.Controller;

//import com.example.carrental.Database.ConnectionPool;

import com.example.carrental.Main;
import com.example.carrental.Model.User;
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

            }
        }
        if (event.getSource().equals(btnSignUp)) {
            boolean registerSucces = registerUser();
            if (registerSucces) {
                panelSignIn.toFront();
            }
        }
    }

    private User getAuthenticatedUser(String email, String password) {

        User user1 = null;

        final String DB_URL = "jdbc:postgresql://localhost:5432/rent-car";
        final String USERNAME = "postgres";
        final String PASSWORD = "postgres";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user1 = new User();
                user1.name = resultSet.getString("name");
                user1.email = resultSet.getString("email");
                user1.phone = resultSet.getString("phone");
                user1.address = resultSet.getString("address");
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user1;
    }

    private boolean registerUser() {
        boolean succes = true;
        String name = tfSignUpName.getText();
        String email = tfSignUpEmail.getText();
        String phone = thSignUpPhone.getText();
        String address = thSignUpAddress.getText();
        String password = tfSignUpPassword.getText();
        String confirmPassword = tfSignUpConfirmPassword.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            succes = false;
            return succes;
        }

        if (!password.equals(confirmPassword)) {
//            JOptionPane.showMessageDialog(this,
//                    "Confirm Password does not match",
//                    "Try again",
//                    JOptionPane.ERROR_MESSAGE);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare");
            alert.setHeaderText(null);
            alert.setContentText("Parola nu corespunde! Vă rugăm să introduceți aceeași parolă în ambele câmpuri.");
            alert.showAndWait();
            succes = false;
            return succes;
        }

        userLogIn = addUserToDatabase(name, email, phone, address, password);
        if (userLogIn != null) {
            //close();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new userLogIn",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
        return succes;
    }

    private User addUserToDatabase(String name, String email, String phone, String address, String password) {
        User user = null;
        final String DB_URL = "jdbc:postgresql://localhost:5432/rent-car";
        final String USERNAME = "postgres";
        final String PASSWORD = "postgres";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (name, email, phone, address, password) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, password);

            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.name = name;
                user.email = email;
                user.phone = phone;
                user.address = address;
                user.password = password;
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
