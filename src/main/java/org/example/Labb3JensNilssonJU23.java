package org.example;

import java.sql.*;
import java.util.Scanner;

public class Labb3JensNilssonJU23 {

    private static Scanner scanner = new Scanner(System.in);

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

    private static void printMenuOptions() {
        System.out.print("""
                Choose an option:
                =============
                0. Exit the program
                1. Show all movies
                2. Add a new movie
                3. Update a existing movie
                4. Remove a movie
                5. Show all menu options
                """);
    }

    private static void showAllMovies() {
        String sql = "SELECT * FROM movies";

        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            printMovieHeader();

            while (resultSet.next()) {
                printMovieDetails(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printMovieDetails(ResultSet resultSet) throws SQLException {
        System.out.println(resultSet.getInt("movieId") + "\t" +
                resultSet.getString("movieTitle") + "\t" +
                resultSet.getString("movieDirector") + "\t" +
                resultSet.getInt("movieRating") + "\t" +
                resultSet.getInt("movieBudget") + "\t" +
                resultSet.getInt("movieGross"));
    }

    private static void printMovieHeader() {
        System.out.println("Movie ID:\tTitle\tDirector\tRating\tBudget\tGross");
    }

    public static void main(String[] args) {
        String selection;
        do {
            printMenuOptions();
            selection = scanner.next();
            try {
                switch (selection) {
                    case "0" -> System.out.println("Exiting the program...");
                    case "1" -> showAllMovies();
                    default -> System.out.print("Choose the right option...\n");
                }
            } catch (IndexOutOfBoundsException exception) {
                scanner.nextLine();
            }
        } while (!selection.equals("0"));
    }
}
