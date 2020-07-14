package com.ex.app.persistence;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class PostgresConnectionFactory implements ConnectionFactory {
    private final Logger LOG = Logger.getLogger("logger");

    private String url;
    private String username;
    private String password;
    private Driver registeredDriver;

    public PostgresConnectionFactory() {

        try {
            this.registeredDriver= new org.postgresql.Driver();
            DriverManager.registerDriver(registeredDriver);
             
        } catch (SQLException ex) {
            System.out.println("Error registering driver");
            ex.printStackTrace();
        }
    }

    public PostgresConnectionFactory(String url, String username, String password) {
        this();
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection newConnection() {
        try {
            return DriverManager.getConnection(this.url, this.username, this.password);
        } catch(SQLException ex) {
            System.out.println("Error establishing connection");
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void destroy() {
        try {
            DriverManager.deregisterDriver(this.registeredDriver);
        } catch (SQLException ex) {
            System.out.println("Error deregistering driver");
        }

    }
}
