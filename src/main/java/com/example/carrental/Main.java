package com.example.carrental;

import com.example.carrental.Services.DatabaseService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main extends Application {
    static DatabaseService databaseService = new DatabaseService();
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CarRent");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        try {
//            // Schedulează job-ul de actualizare a disponibilității masinilor
//            DailyUpdateJob.scheduleJob();
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }

        Thread carAvailabilityThread = new Thread(() -> {
            while (true) {
                try {
                    LOGGER.info("Am inceput sa verific daca masinile sunt valide sau nu pt inchiriere");
                    databaseService.updateCarAvailabilityDaily(); // metoda care actualizează disponibilitatea mașinilor
                    Thread.sleep(24 * 60 * 60 * 1000); // așteaptă 24 de ore (86400000 milisecunde) înainte de a actualiza din nou, in cazul in care aplicatia nu este inchisa
                    LOGGER.info("Finalizare verificare valabilitate masini");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        carAvailabilityThread.setDaemon(true); // setează firul de execuție ca fiind daemon
        carAvailabilityThread.start(); // pornește firul de execuție

        launch();
    }
}