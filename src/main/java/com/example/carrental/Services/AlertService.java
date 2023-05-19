package com.example.carrental.Services;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import com.example.carrental.Services.DatabaseService;



public class AlertService {

    DatabaseService databaseService = new DatabaseService();

    public void newAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }


    public void newConfirmation(String reusit, String s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(reusit);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}
