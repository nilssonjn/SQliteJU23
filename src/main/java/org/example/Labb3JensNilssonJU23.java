package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Labb3JensNilssonJU23 {

    private static Connection connect() {
        String url = "jdbc:sqlite:/Users/jensnilsson/Documents/sqlite- filer/SQliteLabb3JensNilssonJU23.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void main(String[] args) {
    }
}
