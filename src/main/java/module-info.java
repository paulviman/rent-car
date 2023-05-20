module com.example.carrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.net.http;
    requires com.gluonhq.charm.glisten;
    requires quartz;
    requires org.slf4j;
    requires kernel;
    requires layout;


    opens com.example.carrental to javafx.fxml;
    exports com.example.carrental;
    exports com.example.carrental.Controller;
    opens com.example.carrental.Controller to javafx.fxml;
    opens com.example.carrental.Model;
}