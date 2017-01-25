package com.audiowave.tverdakhleb.dbconnection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConnectionCreator {
    private static final Logger LOGGER = LogManager.getLogger();

    private Properties properties = new Properties();
    private String dbUrl;

    public ConnectionCreator(){
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("properties/dbConnection");
            dbUrl = bundle.getString("db.url");
            properties.put("user", bundle.getString("db.user"));
            properties.put("password", bundle.getString("db.password"));
            properties.put("autoReconnect", bundle.getString("db.autoReconnect"));
            properties.put("characterEncoding", bundle.getString("db.encoding"));
            properties.put("useUnicode", bundle.getString("db.useUnicode"));
            properties.put("useSSL", bundle.getString("db.useSSL"));
        } catch (MissingResourceException e) {
            LOGGER.log(Level.FATAL, e);
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbUrl, properties);
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, e);
            throw new RuntimeException(e);
        }
        return connection;
    }
}

