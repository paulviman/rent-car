package com.example.carrental.Controller;

import com.example.carrental.Model.Client;
import com.example.carrental.Services.AlertService;
import com.example.carrental.Services.DatabaseService;
import com.example.carrental.Services.ValidationService;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class EditClientController {
    @javafx.fxml.FXML
    private TextField nameField;
    @javafx.fxml.FXML
    private TextField emailField;
    @javafx.fxml.FXML
    private TextField phoneField;

    DatabaseService databaseService = new DatabaseService();
    AlertService alertService = new AlertService();
    ValidationService validationService = new ValidationService();

    private Client client;
    @javafx.fxml.FXML
    private Button btnSaveEditClient;
    @javafx.fxml.FXML
    private Button btnCancel;

    public void saveClient(javafx.event.ActionEvent actionEvent) {
        Client clientToEdit = new Client();

        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

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

        clientToEdit.setId(this.client.getId());
        clientToEdit.setName(nameField.getText());
        clientToEdit.setEmail(emailField.getText());
        clientToEdit.setPhone(Integer.parseInt(phoneField.getText()));

        if (databaseService.editClient(clientToEdit)) {
            alertService.editConfirmation(btnSaveEditClient);
            //alertService.newConfirmation("Reusit", "Ati editat cu succes");
        } else {
            alertService.newAlert("Eroare", "Nu s-au putut salva modificariel");
        }
    }

    public void actionBtnCancel(javafx.event.ActionEvent actionEvent) {
        // Obține obiectul Stage asociat scenei curente
        Stage stage = (Stage) btnCancel.getScene().getWindow();

        // Închide fereastra
        stage.close();
    }

    public void setClient(Client client) {
        this.client = client;

        nameField.setText(client.getName());
        emailField.setText(client.getEmail());
        phoneField.setText("0" + client.getPhone());
    }

    @javafx.fxml.FXML
    public void saveClient(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void actionBtnCancel(ActionEvent actionEvent) {
    }
}

