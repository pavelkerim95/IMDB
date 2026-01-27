package se.uu.imdb.ui;
import se.uu.imdb.model.Movie;
import se.uu.imdb.service.ImdbDatabase;
import java.io.*;
import java.util.*;

/**
 * Console-based user interface for IMDB.
 */
public class ConsoleUI {
    private final ImdbDatabase db;
    private final Scanner scan;

    /**
     * Creates a ConsoleUI.
     * @param db IMDB database service
     */
    public ConsoleUI(ImdbDatabase db) {
        this.db = db;
        this.scan = new Scanner(System.in);
    }

    /**
     * Runs the main program loop until user chooses to exit.
     */
    public void run() {
        boolean running = true;
        while(running) {
            printMenu();
            int choice = readInt("", 1, 4);

            switch (choice) {
                case 1 -> handleSearchTitle();
                case 2 -> handleSearchScore();
                case 3 -> handleAddMovie();
                case 4 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
        System.out.println("Program closed.");
    }

    private void handleSearchTitle() {
        System.out.print("Enter keyword: ");
        String keyword = scan.nextLine();

        List<Movie> matches = db.searchByTitleKeyword(keyword);
        printMovies(matches);
    }

    private void handleSearchScore() {
        int min = readInt("Enter minimum review score (1-5): ", 1, 5);
        List<Movie> matches = db.searchByMinimumScore(min);
        printMovies(matches);
    }

    private void handleAddMovie() {
        System.out.print("Title: ");
        String title = scan.nextLine();
        int score = readInt("Review score (1-5): ", 1, 5);

        try {
            db.addMovie(title, score);
            System.out.println("Movie added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add movie: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not save movie file: " + e.getMessage());
        }
    }

    private void printMovies(List<Movie> movies) {
        if(movies.isEmpty()) {
            System.out.println("No matches");
            return;
        }
        for(Movie m : movies) {
            System.out.println(m);
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("** IMDB **");
        System.out.println("----------------------");
        System.out.println("1. Search Title");
        System.out.println("2. Search review score");
        System.out.println("3. Add movie");
        System.out.println("----------------------");
        System.out.println("4. Close program");
    }

    private int readInt(String prompt, int min, int max) {
        while(true) {
            if(!prompt.isBlank()) {
                System.out.print(prompt);
            }
            String input = scan.nextLine().trim();
            try {
                int value =Integer.parseInt(input);
                if(value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
}
