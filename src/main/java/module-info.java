module com.example.carrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.net.http;
    requires com.gluonhq.charm.glisten;


    opens com.example.carrental to javafx.fxml;
    exports com.example.carrental;
    exports com.example.carrental.Controller;
    opens com.example.carrental.Controller to javafx.fxml;
}