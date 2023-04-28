//package com.example.carrental.Database;
//
//
//import org.apache.commons.dbcp2.BasicDataSource;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class ConnectionPool {
//    private static final String URL = "jdbc:postgresql://localhost:5432/rent-car";
//    private static final String USERNAME = "postgres";
//    private static final String PASSWORD = "postgres";
//
//    private static DataSource dataSource;
//
//    public static DataSource getDataSource() {
//        if (dataSource == null) {
//            BasicDataSource ds = new BasicDataSource();
//            ds.setUrl(URL);
//            ds.setUsername(USERNAME);
//            ds.setPassword(PASSWORD);
//            ds.setInitialSize(10); // numărul de conexiuni inițiale
//            dataSource = ds;
//        }
//        return dataSource;
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return getDataSource().getConnection();
//    }
//}