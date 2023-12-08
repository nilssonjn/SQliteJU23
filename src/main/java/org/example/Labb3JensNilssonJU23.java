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
                =================
                0. Exit the program
                1. Show all movies
                2. Add a new movie
                3. Update a movie rating
                4. Delete a movie
                5. Add movie genre
                6. Show all genres
                7. Show movies with genre
                8. Show all menu options
                """);
    }

    private static void showGenres () {
        String sql = "SELECT * FROM genres";

        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet  = statement.executeQuery(sql);
            printGenreHeader();

            while (resultSet.next()) {
                printGenres(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printGenreHeader() {
        System.out.println("Genre ID\tGenre");
    }

    private static void printGenres(ResultSet resultSet) throws SQLException {
        System.out.println(resultSet.getInt("genreId") + "\t" +
                resultSet.getString("genreName"));
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
                resultSet.getString("movieRating") + "\t\t" +
                resultSet.getInt("movieBudget") + "\t" +
                resultSet.getInt("movieGross"));
    }

    private static void printMovieHeader() {
        System.out.println("Movie ID\tTitle\tDirector\t\tRating\t\tBudget\t\tGross");
    }

    private static void handleMovieDelete(int id) {
        String sql = "DELETE FROM movies WHERE movieId = ?";

        try {
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("You have deleted a movie.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteMovie() {
        System.out.println("Insert the id of the movie you want to delete: ");
        int inputId = scanner.nextInt();
        handleMovieDelete(inputId);
        scanner.nextLine();
    }

    private static void handleMovieInsert(String movieTitle, String movieDirector,
                                          double movieRating, int movieBudget, int movieGross) {
        String sql = "INSERT INTO movies(movieTitle, movieDirector, movieRating," +
                "movieBudget, movieGross) VALUES(?,?,?,?,?)";
        try {
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, movieTitle);
            preparedStatement.setString(2, movieDirector);
            preparedStatement.setDouble(3, movieRating);
            preparedStatement.setInt(4, movieBudget);
            preparedStatement.setInt(5, movieGross);
            preparedStatement.executeUpdate();
            System.out.println("You have added a new movie.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insertNewMovie() {
        System.out.println("Enter the movie title: ");
        String insertTitle = scanner.nextLine();

        System.out.println("Enter the movie director: ");
        String insertDirector = scanner.nextLine();

        double insertRating = validateDoubleInput("Enter the movie rating :");
        int insertBudget = validateIntInput("Enter the movie budget: ");
        int insertGross = validateIntInput("Enter the movie gross: ");
        handleMovieInsert(insertTitle, insertDirector, insertRating, insertBudget, insertGross);
    }

    private static int validateIntInput(String prompt) {
        int insertBudget;
        while (true) {
            try {
                System.out.println(prompt);
                insertBudget = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid input.");
            }
        }
        return insertBudget;
    }

    private static double validateDoubleInput(String prompt) {
        double insertRating;
        while (true) {
            try {
                System.out.println(prompt);
                insertRating = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid input.");
            }
        }
        return insertRating;
    }

    private static void handleMovieGenreInsert(String genreName) {
        String sql = "INSERT INTO genres(genreName) VALUES(?)";
        try {
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, genreName);
            preparedStatement.executeUpdate();
            System.out.println("Genre added successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insertNewGenre() {
        System.out.println("Enter the genres you want to add for a movie: ");
        String insertGenreUpdate = scanner.nextLine();
        handleMovieGenreInsert(insertGenreUpdate);
    }

    private static void updateMovie(double movieRating, int movieId) {
        String sql = "UPDATE movies SET movieRating = ? WHERE movieId = ?";
        try {
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, movieRating);
            preparedStatement.setInt(2, movieId);
            preparedStatement.executeUpdate();
            System.out.println("Movie updated successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateMovieOnId() {
        System.out.println("Enter the movie ID you want to update: ");
        int insertUpdate = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the new movie rating you want to update to: ");
        double movieRating = 0.0;
        try {
            String ratingInput = scanner.nextLine();
            ratingInput = ratingInput.replace(',', '.');
            movieRating = Double.parseDouble(ratingInput);
            if (movieRating < 0 || movieRating > 11) {
                System.out.println("Invalid rating. Must be between 0 - 10.");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Enter a valid input");
            scanner.nextLine();
        }
        updateMovie(movieRating, insertUpdate);
        scanner.nextLine();
    }

    // Will only show movieId 1 with genreId 1. Need a method for inserting movieGId and movieMId
    private static void showMovieAndGenre(){
        String sql = "SELECT movies.movieTitle, genres.genreName FROM genres " +
                "INNER JOIN movieGenre ON genres.genreId = movieGenre.movieGId " +
                "INNER JOIN movies ON movieGenre.movieMId = movies.movieId";
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                printMovieAndGenre(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printMovieAndGenre(ResultSet resultSet) throws SQLException {
        System.out.println(resultSet.getString("movieTitle") + "\t" +
                resultSet.getString("genreName"));
    }

    public static void main(String[] args) {
        String selection;
        do {
            printMenuOptions();
            selection = scanner.nextLine();
            try {
                switch (selection) {
                    case "0" -> System.out.println("Exiting the program...");
                    case "1" -> showAllMovies();
                    case "2" -> insertNewMovie();
                    case "3" -> updateMovieOnId();
                    case "4" -> deleteMovie();
                    case "5" -> insertNewGenre();
                    case "6" -> showGenres();
                    case "7" -> showMovieAndGenre();
                    case "8" -> printMenuOptions();
                    default -> System.out.print("Invalid option...\n");
                }
            } catch (IndexOutOfBoundsException exception) {
                scanner.nextLine();
            }
        } while (!selection.equals("0"));
    }
}


