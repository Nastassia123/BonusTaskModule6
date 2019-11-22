package com.epam.ta.salad.Repository.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLConnection {

    public Connection SQLConnection() throws SQLException {

        final String url = "mysql.url";
        Properties prop = new Properties();
        prop.setProperty("user", "mysql.username");
        prop.put("password", "mysql.password");
        prop.put("autoReconnect", "true");
        prop.put("characterEncoding", "UTF-8");
        prop.put("useUnicode", "true");
        Connection cn = null;
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        try {
            cn = DriverManager.getConnection(url, prop);
        } catch (SQLException e) {
            System.out.println("Can not connect to the database");
        }
        return cn;
    }

}
