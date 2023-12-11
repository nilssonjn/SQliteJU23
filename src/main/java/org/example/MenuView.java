package org.example;

import java.util.Scanner;

public class MenuView {
    private static final Scanner scanner = new Scanner(System.in);

    public static void printMenuOptions() {
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
                8. Search movies made by a director
                9. Set movie as a favourite
                10. Show all favourite movies
                11. Show how many movies are saved
                12. Show all menu options
                """);
    }

    public static void printMovieAndDirectorHeader (){
        System.out.println("Movie title:\tMovie rating:");
    }

    public static void printMovieAndGenreHeader() {
        System.out.println("Movie title:\t\tGenre:");
    }

    public static void printMovieHeader() {
        System.out.println("Movie ID:\tTitle:\tDirector:\t\tRating:\t\tBudget:\t\tGross:\t\tFavourite:");
    }

    public static void printGenreHeader() {
        System.out.println("Genre ID:\tGenre:");
    }

    public static String getUserInput() {
        return scanner.nextLine();
    }
}
