package com.example.carrental.Services;

import com.example.carrental.Model.Rent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import com.example.carrental.Services.DatabaseService;


public class AlertService {
    DatabaseService databaseService = new DatabaseService();

    public static void confirmCreatePdfAskSendMail(String title, String text, int rentId, String clientMail) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        ButtonType sendButton = new ButtonType("Trimite email");
        ButtonType cancelButton = new ButtonType("Anulează");

        alert.getButtonTypes().setAll(sendButton, cancelButton);

        // Manipularea răspunsului utilizatorului
        alert.showAndWait().ifPresent(response -> {
            if (response == sendButton) {
                try {
//                    new MailService().sendMail(rentId, clientMail);
                    MailService mailService = new MailService();
                    mailService.sendMail(rentId, clientMail);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Trimite emailul cu factura");
            } else if (response == cancelButton) {
                // Codul pentru anularea trimiterea emailului
                System.out.println("Trimiterea emailului a fost anulată");
            }
        });
    }

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
