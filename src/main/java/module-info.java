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
    requires java.mail;
    requires com.google.api.client.auth;
    requires com.google.api.client.http.apache.v2;
    requires google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.client;
    requires com.google.api.services.gmail;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires org.apache.commons.codec;
    requires jdk.httpserver;
    requires activation;
//    requires javax.activation;

    // requires com.google.api.client.http.javanet;


    opens com.example.carrental to javafx.fxml;
    exports com.example.carrental;
    exports com.example.carrental.Controller;
    opens com.example.carrental.Controller to javafx.fxml;
    opens com.example.carrental.Model;
}