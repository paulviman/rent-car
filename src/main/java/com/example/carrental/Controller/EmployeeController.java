package com.example.carrental.Controller;

import com.example.carrental.Main;
import com.example.carrental.Model.Client;
import com.example.carrental.Model.User;
import com.example.carrental.Services.DatabaseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class EmployeeController {
    @javafx.fxml.FXML
    private Label employeName;
    @javafx.fxml.FXML
    private Label employeEmail;
    @javafx.fxml.FXML
    private Label employePhone;
    @javafx.fxml.FXML
    private Label employeAddress;
    @javafx.fxml.FXML
    private Label employeRole;
    @javafx.fxml.FXML
    private Label employeNoRents;
    @javafx.fxml.FXML
    private Button btnEditEmploye;
    @javafx.fxml.FXML
    private Button bntDeleteEmploye;
    DatabaseService databaseService = new DatabaseService();
    User user;

    public void setUser(User user) {
        this.user = user;
    }

//    public void actionBtnEditEmploye(ActionEvent actionEvent) {
//    }
//
//    public void actionBtnDeleteEmploye(ActionEvent actionEvent) {
//
//    }

    @javafx.fxml.FXML
    public void actionBtnEditEmploye(ActionEvent actionEvent) {
        try {
            // Crează un obiect FXMLLoader pentru fișierul FXML de editare a mașinilor
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("editUser.fxml"));
            Parent root = loader.load();

            // Obține controller-ul pentru fereastra de editare a mașinilor
            EditUser controller = loader.getController();
            // System.out.println(user.getPassword());
            controller.setUser(user);

            // Obține mașina corespunzătoare acestei cărți
            //Car car = (Car) btnEdit.getUserData();

            // Inițializează formularul de editare cu informațiile despre mașină
            //controller.initialize(car);

            // Creează o nouă scenă pentru fereastra de editare a mașinilor și o afișează
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void actionBtnDeleteEmploye(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare ștergere");
        alert.setContentText("Sigur doriți să ștergeți acest user?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            databaseService.deleteUser(user.getId());
        }
    }

    public ArrayList<User> populateListEmployeFromDB() {
        return databaseService.getAllUsers();
    }


    public Node createCardEmployeNode(User user) {
        try {
            // încărcarea fișierului FXML pentru cardul de mașină
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("employe-card.fxml"));
            Parent root = loader.load();

            // obținerea controllerului pentru fișierul FXML
            EmployeeController controller = loader.getController();

            controller.setUser(user);

            controller.employeName.setText(user.getName());
            controller.employeEmail.setText(user.getEmail());
            controller.employePhone.setText("0" + user.getPhone());
            controller.employeAddress.setText(user.getAddress());
            controller.employeNoRents.setText(String.valueOf(user.getNo_rents()));
            if (user.getRole() == 1) {
                controller.employeRole.setText("Administartor");
            } else if (user.getRole() == 2) {
                controller.employeRole.setText("Angajat");

            }

            return root;
        } catch (
                IOException e) {
            e.printStackTrace();
            return null;

        }
    }
}
