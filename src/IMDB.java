package se.uu.imdb;
import se.uu.imdb.data.FileMovieRepository;
import se.uu.imdb.service.ImdbDatabase;
import se.uu.imdb.ui.ConsoleUI;
import java.io.*;
import java.nio.file.Path;

/**
 * Program entry point for IMDB.
 */
public class IMDB {
    /**
     * Starts IMDB. Movies are persisted to a local text file.
     * @param arg command line arg (not used)
     */
    public static void main(String[] arg) {
        Path dataFile = Path.of("data", "imdb.txt");

        try {
            var repository = new FileMovieRepository(dataFile);
            var db = new ImdbDatabase(repository);
            var ui = new ConsoleUI(db);
            ui.run();
        } catch (IOException e) {
            System.out.println("Failed to start application due to IO error: " + e.getMessage());
        }
    }
}