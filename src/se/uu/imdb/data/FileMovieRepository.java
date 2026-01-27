package se.uu.imdb.data;
import se.uu.imdb.model.Movie;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * File-based repository using a simple line format: Title;Score
 */
public class FileMovieRepository implements MovieRepository {
    private final Path filePath;

    /**
     * Creates a respository backed by a given file path
     * @param filePath file path for persistence
     */
    public FileMovieRepository(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Movie> loadAll() throws IOException {
        ensureFileExists();

        List<Movie> movies = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue;

                String[] parts = trimmed.split(",", 2);
                if (parts.length != 2) continue;

                String title = parts[0].trim();
                String scoreText = parts[1].trim();

                try {
                    int score = Integer.parseInt(scoreText);
                    movies.add(new Movie(title, score));
                } catch (NumberFormatException | IllegalArgumentException ignored) {
                }
            }
        }
        return movies;
    }

    @Override
    public void saveAll(List<Movie> movies) throws IOException {
        ensureFileExists();

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            for (Movie movie : movies) {
                writer.write(movie.getTitle() + ";" + movie.getReviewScore());
                writer.newLine();
            }
        }
    }

    private void ensureFileExists() throws IOException {
        Path parent = filePath.getParent();
        if (parent == null || !Files.notExists(parent)) {
            Files.createDirectories(parent);
        }
        if (Files.notExists(filePath)) {
            Files.createFile(filePath);
        }
    }
}
