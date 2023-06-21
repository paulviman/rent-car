package com.example.carrental.Services;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import javafx.stage.Stage;


public class AlertService {
    DatabaseService databaseService = new DatabaseService();

    public static void confirmCreatePdfAskSendMail(String title, String text, int rentId, String clientMail) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        ButtonType sendButton = new ButtonType("Send email");
        ButtonType cancelButton = new ButtonType("Cancel");

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
                //System.out.println("Trimite emailul cu factura");
            } else if (response == cancelButton) {
                // Codul pentru anularea trimiterea emailului
                System.out.println("The sending of the email has been cancelled");
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

    public void editConfirmation(Button btnEdit) {
        // Crează un obiect de tip Alert cu tipul Confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm edit");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to save your changes?");

        // Obține butonul "OK" din fereastra de dialog de confirmare
        ButtonType okButton = ButtonType.OK;

        // Adaugă butonul "OK" la fereastra de dialog
        confirmationAlert.getButtonTypes().setAll(okButton);

        // Așteaptă până când utilizatorul apasă butonul "OK"
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Verifică dacă utilizatorul a apăsat butonul "OK"
        if (result.isPresent() && result.get() == okButton) {
            // Închide fereastra de editare
            Stage stage = (Stage) btnEdit.getScene().getWindow();
            stage.close();
        }
    }
}
